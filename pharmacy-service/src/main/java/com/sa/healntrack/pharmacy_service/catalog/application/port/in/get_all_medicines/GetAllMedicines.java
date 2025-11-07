package com.sa.healntrack.pharmacy_service.catalog.application.port.in.get_all_medicines;

import com.sa.healntrack.pharmacy_service.catalog.domain.Medicine;

import java.util.List;

public interface GetAllMedicines {
    List<Medicine> handle(GetAllMedicinesQuery query);
}
