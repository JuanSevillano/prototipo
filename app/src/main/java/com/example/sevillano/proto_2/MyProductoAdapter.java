package com.example.sevillano.proto_2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.Contacts;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sevillano.proto_2.ProductoFragment.OnListFragmentInteractionListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MyProductoAdapter extends RecyclerView.Adapter<MyProductoAdapter.ViewHolder> {

    private ArrayList<Producto> mValues;
    private Context context;


    public MyProductoAdapter(ArrayList<Producto> items, Context context) {
        mValues = items;
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_producto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // CARGANDO LAS IMAGENES PARA CADA PUBLICACIÓN
        String[] uri = mValues.get(position).getImagen();
        Picasso.with(context).load(uri[0]).into(holder.imagen);

        holder.mItem = mValues.get(position);
        holder.precio.setText(String.valueOf(mValues.get(position).getPrecio()));
        //holder.imagen.setImageResource(mValues.get(position).getImagen());
        holder.nombre.setText(mValues.get(position).getNombre());

        for (int i = 0; i < Usuario.getInstance().getLikes().size(); i++) {
            if (holder.mItem.equals(Usuario.getInstance().getLikes().get(i))) {
                holder.button.setImageResource(R.drawable.ic_likeon);
            } else {
                holder.button.setImageResource(R.drawable.ic_like);
            }
        }

        // Cuando se hace click en agregar al carrito
        holder.car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Carrito car = Carrito.getInstance();
                // Se agrega el producto al arrayList del Carrito
                car.agregarProducto(mValues.get(position));
                // Saving data on cache with TinyDB
                TinyDB tinyDB = new TinyDB(context);
                // setting products
                tinyDB.putListObject("carItems", Carrito.getInstance().getProductos());
                // Setting total
                tinyDB.putFloat("total", car.precioTotal());
                // Feedback
                holder.car.setImageResource(R.drawable.ic_caron);
            }
        });
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                // Verificando si existe el usuario
                String Uid = Usuario.getInstance().getId();

                if (Uid != null) {
                    // Saving online w/ Firebase
                    database.getReference("productos").child(String.valueOf(position)).child("likes").child(Uid).setValue(Uid);

                    // Saving data on cache with TinyDB
                    TinyDB tinyDB = new TinyDB(context);
                    Usuario.getInstance().getLikes().add(holder.mItem);
                    tinyDB.putListObject("favoritos", Usuario.getInstance().getLikes());
                    notifyDataSetChanged();
                    holder.button.setImageResource(R.drawable.ic_likeon);


                } else {
                    // No hay usuario logeado por eso se le notifica
                    Toast.makeText(context, "Debe iniciar sesión", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, VistaProducto.class);
                intent.putExtra("descripcion", holder.mItem.getDescripcion());
                intent.putExtra("precio", holder.mItem.getPrecio());
                intent.putExtra("nombre", holder.mItem.getNombre());
                intent.putExtra("fotos", holder.mItem.getImagen());
                intent.putExtra("likes", holder.mItem.getLikes());
                intent.putExtra("tags", holder.mItem.getTags());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setmValues(ArrayList<Producto> mValues) {
        this.mValues = mValues;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        // public final TextView mIdView;
        public final TextView nombre, precio;
        public final ImageView imagen;
        public final ImageButton button;
        public final ImageButton car;
        public Producto mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            //mIdView = (TextView) view.findViewById(R.id.id);
            nombre = (TextView) view.findViewById(R.id.product_nombre);
            precio = (TextView) view.findViewById(R.id.product_precio);
            imagen = (ImageView) view.findViewById(R.id.product_img);
            button = (ImageButton) view.findViewById(R.id.product_fav);
            car = (ImageButton) view.findViewById(R.id.product_car);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nombre.getText() + "'";
        }
    }

}
