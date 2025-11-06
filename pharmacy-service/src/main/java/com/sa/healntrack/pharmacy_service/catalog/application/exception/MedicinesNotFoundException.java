package com.sa.healntrack.pharmacy_service.catalog.application.exception;

import com.sa.healntrack.pharmacy_service.common.application.exception.EntityNotFoundException;

import java.util.List;

public class MedicinesNotFoundException extends EntityNotFoundException {
    public MedicinesNotFoundException(List<String> codes) {
        super("No se encontraron las siguientes medicinas: " + String.join(", ", codes));
    }
}
