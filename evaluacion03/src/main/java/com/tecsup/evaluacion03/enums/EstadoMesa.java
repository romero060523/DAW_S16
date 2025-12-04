package com.tecsup.evaluacion03.enums;

public enum EstadoMesa {
    DISPONIBLE("Mesa disponible"),
    OCUPADA("Mesa con clientes"),
    RESERVADA("Mesa reservada"),
    MANTENIMIENTO("Mesa en mantenimiento");

    private final String descripcion;

    EstadoMesa(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
