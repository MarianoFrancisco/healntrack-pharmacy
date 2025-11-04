package com.sa.healntrack.pharmacy_service.inventory.application.port.in.get_batches;

import com.sa.healntrack.pharmacy_service.inventory.domain.Batch;

import java.util.List;

public interface GetAllBatches {
    List<Batch> handle(GetAllBatchesQuery q);
}
