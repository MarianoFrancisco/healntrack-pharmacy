package com.sa.healntrack.pharmacy_service.inventory.application.port.out.integration.get_all_medicines;

import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;

import java.util.List;

public interface CatalogGetAllMedicines {
    List<Medicine> getAll(CatalogGetAllMedicinesQuery query);
}
