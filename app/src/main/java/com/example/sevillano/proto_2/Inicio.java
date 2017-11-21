package com.example.sevillano.proto_2;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.*;

import android.provider.Settings.Secure;


import java.util.regex.Pattern;

import static android.R.attr.password;
import static java.security.AccessController.getContext;

public class Inicio extends AppCompatActivity implements Login.OnFragmentInteractionListener, Principal.OnFragmentInteractionListener, Registro.OnFragmentInteractionListener {

    private static final String TAG = " [INICIO PRINT] ";
    TextView continuar;
    EditText usr, psw, correoT, contraT, confirm;
    static FirebaseAuth mAuth;
    static FirebaseAuth.AuthStateListener mAuthListener;
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        // Texto para continuar sin registro
        continuar = (TextView) findViewById(R.id.continuar);
        // Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        // Colocando el fragment inicial
        if (savedInstanceState == null) {
            android.app.FragmentManager manager = getFragmentManager();
            android.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.initio, Principal.newInstance());
            transaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }
    }
    // Onclick to make Login fragment visible
    public void iniciar(View v) {
        setFragment(Login.newInstance());
    }
    // Onclick to make Registro fragment visible
    public void registrar(View v) {
        setFragment(Registro.newInstance());
    }
    // Onclick method to continue without login or register
    public void explorar(View v) {
        Intent i = new Intent(getBaseContext(), MainActivity.class);
        i.putExtra("usuario", Secure.ANDROID_ID);
        startActivity(i);
    }
    // Onclick method for register new user on Firebase
    public void registrarUsuario(View v) {

        // Entradas para registrar un usuario nuevo
        correoT = (EditText) findViewById(R.id.registro_usuario);
        contraT = (EditText) findViewById(R.id.registro_contra);
        confirm = (EditText) findViewById(R.id.confirmar_contra);


        // Getting text from EditTexts into Registro fragment
        String correo = correoT.getText().toString();
        String contrasena = contraT.getText().toString();
        String confirmar = confirm.getText().toString();

        // If password and confirmPass are the same.
        if(checkEmail(correo)) {

            if (contrasena.length() > 5) {

                if (contrasena.equals(confirmar)) {

                    mAuth.createUserWithEmailAndPassword(correo, contrasena)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Log.d(" [ REGISTRO ] ", "createUserWithEmail:onComplete:" + task.isSuccessful());


                                    // If can not be succcessful resgiter
                                    if (!task.isSuccessful()) {
                                        Log.w(TAG, task.getException());
                                        Toast.makeText(Inicio.this, R.string.auth_failed,
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                                        startActivity(i);
                                    }


                                }
                            });
                } else {
                    // Let user know the pass and confirmpass are not the same.
                    Toast.makeText(Inicio.this, R.string.confirmar,
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Inicio.this, R.string.shortPassword,
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(Inicio.this, R.string.wrongEmail,
                    Toast.LENGTH_SHORT).show();
        }
    }

    //Check format email
    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    // Onclick Event for login view
    public void autenticar(View v) {

        // Entradas para login
        usr = (EditText) findViewById(R.id.username);
        psw = (EditText) findViewById(R.id.pass);
        // Getting text from EditText
        String user = usr.getText().toString();
        String cont = psw.getText().toString();

        mAuth.signInWithEmailAndPassword(user, cont)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(Inicio.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Intent i = new Intent(getBaseContext(), MainActivity.class);
                            Log.d(TAG, String.valueOf(mAuth.getCurrentUser()));
                            i.putExtra("usuario", String.valueOf(mAuth.getCurrentUser()));
                            startActivity(i);
                        }


                    }
                });

    }

    private void setFragment(Fragment fr) {
        // create Fragment transaction
        android.support.v4.app.FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        // Adding to back stack
        fTransaction.addToBackStack(null);
        // Replacing fragment_container
        fTransaction.replace(R.id.initio, fr, fr.getTag());
        // Removing previous loaded fragment
        //getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.initio)).commit();
        // Commit so inflates the new fragment
        fTransaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.d(TAG, String.valueOf(uri));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
