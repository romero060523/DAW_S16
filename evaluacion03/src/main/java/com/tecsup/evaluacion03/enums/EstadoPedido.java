package com.tecsup.evaluacion03.enums;

public enum EstadoPedido {
    PENDIENTE("Pendiente de preparación"),
    EN_PREPARACION("En preparación en cocina"),
    SERVIDO("Servido al cliente"),
    CERRADO("Pedido cerrado y cobrado");

    private final String descripcion;

    EstadoPedido(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
