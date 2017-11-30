package com.example.sevillano.proto_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

public class Car extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    MenuItem menuItem;
    Carrito carrito;
    ArrayList<Object> productos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        //Setting toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        // Getting shopping car instance
        carrito = Carrito.getInstance();
        // Creating RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        //recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // Cache storage to load products previous saved
        TinyDB tinydb = new TinyDB(this);
        productos = tinydb.getListObject("carItems", Producto.class);
        // Set recyclerView Adapter w/ info updated.
        adapter = new CarAdapter(getBaseContext(), productos);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ten, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.cerrar:
                System.out.println("USUARIO = " + Inicio.user);
                FirebaseAuth.getInstance().signOut();
                // Eliminando datos guardados en el carrito
                Carrito.getInstance().getProductos().clear();
                TinyDB tinyDB = new TinyDB(this);
                tinyDB.remove("carItems");

                startActivity(new Intent(this, Inicio.class));
                finish();
                return true;

            case R.id.perfil:
                startActivity(new Intent(this,Perfil.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

}
