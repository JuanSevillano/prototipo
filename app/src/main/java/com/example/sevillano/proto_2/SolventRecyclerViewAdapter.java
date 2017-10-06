package com.example.sevillano.proto_2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class SolventRecyclerViewAdapter  extends RecyclerView.Adapter<SolventViewHolders> {

    private List<Tendencia> itemList;
    private Context context;

    public SolventRecyclerViewAdapter(Context context, List<Tendencia> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public SolventViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.solvent_list, null);
        SolventViewHolders rcv = new SolventViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(SolventViewHolders holder, int position) {
        holder.countryName.setText(itemList.get(position).getNombre());
        holder.countryPhoto.setImageResource(itemList.get(position).getImagen());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}