package com.example.sevillano.proto_2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;


public class SolventRecyclerViewAdapter extends RecyclerView.Adapter<SolventRecyclerViewAdapter.SolventViewHolders> {

    private List<Tendencia> itemList;
    private final TendenciaFragment.OnListFragmentInteractionListener mListener;
    private Context context;

    public SolventRecyclerViewAdapter(Context context, List<Tendencia> itemList, TendenciaFragment.OnListFragmentInteractionListener listener) {
        this.itemList = itemList;
        this.context = context;
        this.mListener = listener;
    }

    @Override
    public SolventViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.solvent_list, null);
        SolventViewHolders rcv = new SolventViewHolders(layoutView);

        return rcv;
    }

    @Override
    public void onBindViewHolder(final SolventViewHolders holder, int position) {
        holder.mItem = itemList.get(position);
        String[] uri = itemList.get(position).getSrc();
        Picasso.with(context).load(uri[0]).into(holder.countryPhoto);
        holder.countryName.setText(itemList.get(position).getNombre());
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
        return this.itemList.size();
    }

    public class SolventViewHolders extends RecyclerView.ViewHolder {

        public final TextView countryName;
        public final ImageView countryPhoto;
        public final View mView;
        public Tendencia mItem;

        public SolventViewHolders(View itemView) {
            super(itemView);
            mView = itemView;
            countryName = (TextView) itemView.findViewById(R.id.country_name);
            countryPhoto = (ImageView) itemView.findViewById(R.id.country_photo);
        }

    }
}