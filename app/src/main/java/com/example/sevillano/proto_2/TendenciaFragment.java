package com.example.sevillano.proto_2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TendenciaFragment extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = "Tendencia Fragment";
    private static int mColumnCount = 2;
    private OnListFragmentInteractionListener mListener;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    // Firebase
    FirebaseDatabase database;
    DatabaseReference myRef;
    List<Tendencia> tendencias;
    RecyclerView.Adapter adapter;


    public TendenciaFragment() {
    }

    public static TendenciaFragment newInstance() {
        TendenciaFragment fragment = new TendenciaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, mColumnCount);
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
        // Deleting Filters
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tendencia_list, container, false);
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        // Mientras se crean los datos localmente
        recyclerView.setHasFixedSize(true);
        GridLayoutManager glm = new GridLayoutManager(context, 2);
        /*glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position % 3 == 2) {
                    return 3;
                }
                if(position % 5 == 0){
                    return 2;
                }
                if(position % 6 == 0){
                    return 1;
                }
                switch (position % 4) {
                    case 1:
                    case 3:
                        return 1;
                    case 0:
                    case 2:
                        return 2;
                    default:
                        //never gonna happen
                        return -1;
                }
            }
        });*/
        recyclerView.setLayoutManager(glm);
        tendencias = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("tendencias");
        // Set Adapter w/ empty List
        adapter = new SolventRecyclerViewAdapter(getContext(), tendencias, mListener);
        recyclerView.setAdapter(adapter);
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                tendencias.removeAll(tendencias);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    //GenericTypeIndicator<List<Tendencia>> t = new GenericTypeIndicator<List<Tendencia>>() {};
                    //List<Tendencia> messages = snapshot.getValue(t);
                    Tendencia tendencia = new Tendencia();
                    String name = snapshot.child("nombre").getValue(String.class);
                    String descripcion = snapshot.child("descripcion").getValue(String.class);
                    String[] str = new String[(int)snapshot.child("src").getChildrenCount()];
                    int i = 0;
                    for (DataSnapshot ds : snapshot.child("src").getChildren()) {
                        str[i] = ds.getValue(String.class);
                        i++;
                    }
                    tendencia.setDescripcion(descripcion);
                    tendencia.setNombre(name);
                    if (str[0] != null) {
                        tendencia.setSrc(str);
                    }
                    // Tendencia tendencia = snapshot.getValue(Tendencia.class);
                    //                   tendencia.
                    tendencias.add(tendencia);// = messages;

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                // do s.th.
                return true;

            case R.id.action_car:
                // Enviando a la activity del usuario
                startActivity(new Intent(getActivity(),Car.class));
                return true;

            case R.id.perfil:
                Intent i = new Intent(getContext(), Perfil.class);
                startActivity(i);
                return true;

            case R.id.cerrar:
                System.out.println("USUARIO = " + Inicio.user);
                FirebaseAuth.getInstance().signOut();
                // Eliminando datos guardados en el carrito
                Carrito.getInstance().getProductos().clear();
                startActivity(new Intent(getActivity(), Inicio.class));
                getActivity().finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Tendencia item);
    }
}
