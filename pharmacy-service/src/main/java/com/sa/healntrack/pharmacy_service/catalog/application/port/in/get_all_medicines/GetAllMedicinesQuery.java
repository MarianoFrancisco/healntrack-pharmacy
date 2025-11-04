package com.sa.healntrack.pharmacy_service.catalog.application.port.in.get_all_medicines;

public record GetAllMedicinesQuery(String searchTerm, Boolean isActive) {

}
