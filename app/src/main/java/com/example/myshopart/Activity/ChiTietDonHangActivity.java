package com.example.myshopart.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.myshopart.Adapters.ChiTietDonHangAdapter;
import com.example.myshopart.Models.ChiTietDonHang;
import com.example.myshopart.Models.DonHang;
import com.example.myshopart.R;
import com.example.myshopart.Services.APIService;
import com.example.myshopart.Services.DataService;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietDonHangActivity extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    ArrayList<ChiTietDonHang> list;
    ChiTietDonHangAdapter adapter;

    TextView tvTongtien, tvDiachi, tvSdt, tvTrangthai;
    Button btnHuy, btnXacnhan;
    DonHang donHang;
    private static final int REQUEST_PHONE_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_don_hang);

        anhXa();

        Intent intent = getIntent();
        donHang = (DonHang) intent.getSerializableExtra("donhang");
        setUpToolBar(donHang.getTenkhachhang());

        getData();
        onClick();
    }

    private void onClick() {
        tvSdt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + donHang.getSodienthoai()));
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ChiTietDonHangActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);

                }else {
                    startActivity(callIntent);
                }

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder1 = new AlertDialog.Builder(ChiTietDonHangActivity.this);
                builder1.setMessage("Bạn có chắc chắn đã xác nhận đơn hàng?");
                builder1.setCancelable(true);
                builder1.setIcon(R.drawable.ic_alert);
                builder1.setTitle("Xác nhận hoàn tất đơn hàng");

                builder1.setPositiveButton(
                        "Đồng ý",
                        new DialogInterface.OnClickListener() {
                            public void onClick(final DialogInterface dialog, int id) {
                                DataService dataService = APIService.getService();
                                Call<Integer> callback = dataService.XacNhanDonHang(donHang.getIddonhang());
                                callback.enqueue(new Callback<Integer>() {
                                    @Override
                                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                                        if (response.body() == 1){
                                            Toast.makeText(ChiTietDonHangActivity.this, "Đã xác nhận đơn hàng", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            Intent intent = new Intent(ChiTietDonHangActivity.this, QuanLyActivity.class);
                                            startActivity(intent);

                                            finish();
                                        }else {
                                            Toast.makeText(ChiTietDonHangActivity.this, "Không thành công vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Integer> call, Throwable t) {

                                    }
                                });
                            }
                        });

                builder1.setNegativeButton(
                        "Hủy",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<ArrayList<ChiTietDonHang>> callback = dataService.GetChiTietDonHang(donHang.getIddonhang());
        Toast.makeText(ChiTietDonHangActivity.this, ""+donHang.getIddonhang(), Toast.LENGTH_SHORT).show();
        callback.enqueue(new Callback<ArrayList<ChiTietDonHang>>() {
            @Override
            public void onResponse(Call<ArrayList<ChiTietDonHang>> call, Response<ArrayList<ChiTietDonHang>> response) {
                list = response.body();
                Log.e("LISST", "onResponse: "+list);
                adapter = new ChiTietDonHangAdapter(ChiTietDonHangActivity.this, list);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

                int tong = 0;
                for (int i = 0; i < list.size(); i++){
                    tong = tong + Integer.parseInt(list.get(i).getGiaTien());
                }
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

                tvTongtien.setText("Tổng tiền: " + decimalFormat.format(tong) + " VND");
                tvDiachi.setText("Địa chỉ: " + donHang.getDiachi());
                tvSdt.setText(donHang.getSodienthoai());
                if (Integer.parseInt(donHang.getTrangthai()) == 0){
                    tvTrangthai.setText("Chưa thanh toán");
                    tvTrangthai.setTextColor(Color.RED);
                }else {
                    tvTrangthai.setText("Đã thanh toán");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ChiTietDonHang>> call, Throwable t) {

            }
        });
    }

    private void setUpToolBar(String name) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Đơn hàng: " + name);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhXa() {
        toolbar = findViewById(R.id.toolbarchitiet);
        listView = findViewById(R.id.lvchitiet);
        tvTongtien = findViewById(R.id.tvtongtien);
        tvDiachi = findViewById(R.id.tvdiachi);
        tvSdt = findViewById(R.id.tvsdtchitiet);
        tvTrangthai = findViewById(R.id.tvtrangthai);
        btnHuy = findViewById(R.id.btnhuy);
        btnXacnhan = findViewById(R.id.btnxacnhanchitiet);
    }
}
