package com.example.sevillano.proto_2;

/**
 * Created by Sevi on 6/10/17.
 */

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SolventViewHolders extends RecyclerView.ViewHolder  {

    public TextView countryName;
    public ImageView countryPhoto;
    public final View mView;


    public SolventViewHolders(View itemView) {
        super(itemView);
        mView = itemView;
        countryName = (TextView) itemView.findViewById(R.id.country_name);
        countryPhoto = (ImageView) itemView.findViewById(R.id.country_photo);
    }

}
