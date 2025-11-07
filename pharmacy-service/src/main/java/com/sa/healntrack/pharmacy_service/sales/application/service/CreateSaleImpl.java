package com.sa.healntrack.pharmacy_service.sales.application.service;

import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import com.sa.healntrack.pharmacy_service.common.application.port.out.kafka.report.publish_purchased_medicine.PublishMedicineSold;
import com.sa.healntrack.pharmacy_service.common.application.port.out.kafka.report.publish_purchased_medicine.PublishMedicineSoldCommand;
import com.sa.healntrack.pharmacy_service.common.application.port.out.kafka.report.publish_purchased_medicine.ReportTransactionType;
import com.sa.healntrack.pharmacy_service.sales.application.mapper.SaleItemMapper;
import com.sa.healntrack.pharmacy_service.sales.application.mapper.SaleMapper;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.create_sale.CreateSale;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.create_sale.CreateSaleCommand;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.integration.catalog.get_medicines_by_codes.CatalogGetMedicinesByCodesQuery;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.integration.inventory.decrease_stock.InventoryDecreaseStockCommand;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.kafka.billing.publish_bill_created.PublishBillCreatedCommand;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.kafka.billing.publish_bill_created.ItemBill;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.kafka.hospitalization.publish_medication_created.PublishMedicationCreatedCommand;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.patient.get_patient_by_id.GetPatientById;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.persistence.StoreSale;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.patient.get_patient_by_id.GetPatientByIdQuery;
import com.sa.healntrack.pharmacy_service.sales.application.port.out.rest.patient.get_patient_by_id.PatientById;
import com.sa.healntrack.pharmacy_service.sales.domain.Sale;
import com.sa.healntrack.pharmacy_service.sales.domain.SaleItem;
import com.sa.healntrack.pharmacy_service.sales.application.port.in.create_sale.Item;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.BuyerType;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.Money;
import com.sa.healntrack.pharmacy_service.sales.domain.value_object.SaleStatus;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.kafka.billing.BillPublisher;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.integration.catalog.CatalogGetMedicinesByCodesInProcess;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.integration.inventory.InventoryDecreaseStockInProcess;
import com.sa.healntrack.pharmacy_service.sales.infrastructure.adapter.out.kafka.hospitalization.MedicationPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CreateSaleImpl implements CreateSale {

    private final StoreSale storeSale;
    private final CatalogGetMedicinesByCodesInProcess catalogGetMedicinesByCodesInProcess;
    private final InventoryDecreaseStockInProcess inventoryDecreaseStockProcess;
    private final GetPatientById getPatientById;
    private final BillPublisher billPublisher;
    private final MedicationPublisher medicationPublisher;
    private final PublishMedicineSold publishMedicineSold;

    @Override
    public Sale handle(CreateSaleCommand command) {
        String service = "";
        PatientById patient = null;
        if (command.buyerType().equals(BuyerType.PATIENT.name())) {
            patient = getPatientById.get(new GetPatientByIdQuery(command.buyerId().toString()));
            if (patient == null) {
                throw new IllegalArgumentException("Paciente con identificador " + command.buyerId() + " no encontrado.");
            }
        }

        List<Medicine> medicines = catalogGetMedicinesByCodesInProcess.get(
                new CatalogGetMedicinesByCodesQuery(
                        command.items()
                                .stream()
                                .map(Item::medicineCode)
                                .collect(Collectors.toSet())
                )
        );

        Map<String, Medicine> medByCode = medicines.stream()
                .collect(Collectors.toMap(
                        m -> m.getCode().value(),
                        Function.identity()
                ));

        List<SaleItem> items = SaleItemMapper.toDomain(command, medByCode);

        Money total = Money.sum(
                items.stream()
                        .map(si -> si.getUnitPrice().multiply(si.getQuantity()).value())
                        .toList()
        );
        SaleStatus status = Objects.equals(command.buyerType(), BuyerType.HOSPITALIZATION.name())
                ? SaleStatus.OPEN
                : SaleStatus.COMPLETED;

        Sale sale = SaleMapper.toDomain(command, items, total.value(), status.name());

        for (SaleItem it : sale.getItems()) {
            inventoryDecreaseStockProcess.decrease(
                    new InventoryDecreaseStockCommand(
                            it.getMedicineCode().value(),
                            it.getQuantity())
            );
        }

        storeSale.save(sale);
        if (patient != null) {
            sendBill(
                    patient,
                    sale,
                    medByCode
            );
            service = "PHARMACY";
        } else {
            medicationPublisher.publish(new PublishMedicationCreatedCommand(
                    sale.getId().value(),
                    sale.getBuyerId().value(),
                    Instant.ofEpochMilli(sale.getOccurredAt())
                            .atZone(ZoneOffset.UTC)
                            .toLocalDate(),
                    sale.getTotal().value()
            ));
            service = "MEDICATION";
        }
        String finalService = service;
        sale.getItems().forEach(
                it -> {
                    publishMedicineSold.publish(
                            new PublishMedicineSoldCommand(
                                    it.getMedicineId().value(),
                                    finalService,
                                    LocalDate.now(),
                                    ReportTransactionType.INCOME,
                                    it.getLineTotal().value()
                            )
                    );
                }
        );
        return sale;
    }

    private void sendBill(PatientById info, Sale sale, Map<String, Medicine> medByCode) {
        String subject = "Confirmación de compra";
        String title = "Factura de Compra - HealnTrack";
        String description = "Gracias por su compra en HealnTrack. Adjuntamos su factura detallada.";
        billPublisher.publish(
                new PublishBillCreatedCommand(
                        UUID.randomUUID().toString(),
                        subject,
                        "",
                        title,
                        description,
                        info.email(),
                        info.cui(),
                        info.fullName(),
                        sale.getItems().stream().map(
                                it -> {
                                    Medicine med = medByCode.get(it.getMedicineCode().value());
                                    return new ItemBill(
                                            med.getDescription(),
                                            it.getQuantity(),
                                            it.getUnitPrice().value(),
                                            it.getLineTotal().value()
                                    );
                                }
                        ).toList(),
                        sale.getTotal().value(),
                        Instant.ofEpochMilli(sale.getOccurredAt())
                                .atZone(ZoneOffset.UTC)
                                .toLocalDate()
                )
        );
    }
}
