package com.sa.healntrack.pharmacy_service.sales.application.port.out.integration.catalog.get_medicines_by_codes;

import java.util.Set;

public record CatalogGetMedicinesByCodesQuery(Set<String> codes) {
}
