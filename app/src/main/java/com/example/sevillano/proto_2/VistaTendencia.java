package com.example.sevillano.proto_2;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class VistaTendencia extends AppCompatActivity {


    Tendencia tendencia;

    TextView nombre, descripcion;
    ViewPager viewPager;
    LinearLayout sliderDotsPanel;

    private int dotsCount;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_tendencia);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ten);
        setSupportActionBar(toolbar);

        // Getting texts
        nombre = (TextView) findViewById(R.id.tendencia_name);
        descripcion = (TextView) findViewById(R.id.tendencia_descripcion);

        // Getting slider and dots layout
        viewPager = (ViewPager) findViewById(R.id.sliderTendencias);
        sliderDotsPanel = (LinearLayout) findViewById(R.id.slider_dots);

        // setting texts and photos from Itemclick
        Bundle extras = getIntent().getExtras();
        String[] fotos = (String[]) extras.get("fotos");
        String desc = (String) extras.get("descripcion");
        String name = (String) extras.get("nombre");
        nombre.setText(name);
        descripcion.setText(desc);
        descripcion.setMovementMethod(new ScrollingMovementMethod());
        tendencia = new Tendencia(name,desc,fotos);
        // Creating adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(this,fotos);

        // Setting adapter
        viewPager.setAdapter(adapter);

        dotsCount = adapter.getCount();
        dots = new ImageView[dotsCount];

        for(int i = 0; i<dotsCount; i++){
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            sliderDotsPanel.addView(dots[i],params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i < dotsCount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // do s.th.
                return true;

            case R.id.user_profile:
                // Open submenu
                return true;

            case R.id.action_car:
                // Starting shopping car activity
                Intent intent = new Intent(this,Car.class);
                startActivity(intent);
                this.finish();
                return true;

            case R.id.cerrar:
                System.out.println("USUARIO = " + Inicio.user);
                FirebaseAuth.getInstance().signOut();
                // Eliminando datos guardados en el carrito
                Carrito.getInstance().getProductos().clear();
                TinyDB tinyDB = new TinyDB(this);
                tinyDB.remove("carItems");

                startActivity(new Intent(this, Inicio.class));
                this.finish();
                return true;

            case R.id.perfil:
                Intent i = new Intent(this, Perfil.class);
                startActivity(i);
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void guardar(View v){
        TinyDB tinyDB = new TinyDB(this);
        Usuario.getInstance().getGuardados().add(tendencia);
        tinyDB.putListObject("guardados", Usuario.getInstance().getGuardados());
        Toast.makeText(this,"Se ha guardado", Toast.LENGTH_SHORT).show();
    }

}
