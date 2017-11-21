package com.example.sevillano.proto_2;

import android.media.Image;

import com.google.firebase.database.GenericTypeIndicator;

import java.util.List;

/**
 * Created by Sevi on 6/10/17.
 */

class Tendencia {

    String nombre, descripcion;
    String [] src, guardado,tags;

    Tendencia(){


    }

    Tendencia(String nombre, String descripcion){
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    String getNombre(){
        return nombre;
    }

    String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public void setGuardado(String[] guardado) {
        this.guardado = guardado;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String[] getSrc(){return src;}

    public void setSrc(String[] src){
        this.src = src;
    }

}
