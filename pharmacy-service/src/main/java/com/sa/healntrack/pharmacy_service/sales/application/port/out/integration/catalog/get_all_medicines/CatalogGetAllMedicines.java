package com.sa.healntrack.pharmacy_service.sales.application.port.out.integration.catalog.get_all_medicines;

import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;

import java.util.List;

public interface CatalogGetAllMedicines {
    List<Medicine> getAll(CatalogGetAllMedicinesQuery query);
}
