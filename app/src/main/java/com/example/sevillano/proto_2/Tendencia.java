package com.example.sevillano.proto_2;

import android.media.Image;

/**
 * Created by Sevi on 6/10/17.
 */

class Tendencia {

    String nombre, descripcion;
    int imagen;

    Tendencia(String nombre, String descripcion, int imagen){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    String getNombre(){
        return nombre;
    }

    String getDescripcion(){
        return descripcion;
    }

    int getImagen(){
        return imagen;
    }
}
