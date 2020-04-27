package com.example.myshopart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myshopart.Models.ChiTietDonHang;
import com.example.myshopart.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietDonHangAdapter extends BaseAdapter {

    Context context;
    ArrayList<ChiTietDonHang> list;

    public ChiTietDonHangAdapter(Context context, ArrayList<ChiTietDonHang> list) {
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_chitiet, null);
            holder = new ViewHolder();
            holder.imgchitiet = view.findViewById(R.id.imgchitiet);
            holder.tvtensp = view.findViewById(R.id.tvtenchitiet);
            holder.tvgia = view.findViewById(R.id.tvgiachitiet);
            holder.tvsoluong = view.findViewById(R.id.tvsoluongchitiet);
            view.setTag(holder);

        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        ChiTietDonHang chiTietDonHang = list.get(position);

        Picasso.with(context).load(chiTietDonHang.getHinhanh()).into(holder.imgchitiet);
        holder.tvtensp.setText(chiTietDonHang.getTenSp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.tvgia.setText("Giá: " + decimalFormat.format(Integer.parseInt(chiTietDonHang.getGiaTien()))+" VND");
        holder.tvsoluong.setText("Số lượng: " + chiTietDonHang.getSoluong());

        return view;
    }

    class ViewHolder{
        ImageView imgchitiet;
        TextView tvtensp, tvgia, tvsoluong;
    }
}
