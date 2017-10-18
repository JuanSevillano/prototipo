package com.example.sevillano.proto_2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.example.sevillano.proto_2.dummy.DummyContent;
import com.example.sevillano.proto_2.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

public class TendenciaFragment extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";
    private static int mColumnCount = 2;
    private OnListFragmentInteractionListener mListener;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;


    public TendenciaFragment() {
    }

    @SuppressWarnings("unused")
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tendencia_list, container,false);
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        // Mientras se crean los datos localmente
        recyclerView.setHasFixedSize(true);
        GridLayoutManager glm = new GridLayoutManager(context, 3);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position % 3 == 2) {
                    return 3;
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
                        return  -1 ;
                }
            }
        });
        recyclerView.setLayoutManager(glm);
        // Llamado de datos
        List<Tendencia> gaggeredList = getListItemData();
        SolventRecyclerViewAdapter rcAdapter = new SolventRecyclerViewAdapter(context, gaggeredList);
        recyclerView.setAdapter(rcAdapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_ten,menu);
        Log.d("[----TENDENCIA----]", "Está entrando aquí");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.action_search:
                // do s.th.
                return true;

            case R.id.user_profile:
                Intent i = new Intent(getContext(),Perfil.class);
                startActivity(i);
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

    private List<Tendencia> getListItemData() {
        List<Tendencia> listViewItems = new ArrayList<Tendencia>();
        listViewItems.add(new Tendencia("Alkane", "Este sofa es hermoso y cariñoso", R.mipmap.cama_doble));
        listViewItems.add(new Tendencia("Ethane", "Este sofa es hermoso y cariñoso", R.mipmap.ten_navg));
        listViewItems.add(new Tendencia("Alkyne", "Este sofa es hermoso y cariñoso", R.mipmap.ten_navg));
        listViewItems.add(new Tendencia("Benzene", "Este sofa es hermoso y cariñoso", R.mipmap.ten_navg));
        listViewItems.add(new Tendencia("Amide", "Este sofa es hermoso y cariñoso", R.mipmap.ten_navg));
        listViewItems.add(new Tendencia("Amino Acid", "Este sofa es hermoso y cariñoso", R.mipmap.ten_navg));
        listViewItems.add(new Tendencia("Phenol", "Este sofa es hermoso y cariñoso", R.mipmap.ten_navg));
        listViewItems.add(new Tendencia("Carbonxylic", "Este sofa es hermoso y cariñoso", R.mipmap.ten_navg));
        listViewItems.add(new Tendencia("Nitril", "Este sofa es hermoso y cariñoso", R.mipmap.ten_navg));
        listViewItems.add(new Tendencia("Ether", "Este sofa es hermoso y cariñoso", R.mipmap.ten_navg));
        listViewItems.add(new Tendencia("Ester", "Este sofa es hermoso y cariñoso", R.mipmap.ten_navg));
        listViewItems.add(new Tendencia("Alcohol", "Este sofa es hermoso y cariñoso", R.mipmap.ten_navg));

        return listViewItems;
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
