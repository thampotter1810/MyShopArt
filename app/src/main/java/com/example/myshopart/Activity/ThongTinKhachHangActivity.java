package com.example.myshopart.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;


import com.example.myshopart.Models.ThongTinKhachHang;
import com.example.myshopart.R;
import com.example.myshopart.Services.APIService;
import com.example.myshopart.Services.DataService;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongTinKhachHangActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText edHoTen, edSdt, edDiaChi, edEmail;
    RadioButton rdPaypal, rdOffline;
    Button btnCancel, btnOk;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_khach_hang);

        anXa();
        setUpToolBar();
        edHoTen.setText(MainActivity.hoten);
        edDiaChi.setText(MainActivity.diachi);
        edEmail.setText(MainActivity.email);
        edSdt.setText(MainActivity.sdt);
        onClickButton();


    }

    private void onClickButton() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hoten = edHoTen.getText().toString();
                String sdt = edSdt.getText().toString();
                String diaChi = edDiaChi.getText().toString();
                String email = edEmail.getText().toString();
                String phuongThucThanhToan;
                if (rdPaypal.isChecked()){
                    phuongThucThanhToan = rdPaypal.getText().toString();
                }else {
                    phuongThucThanhToan = rdOffline.getText().toString();
                }
                if (!hoten.isEmpty() && !sdt.isEmpty() && !diaChi.isEmpty() && !email.isEmpty() && !phuongThucThanhToan.isEmpty()){
                    if (phuongThucThanhToan.equals("Thanh toán khi nhận hàng")){
                        showAlert();
                        //insert to database
                        //InsertDonHang();

                    }else{
                        Intent intent = new Intent();
                        //conect to paypal
                    }
                }else{
                    Toast.makeText(ThongTinKhachHangActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void showAlert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ThongTinKhachHangActivity.this);
        builder1.setMessage("Bạn có chắc chắn muốn đặt hàng??");
        builder1.setCancelable(true);
        builder1.setIcon(R.drawable.ic_alert);
        builder1.setTitle("Đặt hàng");

        builder1.setPositiveButton(
                "Đồng ý",
                new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, int id) {
                        //đặt hàng
                        InsertDonHang();
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

    private void InsertDonHang() {
        progressBar.setVisibility(View.VISIBLE);

        String hoten = "";
        String sdt = "";
        String diachi = "";
        String email = "";
        hoten = edHoTen.getText().toString();
        sdt = edSdt.getText().toString();
        diachi = edDiaChi.getText().toString();

        email = edEmail.getText().toString();

        if (!isEmailValid(email)){
            Toast.makeText(this, "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
        }else {
            DataService dataService = APIService.getService();
            Call<String> callback = dataService.InsertDonHang(hoten,sdt,diachi,email, "0");
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.e("LOIAPI", response.body()+"");
                    if (Integer.parseInt(response.body()) > 0){
                        for (int i = 0; i < MainActivity.listgiohang.size(); i++){
                            InsertChiTietDonHang(response.body(), i);
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("ERRO", t.getMessage());
                }
            });
        }

    }

    private void InsertChiTietDonHang(String iddonhang, int positon) {

        DataService dataService = APIService.getService();
        Call<Integer> callback = dataService.InsertChiTietDonHang(MainActivity.listgiohang.get(positon).getTenSanPham(),String.valueOf(MainActivity.listgiohang.get(positon).getSoluong()),String.valueOf(MainActivity.listgiohang.get(positon).getGiasp()),
                iddonhang,MainActivity.listgiohang.get(positon).getIdSanPham());
        callback.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                progressBar.setVisibility(View.INVISIBLE);
                if (response.body() == 1){
                    Toast.makeText(ThongTinKhachHangActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ThongTinKhachHangActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", MainActivity.username);
                    bundle.putString("email", MainActivity.email);
                    bundle.putString("avatar", MainActivity.avatar);
                    intent.putExtra("datauser", bundle);
                    startActivity(intent);
                    MainActivity.listgiohang.clear();
                    finish();
                }else {
                    Toast.makeText(ThongTinKhachHangActivity.this, "Không thể đặt hàng vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    private void setUpToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Thanh toán");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anXa() {
        toolbar = findViewById(R.id.toolbarthongtinkhachhang);
        edHoTen = findViewById(R.id.edhoten);
        edSdt = findViewById(R.id.edsdt);
        edDiaChi = findViewById(R.id.eddiachi);
        edEmail = findViewById(R.id.edemail);
        rdPaypal = findViewById(R.id.rdpaypal);
        rdOffline = findViewById(R.id.rdoffline);
        btnCancel = findViewById(R.id.btncancel);
        btnOk = findViewById(R.id.btnok);
        progressBar = findViewById(R.id.progressbardathang);
    }
}
