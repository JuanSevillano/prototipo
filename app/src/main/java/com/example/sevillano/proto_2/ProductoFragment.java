package com.example.sevillano.proto_2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sevillano.proto_2.dummy.DummyContent;
import com.example.sevillano.proto_2.dummy.DummyContent.DummyItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ProductoFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = "LIST-ITEM";
    private static final String BUNDLE_RECYCLER_LAYOUT = "ProductFragment.recycler.layout";
    // Own variables
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private Parcelable state;
    RecyclerView recyclerView;
    SearchView searchView;
    MenuItem myActionMenuItem;
    // Firebase
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<Producto> productos;
    RecyclerView.Adapter adapter;


    public ProductoFragment() {
        // Empty but necessary constructor
    }


    @SuppressWarnings("unused")
    public static ProductoFragment newInstance(int columnCount) {
        ProductoFragment fragment = new ProductoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
        myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast like print
                Toast.makeText(getContext(), query, Toast.LENGTH_SHORT);// show( "SearchOnQueryTextSubmit: " + query);
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                // do s.th.
                return true;

            case R.id.user_profile:


                return true;
            case R.id.cerrar:
                System.out.println("USUARIO = " + Inicio.user);
                FirebaseAuth.getInstance().signOut();
                System.out.println("USUARIO1 = " + Inicio.user);
                startActivity(new Intent(getActivity(),Inicio.class));
                getActivity().finish();
                return true;

            case R.id.perfil:
                Intent i = new Intent(getActivity(), Perfil.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_producto_list, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            // restore index and position
            //recyclerView.setSelectionFromTop(index,top);
            productos = new ArrayList<>();
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("productos");
            // Set Adapter w/ empty List
            adapter = new MyProductoAdapter(productos, mListener, getContext());
            recyclerView.setAdapter(adapter);
            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    productos.removeAll(productos);

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Producto producto = new Producto();
                        producto.setNombre(snapshot.child("nombre").getValue(String.class));
                        producto.setMedidas(snapshot.child("medidas").getValue(String.class));
                        producto.setPrecio(snapshot.child("precio").getValue(String.class));
                        producto.setDescripcion(snapshot.child("descripcion").getValue(String.class));
                        String[] tags = new String[(int) snapshot.child("tags").getChildrenCount()];
                        String[] likes = new String[(int) snapshot.child("likes").getChildrenCount()];
                        String[] src = new String[(int) snapshot.child("src").getChildrenCount()];
                        int i = 0, j = 0, k = 0;


                        for (DataSnapshot ds : snapshot.child("tags").getChildren()) {
                            if (ds.getValue() != null) {
                                tags[i] = ds.getValue(String.class);
                                i++;
                            }
                        }

                        for (DataSnapshot ds : snapshot.child("likes").getChildren()) {
                            if (ds.getValue() != null) {
                                likes[j] = ds.getValue(String.class);

                                System.out.println("laics  "+likes[j]);
                                j++;
                            }
                        }

                        for (DataSnapshot ds : snapshot.child("src").getChildren()) {
                            if (ds.getValue() != null) {
                                src[k] = ds.getValue(String.class);
                                k++;
                            }
                        }
                        // Después de traer los arreglos los setea.
                        producto.setTags(tags);
                        producto.setLikes(likes);
                        producto.setSrc(src);
                        productos.add(producto);
                    }

                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
    }


    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Producto item);
    }
}
