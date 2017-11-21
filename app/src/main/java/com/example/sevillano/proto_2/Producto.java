package com.example.sevillano.proto_2;

/**
 * Created by Sevi on 6/10/17.
 */

class Producto {
    // Variables para definir un producto
    String nombre, descripcion, precio, medidas;
    String[] src, likes, tags;

    Producto(){

    }

    Producto(String nombre, String descripcion, String precio, String medidas, String[] src,String[] likes, String[] tags) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.src = src;
        this.likes = likes;
        this.tags = tags;
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

    public String[] getImagen() {
        return src;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setMedidas(String medidas) {
        this.medidas = medidas;
    }

    public void setLikes(String[] likes) {
        this.likes = likes;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public void setSrc(String[] src) {
        this.src = src;
    }
}
