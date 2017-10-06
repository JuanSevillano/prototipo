package com.example.sevillano.proto_2;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sevillano.proto_2.dummy.DummyContent;
import com.example.sevillano.proto_2.dummy.DummyContent.DummyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TendenciaFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private static int mColumnCount = 2;
    private OnListFragmentInteractionListener mListener;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TendenciaFragment() {
    }

    // TODO: Customize parameter initialization
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tendencia_list, container,false);
        Context context = view.getContext();
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        // Mientras se crean los datos localmente
        recyclerView.setHasFixedSize(true);
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
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
        //rv.setLayoutManager(glm);
        recyclerView.setLayoutManager(glm);
        List<Tendencia> gaggeredList = getListItemData();
        SolventRecyclerViewAdapter rcAdapter = new SolventRecyclerViewAdapter(context, gaggeredList);
        Log.d("HABLAME----------------", "ESTO ESTA PASANDO");
        recyclerView.setAdapter(rcAdapter);

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
