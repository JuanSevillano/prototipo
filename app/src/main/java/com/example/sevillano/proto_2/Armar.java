package com.example.sevillano.proto_2;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.sql.SQLOutput;

public class Armar extends AppCompatActivity {

    private StorageReference mStorageRef;
    VideoView video;
    MediaController mediaController;
    BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        video = (VideoView) findViewById(R.id.video);
        mediaController = new CustomController(this, video);
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://prototipo-pdg.appspot.com/video.mp4");
        nav();
        cargarVideo();
    }

    public void cargarVideo() {

        String url = "https://firebasestorage.googleapis.com/v0/b/prototipo-pdg.appspot.com/o/completo.mp4?alt=media&token=c495205f-a93b-4267-82bb-46f731ec6005";
        Uri uri = Uri.parse(url);
        video.setVideoURI(uri);
        mediaController.setAnchorView(video);
        mediaController.setMediaPlayer(video);
        video.setMediaController(mediaController);
        video.start();

    }

    public void nav() {
        navigation = (BottomNavigationView) findViewById(R.id.nav);
        //Disabling shiftmode effect
        BottomNavigationViewHelper.disableShiftMode(navigation);
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
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.mueble:
                    finish();
                    return true;
                case R.id.tresde:
                    return true;
                case R.id.armar:

                    return true;
            }
            return false;
        }

    };

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
                startActivity(new Intent(this, Car.class));
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

    @Override
    protected void onStart() {
        super.onStart();
        navigation.getMenu().getItem(2).setChecked(true);
        //navigation.setSelectedItemId(R.id.armar);
    }


}
