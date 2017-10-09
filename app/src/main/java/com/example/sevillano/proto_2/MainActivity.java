package com.example.sevillano.proto_2;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sevillano.proto_2.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements ProductoFragment.OnListFragmentInteractionListener, FiltroFragment.OnFragmentInteractionListener, TendenciaFragment.OnListFragmentInteractionListener {

    private static final String TAG = "[--MAIN ACTIVIY--]";
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Making BottomNavigation
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //Disabling shiftmode effect
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // Setting initial fragment
        getSupportFragmentManager().beginTransaction().add(R.id.content, ProductoFragment.newInstance(1)).addToBackStack(null).commit();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            ;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    item.setIcon(R.mipmap.logo_nav);
                    setFragment(ProductoFragment.newInstance(1));
                    return true;
                case R.id.navigation_dashboard:
                    item.setIcon(R.mipmap.ra_nav);
                    return true;
                case R.id.navigation_notifications:
                    item.setIcon(R.mipmap.ten_nav);
                    setFragment(TendenciaFragment.newInstance());
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
        fTransaction.replace(R.id.content, fr);
        // Adding to back stack
        fTransaction.addToBackStack(null);
        // Commit so inflates the new fragment
        fTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count <= 1) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }
    }


    @Override
    public void onListFragmentInteraction(Producto item) {
        // Every time a Product is clicked into recyclerView
        Toast.makeText(getBaseContext(), "Clicked Position = " + item.getNombre(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}


