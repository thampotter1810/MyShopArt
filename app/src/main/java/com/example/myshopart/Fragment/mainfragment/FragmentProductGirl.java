package com.example.myshopart.Fragment.mainfragment;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshopart.Activity.InfomationActivity;
import com.example.myshopart.Adapters.AllProductAdapter;
import com.example.myshopart.Models.SanPham;
import com.example.myshopart.R;
import com.example.myshopart.Services.APIService;
import com.example.myshopart.Services.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentProductGirl extends Fragment {

    View view;
    Context context;
    AllProductAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<SanPham> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_products,null);
        anhxa();
        getData();
        return view;
    }

    private void anhxa() {
        recyclerView = view.findViewById(R.id.recyclerviewall);
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<SanPham>> listCall = dataService.GetLimitProductsGirl();
        listCall.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                list = new ArrayList<>();
                list = (ArrayList<SanPham>) response.body();
                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
                gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                adapter = new AllProductAdapter(list,getContext());
                recyclerView.setLayoutManager(gridLayoutManager);
                adapter.notifyDataSetChanged();
                adapter.setOnItemClickListener(new AllProductAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(int position, String masp) {
                        Intent intent = new Intent(getActivity(), InfomationActivity.class);
                        intent.putExtra("sanpham", list.get(position));
                        startActivity(intent);
                    }
                });
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                Toast.makeText(getContext(), "không thể tải dữ liệu từ server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
