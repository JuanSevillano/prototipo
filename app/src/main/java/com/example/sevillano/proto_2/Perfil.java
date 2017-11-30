package com.example.sevillano.proto_2;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Perfil extends AppCompatActivity {

    ImageView imageView;
    TextView userName, userEmail;
    Button gustan, guardar;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    ArrayList<Object> elegidos;
    ArrayList<Object> guard, fav;
    TinyDB tinydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        imageView = (ImageView) findViewById(R.id.user_img);
        userName = (TextView) findViewById(R.id.user_name);
        userEmail = (TextView) findViewById(R.id.user_email);
        gustan = (Button) findViewById(R.id.likes);
        guardar = (Button) findViewById(R.id.saved);
        userName.setText(Usuario.getInstance().getNombre());
        userEmail.setText(Usuario.getInstance().getEmail());



        // Recycler
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        GridLayoutManager gr = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gr);

        // Cache storage to load products previous saved
        tinydb = new TinyDB(this);
        elegidos = new ArrayList<Object>();
        fav = tinydb.getListObject("favoritos",Producto.class);
        elegidos.addAll(fav);
        adapter = new PerfilAdapter(this, elegidos);
        recyclerView.setAdapter(adapter);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_perfil, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.car_action:
                // Starting shopping car activity
                Intent intent = new Intent(this, Car.class);
                startActivity(intent);
                return true;

            case R.id.cerrar:
                System.out.println("USUARIO = " + Inicio.user);
                FirebaseAuth.getInstance().signOut();
                // Eliminando datos guardados en el carrito
                Carrito.getInstance().getProductos().clear();
                TinyDB tinyDB = new TinyDB(this);
                tinyDB.remove("carItems");
                tinyDB.remove("guardados");
                tinyDB.remove("favoritos");

                startActivity(new Intent(this, Inicio.class));
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void userGusta(View v) {

        if (v.getId() == R.id.likes) {

            elegidos.clear();
            fav = tinydb.getListObject("favoritos",Producto.class);
            elegidos.addAll(fav);
            adapter.notifyDataSetChanged();
            gustan.setBackgroundResource(R.drawable.bordes);
            guardar.setBackgroundResource(R.color.white);

        } else if (v.getId() == R.id.saved) {

            elegidos.clear();
            guard = tinydb.getListObject("guardados",Tendencia.class);
            System.out.println(guard);
            elegidos.addAll(guard);
            adapter.notifyDataSetChanged();
            guardar.setBackgroundResource(R.drawable.bordes);
            gustan.setBackgroundResource(R.color.white);


        }

    }

}
