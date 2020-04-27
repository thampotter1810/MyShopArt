package com.example.myshopart.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.myshopart.R;
import com.example.myshopart.Services.APIService;
import com.example.myshopart.Services.DataService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddInformationActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText edname, edsdt, eddiachi;
    Button btnok;
    ProgressBar progressBar;
    String ten = "";
    String sdt = "";
    String dc = "";
    String username = "";
    String email = "";
    String avatar = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);

        anhxa();
        onCLick();
    }

    private void onCLick() {
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ten = edname.getText().toString();
                sdt = edsdt.getText().toString();
                dc = eddiachi.getText().toString();
                Intent intent = getIntent();
                Bundle bundle = intent.getBundleExtra("data");
                username = bundle.getString("username");
                email = bundle.getString("email");
                avatar = bundle.getString("avatar");

                if (!ten.isEmpty() && !sdt.isEmpty() && !dc.isEmpty() && !username.isEmpty()){
                    getData(ten,sdt,dc,username, email);
                }else {
                    Toast.makeText(AddInformationActivity.this, "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getData(final String ht, final String dt, final String diachi, final String user, final String email) {
        progressBar.setVisibility(View.VISIBLE);
        DataService dataService = APIService.getService();
        Call<String> callback = dataService.InsertThongTinKhachHang(ten,sdt,dc,username);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressBar.setVisibility(View.GONE);
                if (response.body().equals("ok")){
                    Toast.makeText(AddInformationActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddInformationActivity.this,MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username",user);
                    bundle.putString("email", email);
                    bundle.putString("avatar", avatar);
                    intent.putExtra("datauser",bundle);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(AddInformationActivity.this, "Hãy kiểm tra lại dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void anhxa() {
        toolbar = findViewById(R.id.toolbaraddinforuser);
        edname = findViewById(R.id.edaddhoten);
        edsdt = findViewById(R.id.edaddsdt);
        eddiachi = findViewById(R.id.edadddiachi);
        btnok = findViewById(R.id.btnxacnhan);
        progressBar = findViewById(R.id.progressbaraddinforuser);
    }
}
