package com.example.sevillano.proto_2;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VistaTendencia extends AppCompatActivity {


    TextView nombre, descripcion;
    ViewPager viewPager;
    LinearLayout sliderDotsPanel;
    private int dotsCount;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista_tendencia);

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
}
