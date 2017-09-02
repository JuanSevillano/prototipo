package com.example.sevillano.proto_2;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Inicio extends AppCompatActivity implements Login.OnFragmentInteractionListener, Principal.OnFragmentInteractionListener{

    TextView continuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


    continuar = (TextView) findViewById(R.id.continuar);
    }

    public void iniciar(View v){

        Fragment ff = Login.newInstance("juan","sevillano");
        FragmentTransaction f = getSupportFragmentManager().beginTransaction();
        f.replace(R.id.initio,ff);
        //f.add(R.id.initio,Login.newInstance("juan","sevillano"));
        f.addToBackStack(null);
        f.commit();
        //setFragment(Login.newInstance("juan","sevillano"));
    }

    public void registrar(View v){
        setFragment(Login.newInstance("juan","sevillano"));
    }

    public void explorar(View v){
        Intent i = new Intent(this,MainActivity.class);
        String location = continuar.toString();
        i.putExtra("ubicacion",location);
        startActivity(i);
    }


    private void setFragment(Fragment fr) {
        // create Fragment transaction
        android.support.v4.app.FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        // Replacing fragment_container
        fTransaction.replace(R.id.initio, fr);
        // Adding to back stack
        fTransaction.addToBackStack(null);
        // Commit so inflates the new fragment
        fTransaction.commit();

    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
