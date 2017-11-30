package com.example.sevillano.proto_2;

import android.content.Context;
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


    private String nombre, id, email;
    private ArrayList<Object> likes;
    private ArrayList<Object> guardados;
    private static Usuario ourInstance = new Usuario();

    static Usuario getInstance() {
        return ourInstance;
    }

    private Usuario() {

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            System.out.println(" ENTRANDO ");
            id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            nombre = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            email = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
            likes = new ArrayList<>();
            guardados = new ArrayList<>();
            // Cargando likes desde los productos


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

    public ArrayList<Object> getLikes() {
        return likes;
    }

    public ArrayList<Object> getGuardados() {
        return guardados;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLikes(ArrayList<Object> likes) {
        this.likes = likes;
    }

    public void setGuardados(ArrayList<Object> guardados) {
        this.guardados = guardados;
    }

    public String getEmail() {
        return email;
    }
}
