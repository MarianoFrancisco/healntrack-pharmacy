package com.sa.healntrack.pharmacy_service.sales.application.port.out.persistence;

import com.sa.healntrack.pharmacy_service.sales.domain.Sale;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FindSales {
    Optional<Sale> findById(UUID saleId);

    List<Sale> search(Long occurredFrom, Long occurredTo, UUID sellerId, String status);
}
