package com.sa.healntrack.pharmacy_service.inventory.application.port.in.get_batches;

public record GetAllBatchesQuery(String medicineCode, Boolean onlyWithStock, Boolean onlyNotExpired) {
}
