package com.sa.healntrack.pharmacy_service.catalog.application.port.out.persistence;

import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;

public interface StoreMedicine {
    Medicine save(Medicine medicine);
}
