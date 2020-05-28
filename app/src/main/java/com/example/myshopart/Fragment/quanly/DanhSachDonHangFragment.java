package com.example.myshopart.Fragment.quanly;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myshopart.Activity.ChiTietDonHangActivity;
import com.example.myshopart.Adapters.DonHangAdapter;
import com.example.myshopart.Models.DonHang;
import com.example.myshopart.R;
import com.example.myshopart.Services.APIService;
import com.example.myshopart.Services.DataService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachDonHangFragment extends Fragment {

    View view;
    ListView listView;
    ArrayList<DonHang> list;
    DonHangAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_danhsachdonhang,container,false);

        anhXa();
        getData();
        onClick();
        return view;
    }

    private void onClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChiTietDonHangActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("key", 1);
                bundle.putSerializable("donhang", list.get(position));
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
        });
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<ArrayList<DonHang>>  callback = dataService.GetDanhSachDonHang();
        callback.enqueue(new Callback<ArrayList<DonHang>>() {
            @Override
            public void onResponse(Call<ArrayList<DonHang>> call, Response<ArrayList<DonHang>> response) {
                list = response.body();
                adapter = new DonHangAdapter(getActivity(),list);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<DonHang>> call, Throwable t) {

            }
        });
    }

    private void anhXa() {
        listView = view.findViewById(R.id.lvdonhang);
    }
}
