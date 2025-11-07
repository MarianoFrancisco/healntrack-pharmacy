package com.sa.healntrack.pharmacy_service.inventory.application.service;

import com.sa.healntrack.pharmacy_service.catalog.application.exception.MedicineNotFoundException;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence.FindMedicines;
import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;
import com.sa.healntrack.pharmacy_service.common.application.port.out.publish_notification_created.PublishNotificationCreatedCommand;
import com.sa.healntrack.pharmacy_service.common.infrastructure.adapter.out.kafka.notification.NotificationPublisher;
import com.sa.healntrack.pharmacy_service.inventory.application.exception.InsufficientStockException;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.decrease_stock.DecreaseStock;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.decrease_stock.DecreaseStockCommand;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.batch.FindBatches;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.batch.StoreBatch;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.snapshot.FindSnapshot;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.snapshot.StoreSnapshot;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.rest.employee.Employee;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.rest.employee.get_employees_by_code.GetEmployeesByCode;
import com.sa.healntrack.pharmacy_service.inventory.domain.Batch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class DecreaseStockImpl implements DecreaseStock {

    private final FindMedicines findMedicines;
    private final FindSnapshot findSnapshot;
    private final StoreSnapshot storeSnapshot;
    private final FindBatches findBatches;
    private final StoreBatch storeBatch;
    private final NotificationPublisher notificationPublisher;
    private final GetEmployeesByCode getEmployeesByCode;

    @Override
    public void handle(DecreaseStockCommand command) {
        Medicine medicine = resolveMedicine(command.medicineCode());
        UUID medicineId = medicine.getId().value();

        ensureAvailable(medicineId, command.quantity());

        List<Batch> fefoBatches = loadFefoConsumables(medicineId);
        consumeQuantity(fefoBatches, command.quantity());

        decreaseSnapshot(medicineId, command.quantity());

        validateRemaining(medicine, medicineId);
    }

    private Medicine resolveMedicine(String medicineCode) {
        return findMedicines.findByCode(medicineCode)
                .orElseThrow(() -> new MedicineNotFoundException(medicineCode));
    }

    private void ensureAvailable(UUID medicineId, int requestedQty) {
        int available = findSnapshot.getTotalQuantity(medicineId);
        if (available < requestedQty) {
            throw new InsufficientStockException("Stock insuficiente para la medicina solicitada");
        }
    }

    private List<Batch> loadFefoConsumables(UUID medicineId) {
        return findBatches.findConsumableBatches(medicineId).stream()
                .sorted(
                        Comparator
                                .comparing(Batch::getExpirationDate, Comparator.nullsLast(Comparator.naturalOrder()))
                                .thenComparingLong(Batch::getCreatedAt)
                                .thenComparing(b -> b.getId().value())
                )
                .toList();
    }

    private void consumeQuantity(List<Batch> batches, int requestedQty) {
        int remaining = requestedQty;

        for (Batch b : batches) {
            if (remaining == 0) break;

            int onHand = b.getQuantityOnHand().value();
            if (onHand <= 0) continue;

            int toTake = Math.min(remaining, onHand);
            if (toTake <= 0) continue;

            b.consume(toTake);
            storeBatch.save(b);
            remaining -= toTake;
        }

        if (remaining > 0) {
            throw new InsufficientStockException("No fue posible consumir toda la cantidad solicitada bajo FEFO");
        }
    }

    private void decreaseSnapshot(UUID medicineId, int qty) {
        storeSnapshot.decrease(medicineId, qty);
    }

    private void validateRemaining(Medicine medicine, UUID medicineId) {
        int available = findSnapshot.getTotalQuantity(medicineId);
        if (available < medicine.getMinStock()) {
            List<Employee> employee = getEmployeesByCode.getByCode();
            employee.forEach(
                    e -> publishNotification(medicine, e, available)
            );
        }
    }

    private void publishNotification(Medicine medicine, Employee employee, int available) {
        notificationPublisher.publish(
                new PublishNotificationCreatedCommand(
                        UUID.randomUUID().toString(),
                        employee.email(),
                        employee.fullname(),
                        "Stock bajo para medicina: " + medicine.getName(),
                        getHtml(medicine, available)
                )
        );
    }

    private String getHtml(Medicine medicine, int available) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <meta charset='UTF-8' />" +
                "    <title>Alerta de Stock Bajo</title>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; }" +
                "        .container { max-width: 600px; padding: 20px; border: 1px solid #ddd; border-radius: 4px; }" +
                "        .title { color: #b71c1c; font-size: 18px; font-weight: bold; }" +
                "        table { width: 100%; border-collapse: collapse; margin-top: 15px; }" +
                "        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; font-size: 14px; }" +
                "        th { background-color: #f2f2f2; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='container'>" +
                "        <p class='title'>Alerta de stock bajo</p>" +
                "        <p>Se ha detectado que una medicina se encuentra por debajo del nivel mínimo de stock establecido.</p>" +
                "        <table>" +
                "            <tr><th>Código</th><td>" + medicine.getCode().value() + "</td></tr>" +
                "            <tr><th>Nombre</th><td>" + medicine.getName() + "</td></tr>" +
                "            <tr><th>Stock actual</th><td>" + available + "</td></tr>" +
                "            <tr><th>Stock mínimo</th><td>" + medicine.getMinStock() + "</td></tr>" +
                "        </table>" +
                "        <p>Se recomienda realizar el reabastecimiento correspondiente.</p>" +
                "    </div>" +
                "</body>" +
                "</html>";
    }
}
