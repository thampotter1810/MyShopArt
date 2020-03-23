package com.example.myshopart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshopart.Models.SanPham;
import com.example.myshopart.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AllProductAdapter extends RecyclerView.Adapter<AllProductAdapter.ViewHolder>{
    ArrayList<SanPham> list;
    Context context;

    public AllProductAdapter(ArrayList<SanPham> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_row_all, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(context).load(list.get(position).getHinhanh()).into(holder.imgProduct);
        holder.tvNameProduct.setText(list.get(position).getTensanpham());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        long gia = Long.parseLong(list.get(position).getGiaban());
        holder.tvPrinceProduct.setText(decimalFormat.format(gia) + " VND");
    }

    @Override
    public int getItemCount() {
        return list != null ?list.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvNameProduct, tvPrinceProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgproduct);
            tvNameProduct = itemView.findViewById(R.id.tvnameproduct);
            tvPrinceProduct = itemView.findViewById(R.id.tvprince);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    listener.onItemClick(position,list.get(position).getMasanpham() );
                }
            });
        }
    }
    public interface ItemClickListener{
        void onItemClick(int position, String masp);
    }
    public ItemClickListener listener;

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.listener = itemClickListener;
    }
}
