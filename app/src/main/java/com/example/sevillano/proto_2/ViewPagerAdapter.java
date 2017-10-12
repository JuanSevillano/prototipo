package com.example.sevillano.proto_2;

import android.content.Context;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sevillano.proto_2.R;

/*
 * Created by Juan D. Sevillano Giraldo on 9/10/17.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer[] images = {R.drawable.ic_dashboard_black_24dp, R.drawable.ic_home_black_24dp, R.drawable.ic_favouite};


    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // Creando la vista a través del inflater que se desarrolla
        View view = layoutInflater.inflate(R.layout.custom_layout, null);
        // Accediendo al imageView del custom layout
        ImageView imageView = view.findViewById(R.id.img_slider);
        // Pasando el contenido al slider
        imageView.setImageResource(images[position]);
        // creating viewpager
        ViewPager viewPager = (ViewPager) viewGroup;
        viewPager.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);

    }
}
