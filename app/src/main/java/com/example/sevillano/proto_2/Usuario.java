package com.example.sevillano.proto_2;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Sevi on 24/11/17.
 */

class Usuario {


    private String nombre, id;
    private String[] likes;
    private static final Usuario ourInstance = new Usuario();

    static Usuario getInstance() {
        return ourInstance;
    }

    private Usuario() {
        if(FirebaseAuth.getInstance().getCurrentUser().getUid() != null) {
            id = FirebaseAuth.getInstance().getCurrentUser().getUid();
            nombre = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        }
        //likes = new String[]
    }
}
