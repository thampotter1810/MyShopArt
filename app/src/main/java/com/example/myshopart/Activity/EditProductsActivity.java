package com.example.myshopart.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myshopart.Models.SanPham;
import com.example.myshopart.R;
import com.example.myshopart.Services.APIService;
import com.example.myshopart.Services.DataService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProductsActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText edma, edten, edgia, edkichthuoc, edchatlieu,edsoluong;
    Button btnhuy, btnxacnhan;
    String masp = "";
    String tensp = "";
    String giaban = "";
    String kichthuoc = "";
    String chatlieu = "";
    String soluong = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_products);

        anhXa();
        setUpToolBar();
        getData();
        onClick();
    }

    private void getData() {
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("product");
        masp = sanPham.getMasanpham();
        tensp = sanPham.getTensanpham();
        giaban = sanPham.getGiaban();
        kichthuoc = sanPham.getKichthuoc();
        chatlieu = sanPham.getChatkieu();
        soluong = sanPham.getSoluong();

        edma.setText(masp);
        edten.setText(tensp);
        edgia.setText(giaban);
        edkichthuoc.setText(kichthuoc);
        edchatlieu.setText(chatlieu);
        edsoluong.setText(soluong);
    }

    private void onClick() {
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editProduct(masp, tensp, giaban, kichthuoc, chatlieu, soluong);
            }
        });
    }

    private void editProduct(String ma, String ten, String gia, String kichthuoc, String chatlieu, String soluong) {
        DataService dataService = APIService.getService();
        Call<Integer> callback = dataService.EditProduct(ma, ten, gia, kichthuoc, chatlieu, soluong);
        callback.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.body() == 1){
                    Toast.makeText(EditProductsActivity.this, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(EditProductsActivity.this, "Chỉnh sửa không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(EditProductsActivity.this, "Vui lòng kiểm tra lại kết nối!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhXa() {
        toolbar = findViewById(R.id.toolbaredit);
        edma = findViewById(R.id.ededitmasanpham);
        edten = findViewById(R.id.ededittensp);
        edgia = findViewById(R.id.ededitgiaban);
        edkichthuoc = findViewById(R.id.ededitkichthuoc);
        edsoluong = findViewById(R.id.ededitsoluong);
        edchatlieu = findViewById(R.id.ededitchatlieu);
        btnhuy = findViewById(R.id.btnedithuy);
        btnxacnhan = findViewById(R.id.btneditxacnhan);
    }
}
