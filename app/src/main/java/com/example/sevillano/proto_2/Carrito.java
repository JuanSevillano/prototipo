package com.example.sevillano.proto_2;

import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by Sevi on 27/11/17.
 */

class Carrito {

    private static final Carrito ourInstance = new Carrito();
    private ArrayList<Object> productos;

    static Carrito getInstance() {
        return ourInstance;
    }

    private Carrito() {
        productos = new ArrayList<>();

    }

    public void hacerPedido(){

    }

    public void agregarProducto(Producto p){
        productos.add(p);
    }

    public float precioTotal(){

        float total = 0;
        for(int i = 0; i < productos.size(); i++){
            if(productos.get(i) instanceof Producto) {
                Producto p = (Producto) productos.get(i);
                float precio = Float.valueOf(p.getPrecio().split(" ")[1]);
                total += precio;
            }
        }
        return total;
    }


    public ArrayList<Object> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Object> productos) {
        this.productos = productos;
    }
}
