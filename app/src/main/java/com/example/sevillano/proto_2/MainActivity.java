package com.example.sevillano.proto_2;

import android.content.ClipData;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sevillano.proto_2.dummy.DummyContent;
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

public class MainActivity extends AppCompatActivity implements ProductoFragment.OnListFragmentInteractionListener, FiltroFragment.OnFragmentInteractionListener, TendenciaFragment.OnListFragmentInteractionListener {

    private static final String TAG = "[ - MAIN ACTIVIY - ]";
    private BottomNavigationView navigation;
    // Firebase
    private StorageReference mStorageRef;
    FirebaseUser usuario;
    static ArrayList<String> likes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        likes = new ArrayList<String>();
        usuario = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference loadingLike = FirebaseDatabase.getInstance().getReference("like").child(usuario.getUid());

        if(loadingLike != null){
            System.out.println("User not null");

            loadingLike.addValueEventListener(new ValueEventListener() {
                @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                  /* for (DataSnapshot mylikes: dataSnapshot.getChildren() ){
                        String currentLike = mylikes.getValue()+"";
                        //System.out.println(currentLike);
                        likes.add(currentLike);

                    } System.out.println(likes);*/

                }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        }

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
        getSupportFragmentManager().beginTransaction().add(R.id.content, ProductoFragment.newInstance(1), "A").commit();
        getSupportFragmentManager().beginTransaction().add(R.id.filtros, FiltroFragment.newInstance(), "F").commit();
        // Getting storage
        mStorageRef = FirebaseStorage.getInstance().getReference();
        // Write a message to the database

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(ProductoFragment.newInstance(1));
                    return true;
                case R.id.navigation_dashboard:
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.PDG.RANochero");
                    if (launchIntent != null) {
                        startActivity(launchIntent);
                    }
                    return true;
                case R.id.navigation_notifications:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, TendenciaFragment.newInstance(), "T").commit();
                    getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("F"));
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

    public void favorito(View v) {

        Bundle b = getIntent().getExtras();
        String[] photos = (String[]) b.get("fotos");
        System.out.println(photos[0]);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("like").child(usuario.getUid());
        //Map<String, String> userData = new HashMap<String, String>();
        //userData.put(user, " ");



        if(likes.contains(usuario.getUid())){
            likes.remove(usuario.getUid());
            Toast.makeText(MainActivity.this, "Dislike",
                    Toast.LENGTH_SHORT).show();
        }else{
            likes.add(usuario.getUid());
            myRef.setValue(likes);
            //Toast.makeText(getBaseContext(), "LIKED", Toast.LENGTH_SHORT);
            //myRef.setValue(v.getId(),FirebaseAuth.getInstance().getCurrentUser().getUid());
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Toast.makeText(MainActivity.this, "Like",
                            Toast.LENGTH_SHORT).show();
                    System.out.println(dataSnapshot.getValue());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    public void onListFragmentInteraction(Producto item) {

        // Every time a Product is clicked into recyclerView
        Intent intent = new Intent(getBaseContext(), VistaProducto.class);

        intent.putExtra("fotos", item.getImagen());

        startActivity(intent);

        //Toast.makeText(getBaseContext(), "Clicked Position = " + item.getNombre(), Toast.LENGTH_SHORT).show();

    }




    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(Tendencia item) {

        Intent i = new Intent(getBaseContext(), VistaTendencia.class);
        i.putExtra("fotos", item.getSrc());
        startActivity(i);

    }


}


