package com.example.myshopart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshopart.Models.DonHang;
import com.example.myshopart.Models.NhanXet;
import com.example.myshopart.R;

import java.util.ArrayList;

public class NhanXetAdapter extends RecyclerView.Adapter<NhanXetAdapter.ViewHolder> {

    Context context;
    ArrayList<NhanXet> list;

    public NhanXetAdapter(Context context, ArrayList<NhanXet> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_nhanxet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvhoten.setText(list.get(position).getEmailkhachhang());
        holder.tvnoidung.setText(list.get(position).getNoidung());

    }

    @Override
    public int getItemCount() {
        return list != null ?list.size() : 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvhoten, tvnoidung;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvhoten = itemView.findViewById(R.id.tvtennguoinhanxet);
            tvnoidung = itemView.findViewById(R.id.tvnoidungnhanxet);
        }
    }

}
