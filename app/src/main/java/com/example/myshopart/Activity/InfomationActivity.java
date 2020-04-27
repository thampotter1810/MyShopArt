package com.example.myshopart.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.example.myshopart.Models.GioHang;
import com.example.myshopart.Models.SanPham;
import com.example.myshopart.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class InfomationActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView imgsp;
    TextView tvTensp, tvGiasp, tvKichThuocsp, tvLoaisp, tvTinhTrang, tvChatLieu;
    Button btnThemGioHang;
    Spinner spinner;
    String idsp, tensp, hinhAnh, chatLieu, loaisp, kichThuoc;
    long giasp;
    int soLuongSp;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);

        anhXa();
        settingToolbar();
        getData();
        catchEventSpinner();
        onClickButton();
    }

    private void onClickButton() {
        btnThemGioHang.setOnClickListener(new View.OnClickListener() {
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

        Picasso.with(this).load(hinhAnh)
                .placeholder(R.drawable.ic_loadding)
                .error(android.R.drawable.stat_notify_error)
                .into(imgsp);

        tvTensp.setText(tensp);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

        tvGiasp.setText(decimalFormat.format(giasp) + " VND");
        tvKichThuocsp.setText(kichThuoc);
        tvLoaisp.setText(loaisp);
        if (Integer.parseInt(sanPham.getSoluong()) > 0){
            tvTinhTrang.setText("Còn hàng, " + soLuongSp +" sp");
            tvTinhTrang.setTextColor(Color.BLUE);
        }else {
            tvTinhTrang.setText("Hết hàng");
            tvTinhTrang.setTextColor(Color.RED);
        }

        tvChatLieu.setText(chatLieu);
    }

    private void anhXa() {
        toolbar = findViewById(R.id.toolbar);
        imgsp = findViewById(R.id.imgsanpham);
        tvTensp = findViewById(R.id.tvtensp);
        tvGiasp = findViewById(R.id.tvgia);
        tvKichThuocsp = findViewById(R.id.tvkichthuoc);
        tvLoaisp = findViewById(R.id.tvloaisp);
        tvTinhTrang = findViewById(R.id.tvtinhtrang);
        tvChatLieu = findViewById(R.id.tvgioithieusp);
        btnThemGioHang = findViewById(R.id.btnthemgiohang);
        spinner = findViewById(R.id.spinner);
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
