package com.example.myshopart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myshopart.Models.DonHang;
import com.example.myshopart.R;

import java.util.ArrayList;

public class DonHangAdapter extends BaseAdapter {
    Context context;
    ArrayList<DonHang> list;

    public DonHangAdapter(Context context, ArrayList<DonHang> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_danhsachdonhang, null);

            viewHolder.tvHoTen = view.findViewById(R.id.tvtendonhang);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        DonHang donHang = (DonHang) getItem(position);
        viewHolder.tvHoTen.setText(donHang.getTenkhachhang()+"");
        return view;
    }

    public class ViewHolder{
        TextView tvHoTen;
    }
}
