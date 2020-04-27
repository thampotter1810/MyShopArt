package com.example.myshopart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myshopart.Activity.GioHangActivity;
import com.example.myshopart.Activity.MainActivity;
import com.example.myshopart.Models.GioHang;
import com.example.myshopart.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {

    Context context;
    ArrayList<GioHang> list;

    public GioHangAdapter(Context context, ArrayList<GioHang> list) {
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
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_giohang, null);

            viewHolder.imgspGioHang = view.findViewById(R.id.imgspgiohang);
            viewHolder.tvTensp = view.findViewById(R.id.tvtenspgiohang);
            viewHolder.tvGia = view.findViewById(R.id.tvgiaspgiohang);
            viewHolder.btnminus = view.findViewById(R.id.btnminus);
            viewHolder.btnvalues = view.findViewById(R.id.btnvalues);
            viewHolder.btnplus = view.findViewById(R.id.btnplus);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        GioHang gioHang = (GioHang) getItem(position);
        Picasso.with(context).load(gioHang.getHinhsp())
                .placeholder(R.drawable.ic_loadding)
                .error(R.drawable.ic_error)
                .into(viewHolder.imgspGioHang);
        viewHolder.tvTensp.setText(gioHang.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvGia.setText(decimalFormat.format(gioHang.getGiasp()) + " VND");
        viewHolder.btnvalues.setText(gioHang.getSoluong()+"");

        int sl = Integer.parseInt(viewHolder.btnvalues.getText().toString());
        if (sl >= 10){
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }else if (sl <= 1){
            viewHolder.btnplus.setVisibility(View.VISIBLE);
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        }else if (sl >1 ){
            viewHolder.btnplus.setVisibility(View.VISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl = Integer.parseInt(finalViewHolder.btnvalues.getText().toString()) - 1;
                int slHienTai = MainActivity.listgiohang.get(position).getSoluong();
                long giaht  = MainActivity.listgiohang.get(position).getGiasp();
                MainActivity.listgiohang.get(position).setSoluong(sl);
                long giaMoi = (giaht * sl )/ slHienTai;
                MainActivity.listgiohang.get(position).setGiasp(giaMoi);

                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.tvGia.setText(decimalFormat.format(giaMoi) + " VND");
                GioHangActivity.EventPlus();
                if (sl < 2){
                    finalViewHolder.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(sl));
                }else {
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(sl));
                }
            }
        });

        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl = Integer.parseInt(finalViewHolder.btnvalues.getText().toString()) + 1;
                int slHienTai = MainActivity.listgiohang.get(position).getSoluong();
                long giaht  = MainActivity.listgiohang.get(position).getGiasp();
                MainActivity.listgiohang.get(position).setSoluong(sl);
                long giaMoi = (giaht * sl )/ slHienTai;
                MainActivity.listgiohang.get(position).setGiasp(giaMoi);

                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.tvGia.setText(decimalFormat.format(giaMoi) + " VND");
                GioHangActivity.EventPlus();
                if (sl > 9){
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(sl));
                }else {
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(sl));
                }
            }
        });
        return view;
    }
    public class ViewHolder{
        ImageView imgspGioHang;
        TextView tvTensp, tvGia;
        Button btnminus, btnvalues, btnplus;
    }
}
