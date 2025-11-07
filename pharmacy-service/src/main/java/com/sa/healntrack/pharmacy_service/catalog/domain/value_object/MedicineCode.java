package com.sa.healntrack.pharmacy_service.catalog.domain.value_object;

public record MedicineCode(String value) {

    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 10;
    private static final String VALID_PATTERN = "^[A-Z]+-[0-9]+$";;

    public MedicineCode {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El código de la medicina no puede estar vacío");
        }

        String normalized = value.trim().toUpperCase();

        if (normalized.length() < MIN_LENGTH || normalized.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "El código de la medicina debe tener entre " + MIN_LENGTH + " y " + MAX_LENGTH + " caracteres"
            );
        }

        if (!normalized.matches(VALID_PATTERN)) {
            throw new IllegalArgumentException(
                    "El código solo puede contener letras, números o guiones (ejemplo válido: AMOX-500)"
            );
        }

        value = normalized;
    }
}
