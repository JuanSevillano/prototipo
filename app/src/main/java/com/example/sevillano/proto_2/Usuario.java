package com.example.sevillano.proto_2;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by Sevi on 24/11/17.
 */

class Usuario {


    private String nombre, id;
    private ArrayList<String> likes;
    private ArrayList<String> guardados;
    private static Usuario ourInstance = new Usuario();

    static Usuario getInstance() {
        return ourInstance;
    }

    private Usuario() {

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            System.out.println(" ENTRANDO ");
            id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            nombre = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            // Creando arreglos del usuario
            likes = new ArrayList<>();
            guardados = new ArrayList<String>();
            // Cargando likes desde los productos
            if (ProductoFragment.productos != null) {
                for (int i = 0; i < ProductoFragment.productos.size(); i++) {
                    for (int j = 0; j < ProductoFragment.productos.get(i).getLikes().length; j++) {
                        // Verificando que el like del producto coincida con el Uid Del usuario
                        if (ProductoFragment.productos.get(i).getLikes()[j].equals(Usuario.getInstance().getId())) {
                            likes.add(ProductoFragment.productos.get(i).getNombre());
                        }
                    }
                }
            }
            System.out.println("LIKES USUARIO : " + likes);

        } else {
            System.out.println("THERES NOT USER LOGED");
        }

    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public ArrayList<String> getGuardados() {
        return guardados;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }

    public void setGuardados(ArrayList<String> guardados) {
        this.guardados = guardados;
    }
}
