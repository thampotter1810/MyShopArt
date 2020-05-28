package com.example.myshopart.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myshopart.Adapters.AdapterHinhAnh;
import com.example.myshopart.Adapters.BannerAdapter;
import com.example.myshopart.Adapters.NhanXetAdapter;
import com.example.myshopart.Models.GioHang;
import com.example.myshopart.Models.NhanXet;
import com.example.myshopart.Models.SanPham;
import com.example.myshopart.R;
import com.example.myshopart.Services.APIService;
import com.example.myshopart.Services.DataService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfomationActivity extends AppCompatActivity {

    ViewPager viewPager;
    AdapterHinhAnh adapter;
    Toolbar toolbar;
    ImageView imgsp;
    TextView tvTensp, tvGiasp, tvKichThuocsp, tvLoaisp, tvTinhTrang, tvChatLieu;
    CheckBox cbm, cbl, cbxl;
    Spinner spinner;
    FloatingActionButton btnadd;
    String idsp, tensp, hinhAnh, chatLieu, loaisp, kichThuoc;
    ArrayList<String> listHinhAnhsp;
    long giasp;
    int soLuongSp;
    ImageView imgnhanxet;
    EditText ednhanxet;
    RecyclerView recyclerviewNhanXet;
    ArrayList<NhanXet> listnhanxet;
    NhanXetAdapter nhanXetAdapter;
    TextView tvtinhtrangnhanxet;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);

        anhXa();
        settingToolbar();
        getData();
        getBinhLuan();
        catchEventSpinner();
        onClickButton();
    }

    private void getBinhLuan() {
        DataService dataService = APIService.getService();
        Call<ArrayList<NhanXet>> callback = dataService.GetDanhSachBinhLuan(idsp);
        callback.enqueue(new Callback<ArrayList<NhanXet>>() {
            @Override
            public void onResponse(Call<ArrayList<NhanXet>> call, Response<ArrayList<NhanXet>> response) {
                listnhanxet = new ArrayList<>();
                listnhanxet = response.body();
                if (listnhanxet.size()<=0){
                    tvtinhtrangnhanxet.setVisibility(View.VISIBLE);
                    recyclerviewNhanXet.setVisibility(View.INVISIBLE);
                }
                nhanXetAdapter = new NhanXetAdapter(InfomationActivity.this, listnhanxet);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(InfomationActivity.this);
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerviewNhanXet.setLayoutManager(linearLayoutManager);
                recyclerviewNhanXet.setAdapter(nhanXetAdapter);
                nhanXetAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<NhanXet>> call, Throwable t) {

            }
        });
    }

    private void onClickButton() {
        imgnhanxet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noidung = ednhanxet.getText().toString();
                themNhanXet(noidung);
            }
        });
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.listgiohang.size() > 0){
                    boolean exists = false;
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    for (int i = 0; i < MainActivity.listgiohang.size();i++){
                        if (MainActivity.listgiohang.get(i).getIdSanPham().equals(idsp)){
                            MainActivity.listgiohang.get(i).setSoluong(MainActivity.listgiohang.get(i).getSoluong() + sl);
                            if (MainActivity.listgiohang.get(i).getSoluong() >= 10){
                                MainActivity.listgiohang.get(i).setSoluong(10);
                            }
                            MainActivity.listgiohang.get(i).setGiasp(giasp * MainActivity.listgiohang.get(i).getSoluong());
                            exists = true;
                        }
                    }
                    if (!exists){
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long giaMoi = soluong * giasp;
                        MainActivity.listgiohang.add(new GioHang(idsp,tensp, giaMoi, hinhAnh, soluong));
                    }
                }else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long giaMoi = soluong * giasp;
                    MainActivity.listgiohang.add(new GioHang(idsp,tensp, giaMoi, hinhAnh, soluong));

                }
                Intent intent = new Intent(InfomationActivity.this, GioHangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void themNhanXet(final String noidung) {
        if (!MainActivity.email.isEmpty()){
            DataService dataService = APIService.getService();
            Call<Integer> callback = dataService.ThemBinhLuan(idsp, MainActivity.email,noidung);
            callback.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    if (response.body() != 0){
                        listnhanxet.add(new NhanXet(response.body().toString(),MainActivity.email,noidung, idsp));
                        if (recyclerviewNhanXet.getVisibility() == View.INVISIBLE){
                            recyclerviewNhanXet.setVisibility(View.VISIBLE);
                            tvtinhtrangnhanxet.setVisibility(View.INVISIBLE);
                        }
                        nhanXetAdapter.notifyDataSetChanged();
                        Toast.makeText(InfomationActivity.this, "Thêm nhận xét thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(InfomationActivity.this, "Không thể thêm nhận xét!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {

                }
            });
            nhanXetAdapter.notifyDataSetChanged();
        }else {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_thememail);
            dialog.show();


            final EditText edemail = dialog .findViewById(R.id.eddialogemail);
            Button btnHuy = dialog.findViewById(R.id.btndialoghuy);
            Button btnOk = dialog.findViewById(R.id.btndialogok);

            btnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.email = edemail.getText().toString();
                    dialog.dismiss();
                }
            });

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void catchEventSpinner() {
        Integer[] soluong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        spinner.setAdapter(arrayAdapter);
    }

    private void getData() {
        Intent intent = getIntent();
        SanPham sanPham = (SanPham) intent.getSerializableExtra("sanpham");

        tensp = sanPham.getTensanpham();
        idsp = sanPham.getMasanpham();
        giasp = Long.parseLong(sanPham.getGiaban());
        kichThuoc = sanPham.getKichthuoc();
        loaisp = sanPham.getLoaisanpham();
        soLuongSp = Integer.parseInt(sanPham.getSoluong());
        chatLieu = sanPham.getChatkieu();
        hinhAnh = sanPham.getHinhanh();

//        Picasso.with(this).load(hinhAnh)
//                .placeholder(R.drawable.ic_loadding)
//                .error(android.R.drawable.stat_notify_error)
//                .into(imgsp);

        tvTensp.setText(tensp);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

        tvGiasp.setText(decimalFormat.format(giasp) + " VND");
//        tvKichThuocsp.setText(kichThuoc);
        tvLoaisp.setText(loaisp);
        if (Integer.parseInt(sanPham.getSoluong()) > 0){
            tvTinhTrang.setText("Còn hàng, " + soLuongSp +" sp");
            tvTinhTrang.setTextColor(Color.BLUE);
        }else {
            tvTinhTrang.setText("Hết hàng");
            tvTinhTrang.setTextColor(Color.RED);
        }

        tvChatLieu.setText(chatLieu);

        DataService dataService1 = APIService.getService();
        Call<ArrayList<String>> listHinhAnh = dataService1.GetHinhAnh(idsp);
        listHinhAnh.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                listHinhAnhsp = response.body();
                adapter = new AdapterHinhAnh(InfomationActivity.this, listHinhAnhsp);
                viewPager.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {

            }
        });
    }

    private void anhXa() {
        viewPager = findViewById(R.id.viewpagerthongtin);
        btnadd = findViewById(R.id.floattingactionbuttonadd);
        toolbar = findViewById(R.id.toolbar);
        //imgsp = findViewById(R.id.imgsanpham);
        tvTensp = findViewById(R.id.tvtensp);
        tvGiasp = findViewById(R.id.tvgia);
        cbm = findViewById(R.id.cbm);
        cbl = findViewById(R.id.cbl);
        cbxl = findViewById(R.id.cbxl);
        tvLoaisp = findViewById(R.id.tvloaisp);
        tvTinhTrang = findViewById(R.id.tvtinhtrang);
        tvChatLieu = findViewById(R.id.tvgioithieusp);
        spinner = findViewById(R.id.spinner);
        recyclerviewNhanXet = findViewById(R.id.recyclernhanxet);
        imgnhanxet = findViewById(R.id.imgguinhanxet);
        ednhanxet = findViewById(R.id.edbinhluan);
        tvtinhtrangnhanxet = findViewById(R.id.tvtinhtrangnhanxet);
    }

    private void settingToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thông tin sản phẩm");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
