package com.example.sevillano.proto_2;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sevillano.proto_2.ProductoFragment.OnListFragmentInteractionListener;
import com.example.sevillano.proto_2.dummy.DummyContent.DummyItem;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class MyProductoAdapter extends RecyclerView.Adapter<MyProductoAdapter.ViewHolder> {

    private final List<Producto> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context context;

    public MyProductoAdapter(List<Producto> items, OnListFragmentInteractionListener listener,Context context) {
        mValues = items;
        mListener = listener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_producto, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        String[] uri = mValues.get(position).getImagen();
        Picasso.with(context).load(uri[0]).into(holder.imagen);

        holder.mItem = mValues.get(position);
        holder.precio.setText(String.valueOf(mValues.get(position).getPrecio()));
        //holder.imagen.setImageResource(mValues.get(position).getImagen());
        holder.nombre.setText(mValues.get(position).getNombre());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
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
