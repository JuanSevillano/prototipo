package com.example.sevillano.proto_2;

/**
 * Created by Sevi on 6/10/17.
 */

class Producto {
    // Variables para definir un producto
    String nombre, descripcion;
    float precio;
    int imagen;
    boolean tendencia;

    Producto(String nombre, String descripcion, float precio, int imagen, boolean tendencia) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.tendencia = tendencia;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public int getImagen() {
        return imagen;
    }
}
