package com.example.sevillano.proto_2;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Feed.OnFragmentInteractionListener, ProductoFragment.OnListFragmentInteractionListener, FiltroFragment.OnFragmentInteractionListener, TendenciaFragment.OnListFragmentInteractionListener {

    private static final String TAG = "[ - MAIN ACTIVIY - ]";
    private BottomNavigationView navigation;
    // Firebase
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        // Making BottomNavigation
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
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
        // Setting initial fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.content, Feed.newInstance(), "A").commit();
        }
        usuario = Usuario.getInstance();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(Feed.newInstance());
                    //getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag("F")).commit();
                    return true;
                case R.id.navigation_dashboard:
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.PDG.RANochero");
                    if (launchIntent != null) {
                        startActivity(launchIntent);
                    }
                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, TendenciaFragment.newInstance(), "T").commit();
                    //getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("F")).commit();
                    //getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag("F"));
                    //setFragment(TendenciaFragment.newInstance());
                    return true;
            }
            return false;
        }

    };

    private void setFragment(Fragment fr) {
        // Getting fragment instance from context
        android.support.v4.app.FragmentManager fManager = getSupportFragmentManager();
        // create Fragment transaction
        android.support.v4.app.FragmentTransaction fTransaction = fManager.beginTransaction();
        // Replacing fragment_container
        //fTransaction.hide(getSupportFragmentManager().findFragmentByTag("F"));
        fTransaction.replace(R.id.content, fr);
        // Adding to back stack
        fTransaction.addToBackStack(null);
        // Commit so inflates the new fragment
        fTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(intent);
    }


    @Override
    public void onListFragmentInteraction(Producto item) {

        Intent intent = new Intent(getBaseContext(), VistaProducto.class);
        intent.putExtra("descripcion", item.getDescripcion());
        intent.putExtra("precio", item.getPrecio());
        intent.putExtra("nombre", item.getNombre());
        intent.putExtra("fotos", item.getImagen());
        intent.putExtra("likes", item.getLikes());
        intent.putExtra("tags", item.getTags());
        startActivity(intent);

    }

    public void filtrar(View v) {
        Feed f = (Feed) getSupportFragmentManager().findFragmentByTag("A");
        f.filtrando(v.getId());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        System.out.println("ALOOOO : " + uri.toString());
    }

    @Override
    public void onFragmentInteraction(Producto uri) {
        System.out.println("ALOOOO : " + uri.toString());
    }

    @Override
    public void onListFragmentInteraction(Tendencia item) {
        Intent i = new Intent(getBaseContext(), VistaTendencia.class);
        i.putExtra("fotos", item.getSrc());
        i.putExtra("nombre", item.getNombre());
        i.putExtra("descripcion", item.getDescripcion());
        startActivity(i);
    }


}


