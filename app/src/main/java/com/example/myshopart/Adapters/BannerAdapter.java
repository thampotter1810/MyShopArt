package com.example.myshopart.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.myshopart.Activity.InfomationActivity;
import com.example.myshopart.Models.SanPham;
import com.example.myshopart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter {
    Context context;
    ArrayList<SanPham> list;

    public BannerAdapter(Context context, ArrayList<SanPham> list) {
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
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_banner, null);

        ImageView imgbackground = view.findViewById(R.id.imgviewbackgroundbanner);
        ImageView imgviewbanner = view.findViewById(R.id.imgviewbanner);
        TextView tvmabanner = view.findViewById(R.id.tvmabanner);
        TextView tvtenbanner = view.findViewById(R.id.tvnamebanner);

        Picasso.with(context).load(list.get(position).getHinhanh()).into(imgbackground);
        Picasso.with(context).load(list.get(position).getHinhanh()).into(imgviewbanner);
        tvmabanner.setText(list.get(position).getMasanpham());
        tvtenbanner.setText(list.get(position).getTensanpham());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InfomationActivity.class);
                intent.putExtra("sanpham", list.get(position));
                context.startActivity(intent);
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        throw new UnsupportedOperationException("Required method destroyItem was not overridden");
        container.removeView((View) object);
    }
}
