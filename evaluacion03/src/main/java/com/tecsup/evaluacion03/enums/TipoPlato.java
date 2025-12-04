package com.tecsup.evaluacion03.enums;

public enum TipoPlato {
    ENTRADA("Entrada"),
    FONDO("Fondo"),
    POSTRE("Postre"),
    BEBIDA("Bebida");

    private final String descripcion;

    TipoPlato(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
