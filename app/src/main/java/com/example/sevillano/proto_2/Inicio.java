package com.example.sevillano.proto_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Inicio extends AppCompatActivity {

    TextView continuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        continuar = (TextView) findViewById(R.id.continuar);
    }

    public void explorar(View v){
        Intent i = new Intent(this,MainActivity.class);
        String location = continuar.toString();
        i.putExtra("ubicacion",location);
        startActivity(i);
    }


}
