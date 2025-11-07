package com.sa.healntrack.pharmacy_service.sales.application.port.out.integration.catalog.get_all_medicines;

public record CatalogGetAllMedicinesQuery(String searchTerm, Boolean isActive) {
}
