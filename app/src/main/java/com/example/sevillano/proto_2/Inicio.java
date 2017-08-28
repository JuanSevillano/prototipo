package com.example.sevillano.proto_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


}
