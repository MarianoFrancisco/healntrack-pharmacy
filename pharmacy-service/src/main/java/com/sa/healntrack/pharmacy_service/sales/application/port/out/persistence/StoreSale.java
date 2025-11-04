package com.sa.healntrack.pharmacy_service.sales.application.port.out.persistence;

import com.sa.healntrack.pharmacy_service.sales.domain.Sale;

public interface StoreSale {
    Sale save(Sale sale);
}
