package com.example.myshopart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.myshopart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterHinhAnh extends PagerAdapter {

    Context context;
    ArrayList<String> list;

    public AdapterHinhAnh(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_hinhanh, null);

        ImageView imgHinhanh = view.findViewById(R.id.imgpagerhinhanh);

        Picasso.with(context).load(list.get(position)).placeholder(R.drawable.ic_loadding).into(imgHinhanh);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        throw new UnsupportedOperationException("Required method destroyItem was not overridden");
        container.removeView((View) object);
    }
}
