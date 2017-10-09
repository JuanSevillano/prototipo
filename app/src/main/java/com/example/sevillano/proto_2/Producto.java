package com.example.sevillano.proto_2;

/**
 * Created by Sevi on 6/10/17.
 */

class Producto {
    // Variables para definir un producto
    String nombre, descripcion, precio, imgUrl;

    Producto(String nombre, String descripcion, String precio, String medidas, String imgUrl) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imgUrl = imgUrl;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getPrecio() {
        return precio;
    }

    public String getImagen() {
        return imgUrl;
    }
}
