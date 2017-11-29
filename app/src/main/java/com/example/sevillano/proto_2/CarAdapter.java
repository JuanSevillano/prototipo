package com.example.sevillano.proto_2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sevi on 28/11/17.
 */

class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    Context context;
    ArrayList<Object> mValues;
    RecyclerView.OnItemTouchListener mListener;

    public CarAdapter(Context baseContext, ArrayList<Object> productos) {
        this.context = baseContext;
        this.mValues = productos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(mValues.get(position) instanceof Producto){
            final Producto p = (Producto) mValues.get(position);
            String[] uri = p.getImagen();
            Picasso.with(context).load(uri[0]).into(holder.imagen);
            holder.nombre.setText(p.getNombre());
            holder.precio.setText(p.getPrecio());
            //holder.cantidad.setText("4");
            holder.close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Eliminando item del carro y notificando al array
                    mValues.remove(position);
                    notifyDataSetChanged();
                    TinyDB tinyDB = new TinyDB(context);
                    // Se elimina del carro que hay local durante la app para agregarlo
                    Carrito.getInstance().setProductos(mValues);
                    tinyDB.putListObject("carItems", mValues);
                }
            });
            // envio a la vista detallada desde el carrito
            holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public View mView;
        public ImageView imagen;
        public TextView nombre, precio;
        public ImageButton close;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            imagen = mView.findViewById(R.id.car_img);
            nombre = mView.findViewById(R.id.car_name);
            precio = mView.findViewById(R.id.car_price);
            close = mView.findViewById(R.id.car_close);
        }
    }

}
