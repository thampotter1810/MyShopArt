package com.example.myshopart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myshopart.Models.SanPham;
import com.example.myshopart.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class QuanLySanPhamAdapter extends BaseAdapter {

    ArrayList<SanPham> list;
    Context context;

    public QuanLySanPhamAdapter(ArrayList<SanPham> list, Context context) {
        this.list = list;
        this.context = context;
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
            view = inflater.inflate(R.layout.item_quanlysp, null);

            viewHolder.tvTensp = view.findViewById(R.id.tvquanlytensp);
            viewHolder.tvGiasp = view.findViewById(R.id.tvquanlygiasp);
            viewHolder.imgQuanly = view.findViewById(R.id.imgquanly);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        SanPham sanPham = list.get(position);

        viewHolder.tvTensp.setText(sanPham.getTensanpham()+"");
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        long gia = Long.parseLong(sanPham.getGiaban().toString());
        viewHolder.tvGiasp.setText(decimalFormat.format(gia) + "VND");
        Picasso.with(context).load(sanPham.getHinhanh()).error(R.drawable.ic_error)
                .placeholder(R.drawable.ic_loadding)
                .into(viewHolder.imgQuanly);
        return view;
    }

    class ViewHolder{
        ImageView imgQuanly;
        TextView tvTensp, tvGiasp;
    }
}
