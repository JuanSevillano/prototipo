package com.example.sevillano.proto_2;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Inicio extends AppCompatActivity implements Login.OnFragmentInteractionListener, Principal.OnFragmentInteractionListener{

    TextView continuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_inicio);
        continuar = (TextView) findViewById(R.id.continuar);
        if(savedInstanceState == null) {
            android.app.FragmentManager manager = getFragmentManager();
            android.app.FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.initio, Principal.newInstance("Juan","sevillano"));
            transaction.setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();
        }
    }


    public void iniciar(View v){
        setFragment(Login.newInstance("juan","sevillano"));
    }

    public void registrar(View v){
        setFragment(Login.newInstance("juan","sevillano"));
    }

    public void explorar(View v){
        Intent i = new Intent(getBaseContext(),MainActivity.class);
        //String location = continuar.toString();
        //i.putExtra("ubicacion",location);
        startActivity(i);
    }


    private void setFragment(Fragment fr) {
        // create Fragment transaction
        android.support.v4.app.FragmentTransaction fTransaction = getSupportFragmentManager().beginTransaction();
        // Adding to back stack
        //fTransaction.addToBackStack(null);
        // Replacing fragment_container
        fTransaction.replace(R.id.initio, fr);
        // Removing previous loaded fragment
        getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.initio)).commit();
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
