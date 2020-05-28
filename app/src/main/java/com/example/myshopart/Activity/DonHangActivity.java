package com.example.myshopart.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myshopart.Adapters.DonHangAdapter;
import com.example.myshopart.Models.DonHang;
import com.example.myshopart.R;
import com.example.myshopart.Services.APIService;
import com.example.myshopart.Services.DataService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonHangActivity extends AppCompatActivity {

    String email;
    ListView lvdonhang;
    ArrayList<DonHang> listDonHang;
    DonHangAdapter adapter;
    Button btnXacNhanEmail;
    EditText edEmail;
    LinearLayout linearlayoutdonhangcanhan;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);

        anhXa();
        setUpToolbar();
        email = getIntent().getStringExtra("email");
        Toast.makeText(this, ""+email, Toast.LENGTH_SHORT).show();
        if (!email.isEmpty()){
            linearlayoutdonhangcanhan.setVisibility(View.INVISIBLE);
            getData();
        }else {
            linearlayoutdonhangcanhan.setVisibility(View.VISIBLE);
            lvdonhang.setVisibility(View.INVISIBLE);
        }
        onClick();
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void onClick() {
        btnXacNhanEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.email = edEmail.getText().toString();
                finish();

                Intent intent = new Intent(DonHangActivity.this, DonHangActivity.class);
                intent.putExtra("email", MainActivity.email+"");
                startActivity(intent);
            }
        });

        lvdonhang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DonHangActivity.this, ChiTietDonHangActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("key", 0);
                bundle.putSerializable("donhang", listDonHang.get(position));
                intent.putExtra("data", bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<ArrayList<DonHang>> callback = dataService.GetDanhSachDonHangCaNhan(email);
        callback.enqueue(new Callback<ArrayList<DonHang>>() {
            @Override
            public void onResponse(Call<ArrayList<DonHang>> call, Response<ArrayList<DonHang>> response) {
                listDonHang = response.body();
                adapter = new DonHangAdapter(DonHangActivity.this, listDonHang);
                lvdonhang.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<DonHang>> call, Throwable t) {

            }
        });
    }

    private void anhXa() {
        lvdonhang = findViewById(R.id.lvdonhangcanhan);
        edEmail = findViewById(R.id.edemaildonhang);
        btnXacNhanEmail = findViewById(R.id.btnxacnhanemaildonhang);
        linearlayoutdonhangcanhan = findViewById(R.id.linearlayoutdonhangcanhan);
        toolbar = findViewById(R.id.toolbardonhangcanhan);
    }
}
