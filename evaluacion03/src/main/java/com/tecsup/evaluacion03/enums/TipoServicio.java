package com.tecsup.evaluacion03.enums;

public enum TipoServicio {
    MESA("Servicio en mesa"),
    DELIVERY("Entrega a domicilio"),
    PARA_LLEVAR("Para llevar");

    private final String descripcion;

    TipoServicio(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
