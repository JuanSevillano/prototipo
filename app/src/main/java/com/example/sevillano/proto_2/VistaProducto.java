package com.example.sevillano.proto_2;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class VistaProducto extends AppCompatActivity {

    Producto producto;
    TextView nombre, descripcion, precio, medidas, color;
    ViewPager viewPager;
    LinearLayout sliderDotsPanel;
    BottomNavigationView navigation;
    private int dotsCount;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_producto);
        // Getting slider and dots layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.slider);
        sliderDotsPanel = (LinearLayout) findViewById(R.id.slider_dots);
        nombre = (TextView) findViewById(R.id.detail_name);
        precio = (TextView) findViewById(R.id.detail_price);
        descripcion = (TextView) findViewById(R.id.descripcion);
        medidas = (TextView) findViewById(R.id.medidas);
        color = (TextView) findViewById(R.id.color);
        navigation = (BottomNavigationView) findViewById(R.id.navegacionProducto);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setItemIconTintList(null);
        navigation.setItemTextColor(new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{}
                },
                new int[]{
                        getResources().getColor(R.color.colorPrimary),
                        getResources().getColor(R.color.detail)
                }
        ));
        Bundle b = getIntent().getExtras();
        String[] photos = (String[]) b.get("fotos");
        String[] likes = (String[]) b.get("likes");
        String[] tags = (String[]) b.get("tags");
        String nombre = (String) b.get("nombre");
        String precio = (String) b.get("precio");
        String total = (String) b.get("descripcion");
        String descripcion = total.split("Medidas")[0];
        String medidas = total.split("Medidas")[1].split("Color")[0];
        String color = total.split("Medidas")[1].split("Color")[1];
        producto = new Producto(nombre, descripcion,precio,medidas,photos,likes, tags);
        // Setting textview from bundle info
        this.nombre.setText(nombre);
        this.precio.setText(precio);
        this.color.setText("Color " + color);
        this.medidas.setText("Medidas " + medidas);
        this.descripcion.setText(descripcion);
        // Creating adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, photos);

        // Setting adapter
        viewPager.setAdapter(adapter);

        dotsCount = adapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotsPanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        navigation.setSelectedItemId(R.id.mueble);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ten, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_car:
                startActivity(new Intent(this,Car.class));
                return true;

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
                startActivity(new Intent(this, Perfil.class));
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.mueble:

                    return true;
                case R.id.tresde:

                    return true;
                case R.id.armar:
                    Intent i = new Intent(getBaseContext(), Armar.class);
                    startActivity(i);
                    return true;
            }
            return false;
        }

    };

    public void producto(View v) {

        if (v.getId() == R.id.detail_car) {

            Carrito.getInstance().agregarProducto(producto);
            // Saving data on cache with TinyDB
            TinyDB tinyDB = new TinyDB(this);
            // setting products
            tinyDB.putListObject("carItems", Carrito.getInstance().getProductos());
            Toast.makeText(this,"Agregado",Toast.LENGTH_SHORT).show();

        } else if (v.getId() == R.id.detail_fav) {

            TinyDB tinyDB = new TinyDB(this);
            Usuario.getInstance().getLikes().add(producto);
            tinyDB.putListObject("favoritos", Usuario.getInstance().getLikes());
            Toast.makeText(this,"Liked",Toast.LENGTH_SHORT).show();

        }

    }

}
