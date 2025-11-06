package com.sa.healntrack.pharmacy_service.inventory.application.port.out.integration.get_all_medicines;

public record CatalogGetAllMedicinesQuery(String searchTerm, Boolean isActive) {
}
