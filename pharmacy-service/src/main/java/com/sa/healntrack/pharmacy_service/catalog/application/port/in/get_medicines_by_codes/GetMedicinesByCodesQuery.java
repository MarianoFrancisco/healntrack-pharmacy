package com.sa.healntrack.pharmacy_service.catalog.application.port.in.get_medicines_by_codes;

import java.util.Set;

public record GetMedicinesByCodesQuery(Set<String> codes) {
}
