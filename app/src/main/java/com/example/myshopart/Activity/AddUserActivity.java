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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserActivity extends AppCompatActivity {
    
    Toolbar toolbar;
    EditText edusername, edpass, edpass2, edemail;
    Button btnAdd;
    ProgressBar progressBar;

    String username = "";
    String pass = "";
    String pass2 = "";
    String email = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        
        anhxa();
        setUpActionBar();
        onClick();

    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void onClick() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = edusername.getText().toString();
                pass = edpass.getText().toString();
                pass2 = edpass2.getText().toString();
                email = edemail.getText().toString();

                if (!username.isEmpty() && !pass.isEmpty() && !pass2.isEmpty() && !email.isEmpty()){
                    if (pass.equals(pass2)) {
                        if (isEmailValid(email)){
                            getData(username, email);
                        }else {
                            Toast.makeText(AddUserActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(AddUserActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AddUserActivity.this, "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getData(final String username, final String mail) {
        progressBar.setVisibility(View.VISIBLE);
        DataService dataService = APIService.getService();
        Call<String> callback = dataService.InsertKhachHang(username, pass, email);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                progressBar.setVisibility(View.GONE);
                if (response.body().equals("ok")){
                    Toast.makeText(AddUserActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddUserActivity.this, AddInformationActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    bundle.putString("email",mail);
                    bundle.putString("avatar", "");
                    intent.putExtra("data", bundle);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(AddUserActivity.this, "Tài khoản này đã tồn tại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void anhxa() {
        toolbar = findViewById(R.id.toolbardangky);
        edemail = findViewById(R.id.edaddemail);
        edpass = findViewById(R.id.edaddpassword);
        edpass2 = findViewById(R.id.edaddpassword2);
        edusername = findViewById(R.id.edadduser);
        btnAdd = findViewById(R.id.btnsignup);
        progressBar = findViewById(R.id.progressbardangky);
    }
}
