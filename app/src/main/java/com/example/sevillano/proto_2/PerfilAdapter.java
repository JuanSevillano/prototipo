package com.example.sevillano.proto_2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Sevi on 29/11/17.
 */

class PerfilAdapter extends RecyclerView.Adapter<PerfilAdapter.ViewHolder> {

    Context context;
    ArrayList<Object> mValues;

    public PerfilAdapter(Context context, ArrayList<Object> elegidos) {
        this.context = context;
        this.mValues = elegidos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.per_item, parent, false);
        // Entregando la vista al ViewHolder
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {

        if (mValues.get(position) instanceof Producto) {
            final Producto p = (Producto) mValues.get(position);
            String[] uri = p.getImagen();
            Picasso.with(context).load(uri[0]).into(holder.imagen);

            // envio a la vista detallada desde el carrito
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context,VistaProducto.class);
                    i.putExtra("descripcion", p.getDescripcion());
                    i.putExtra("precio", p.getPrecio());
                    i.putExtra("nombre", p.getNombre());
                    i.putExtra("fotos", p.getImagen());
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);
                }
            });

            // Eliminando los items del arreglo
            holder.cerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Eliminando item del carro y notificando al array
                    mValues.remove(position);
                    notifyDataSetChanged();
                    TinyDB tinyDB = new TinyDB(context);
                    // Se elimina del carro que hay local durante la app para agregarlo
                    Usuario.getInstance().setLikes(mValues);
                    tinyDB.putListObject("favoritos", mValues);
                }
            });

        } else if (mValues.get(position) instanceof Tendencia) {


            final Tendencia t = (Tendencia) mValues.get(position);
            String[] uri = t.getSrc();
            Picasso.with(context).load(uri[0]).into(holder.imagen);

            // envio a la vista detallada desde el carrito
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(context,VistaTendencia.class);
                    i.putExtra("descripcion", t.getDescripcion());
                    i.putExtra("nombre", t.getNombre());
                    i.putExtra("fotos", t.getSrc());
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(i);

                }
            });

            // Eliminando los items del arreglo
            holder.cerrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Eliminando item del carro y notificando al array
                    mValues.remove(position);
                    TinyDB tinyDB = new TinyDB(context);
                    // Se elimina del carro que hay local durante la app para agregarlo
                    Usuario.getInstance().setGuardados(mValues);
                    tinyDB.putListObject("guardados", Usuario.getInstance().getGuardados());
                    notifyDataSetChanged();

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public ImageView imagen;
        public ImageButton cerrar;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            imagen = mView.findViewById(R.id.per_imagen);
            cerrar = mView.findViewById(R.id.per_cerrar);
        }
    }
}
