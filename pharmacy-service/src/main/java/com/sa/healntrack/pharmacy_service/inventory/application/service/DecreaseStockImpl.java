package com.sa.healntrack.pharmacy_service.inventory.application.service;

import com.sa.healntrack.pharmacy_service.catalog.application.exception.MedicineNotFoundException;
import com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence.FindMedicines;
import com.sa.healntrack.pharmacy_service.inventory.application.exception.InsufficientStockException;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.decrease_stock.DecreaseStock;
import com.sa.healntrack.pharmacy_service.inventory.application.port.in.decrease_stock.DecreaseStockCommand;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.batch.FindBatches;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.batch.StoreBatch;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.snapshot.FindSnapshot;
import com.sa.healntrack.pharmacy_service.inventory.application.port.out.persistence.snapshot.StoreSnapshot;
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

    @Override
    public void handle(DecreaseStockCommand command) {
        UUID medicineId = resolveMedicineId(command.medicineCode());
        ensureAvailable(medicineId, command.quantity());

        List<Batch> fefoBatches = loadFefoConsumables(medicineId);
        consumeQuantity(fefoBatches, command.quantity());

        decreaseSnapshot(medicineId, command.quantity());
    }

    private UUID resolveMedicineId(String medicineCode) {
        return findMedicines.findByCode(medicineCode)
                .map(m -> m.getId().value())
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

}
