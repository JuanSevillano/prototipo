package com.example.sevillano.proto_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Feed extends Fragment {


    RecyclerView recyclerView;
    MyProductoAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Producto> productos;
    ArrayList<Producto> filtros;
    SearchView searchView;


    public Feed() {
        // Required empty public constructor
    }


    public static Feed newInstance() {
        Feed fragment = new Feed();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);

        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Toast like print
                Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();// show( "SearchOnQueryTextSubmit: " + query);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_feed, container, false);
        // Creating RecyclerView
        recyclerView = (RecyclerView) root.findViewById(R.id.producticos);
        //recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        // Set recyclerView Adapter w/ info updated.
        productos = new ArrayList<>();
        filtros = new ArrayList<>();
        adapter = new MyProductoAdapter(productos, getContext());
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("productos");
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
                            likes[j] = (ds.getValue(String.class));
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
                Log.w("FEED", "Failed to read value.", error.toException());
            }
        });
        recyclerView.setAdapter(adapter);
        return root;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                // do s.th.
                return true;

            case R.id.user_profile:
                // Open submenu
                return true;

            case R.id.action_car:
                // Starting shopping car activity
                Intent intent = new Intent(getActivity(), Car.class);
                startActivity(intent);
                return true;

            case R.id.cerrar:
                System.out.println("USUARIO = " + Inicio.user);
                FirebaseAuth.getInstance().signOut();
                // Eliminando datos guardados en el carrito
                Carrito.getInstance().getProductos().clear();
                TinyDB tinyDB = new TinyDB(getContext());
                tinyDB.remove("carItems");
                startActivity(new Intent(getActivity(), Inicio.class));
                return true;

            case R.id.perfil:
                Intent i = new Intent(getActivity(), Perfil.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Producto item);
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> p) {
        this.productos = p;
    }

    public void filtrando(int id) {
        switch (id) {
            case R.id.casa:
                adapter.setmValues(productos);
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Casa", Toast.LENGTH_SHORT).show();
                break;
            case R.id.habitacion:
                filtros.clear();
                for (int j = 0; j < productos.size(); j++) {
                    Producto p = productos.get(j);
                    for (int i = 0; i < p.getTags().length; i++) {
                        if (p.getTags()[i].contains("habitacion")) {
                            filtros.add(p);
                        }
                    }
                }
                adapter.setmValues(filtros);
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Habitacion", Toast.LENGTH_SHORT).show();
                break;
            case R.id.estudio:
                filtros.clear();
                for (int j = 0; j < productos.size(); j++) {
                    Producto p = productos.get(j);
                    for (int i = 0; i < p.getTags().length; i++) {
                        if (p.getTags()[i].contains("estudio")) {
                            filtros.add(p);
                        }
                    }
                }
                adapter.setmValues(filtros);
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Estudio", Toast.LENGTH_SHORT).show();
                break;
            case R.id.entretenimiento:
                filtros.clear();
                for (int j = 0; j < productos.size(); j++) {
                    Producto p = productos.get(j);
                    for (int i = 0; i < p.getTags().length; i++) {
                        if (p.getTags()[i].contains("entretenimiento")) {
                            filtros.add(p);
                        }
                    }
                }
                adapter.setmValues(filtros);
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Entretenimiento", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cocina:
                filtros.clear();
                for (int j = 0; j < productos.size(); j++) {
                    Producto p = productos.get(j);
                    for (int i = 0; i < p.getTags().length; i++) {
                        if (p.getTags()[i].contains("cocina")) {
                            filtros.add(p);
                        }
                    }
                }
                adapter.setmValues(filtros);
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Cocinas", Toast.LENGTH_SHORT).show();
                break;
            case R.id.puertas:
                filtros.clear();
                for (int j = 0; j < productos.size(); j++) {
                    Producto p = productos.get(j);
                    for (int i = 0; i < p.getTags().length; i++) {
                        if (p.getTags()[i].contains("puerta")) {
                            filtros.add(p);
                        }
                    }
                }
                adapter.setmValues(filtros);
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Puertas", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
