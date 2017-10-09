package com.example.sevillano.proto_2;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
public class ProductoFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = "LIST-ITEM";
    private static final String BUNDLE_RECYCLER_LAYOUT = "ProductFragment.recycler.layout";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private Parcelable state;
    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProductoFragment() {
    }

    // TODO: Customize parameter initialization
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

            recyclerView.setAdapter(new MyProductoAdapter(getListItemData(), mListener));
        }
        return view;
    }

    private List<Producto> getListItemData() {
        List<Producto> listViewItems = new ArrayList<Producto>();
        listViewItems.add(new Producto("Cama Doble ", "Cama doble de 2x2m perfecta para pareja"
                ,"","", ""));
        listViewItems.add(new Producto("Cama Doble ", "Cama doble de 2x2m perfecta para pareja"
                ,"","", ""));
        listViewItems.add(new Producto("Cama Doble ", "Cama doble de 2x2m perfecta para pareja"
                ,"","", ""));
        listViewItems.add(new Producto("Cama Doble ", "Cama doble de 2x2m perfecta para pareja"
                ,"","", ""));
        listViewItems.add(new Producto("Cama Doble ", "Cama doble de 2x2m perfecta para pareja"
                ,"","", ""));
        listViewItems.add(new Producto("Cama Doble ", "Cama doble de 2x2m perfecta para pareja"
                ,"","", ""));
        listViewItems.add(new Producto("Cama Doble ", "Cama doble de 2x2m perfecta para pareja"
                ,"","", ""));
        listViewItems.add(new Producto("Cama Doble ", "Cama doble de 2x2m perfecta para pareja"
                ,"","", ""));
        listViewItems.add(new Producto("Cama Doble ", "Cama doble de 2x2m perfecta para pareja"
                ,"","", ""));
        listViewItems.add(new Producto("Cama Doble ", "Cama doble de 2x2m perfecta para pareja"
                ,"","", ""));
        return listViewItems;
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
        if(savedInstanceState != null) {
            Log.d(TAG,"[[[[-- RestoredView --]]]]");
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
        // TODO: Update argument type and name
        void onListFragmentInteraction(Producto item);
    }
}
