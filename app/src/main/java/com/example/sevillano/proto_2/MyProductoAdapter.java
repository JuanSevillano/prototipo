package com.example.sevillano.proto_2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sevillano.proto_2.ProductoFragment.OnListFragmentInteractionListener;
import com.example.sevillano.proto_2.dummy.DummyContent.DummyItem;

import java.util.List;


public class MyProductoAdapter extends RecyclerView.Adapter<MyProductoAdapter.ViewHolder> {

    private final List<Producto> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyProductoAdapter(List<Producto> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_producto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.precio.setText(String.valueOf(mValues.get(position).getPrecio()));
        holder.imagen.setImageResource(mValues.get(position).getImagen());
        holder.nombre.setText(mValues.get(position).getNombre());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
       // public final TextView mIdView;
        public final TextView nombre, precio;
        public final ImageView imagen;
        public Producto mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            //mIdView = (TextView) view.findViewById(R.id.id);
            nombre = (TextView) view.findViewById(R.id.product_nombre);
            precio = (TextView) view.findViewById(R.id.product_precio);
            imagen = (ImageView) view.findViewById(R.id.product_img);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + nombre.getText() + "'";
        }
    }
}
