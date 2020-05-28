package com.example.myshopart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myshopart.Models.KhachHang;
import com.example.myshopart.R;
import com.example.myshopart.Services.APIService;
import com.example.myshopart.Services.DataService;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    EditText eduser, edpass;
    Button btnDangnhap;
    TextView tvdangky;
    TextView tvboqua;
    LinearLayout linearLayout;
    ProgressBar progressBar;
    String username = "", pass = "";
    public static GoogleApiClient mGoogleSignInClient;
    int RC_SIGN_IN = 001;
    public static int ok = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        anhXa();
        onClick();

        //yeu cau nguyoi dung cung cap thong tin
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = new GoogleApiClient.Builder(LoginActivity.this).enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SignInButton signInButton = findViewById(R.id.btnlogingoogle);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                signIn();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void insertCustomer(String user, String password, String email){
        DataService dataService = APIService.getService();
        Call<String> callback = dataService.InsertKhachHang(user, password, email);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //progressBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public static void logOutgoogle(){
        Auth.GoogleSignInApi.signOut(mGoogleSignInClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
            }
        });
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()){
            ok = 1;
            GoogleSignInAccount account = result.getSignInAccount();
            Toast.makeText(LoginActivity.this, ""+account.getEmail() +" "+account.getDisplayName(), Toast.LENGTH_SHORT).show();
            insertCustomer(account.getEmail(),"123456",account.getEmail());

            checkInfor(account.getEmail(), account.getEmail(), account.getPhotoUrl().toString());
        }else {
            Toast.makeText(LoginActivity.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.e("SUCCESS", mGoogleSignInClient.isConnected() + "");
    }

    private void onClick() {
        tvdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AddUserActivity.class);
                startActivity(intent);
            }
        });
        tvboqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", "");
                bundle.putString("email", "");
                bundle.putString("avatar", "");
                intent.putExtra("datauser", bundle);
                startActivity(intent);
            }
        });
        btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.INVISIBLE);
                username = eduser.getText().toString();
                pass = edpass.getText().toString();

                if (!username.isEmpty() && !pass.isEmpty()){
                    final DataService dataService = APIService.getService();
                    Call<ArrayList<KhachHang>> callback = dataService.LoginUser(username, pass);
                    callback.enqueue(new Callback<ArrayList<KhachHang>>() {
                        @Override
                        public void onResponse(Call<ArrayList<KhachHang>> call, Response<ArrayList<KhachHang>> response) {
                            ArrayList<KhachHang> list = response.body();
                            Log.e("SIZE", list.size() +"");
                            if (list.size() > 0){
                                final String idkhachhang = list.get(0).getIdkhachhang();
                                final String email = list.get(0).getEmail();

                                linearLayout.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);

                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                checkInfor(idkhachhang,email,"");
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<KhachHang>> call, Throwable t) {
                            Log.e("ERR",t.getMessage());
                            Toast.makeText(LoginActivity.this, "Sai thông tin đăng nhập!", Toast.LENGTH_SHORT).show();
                            linearLayout.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Hãy nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkInfor(final String idkhachhang, final String email, final String avatar) {
        DataService dataService = APIService.getService();
        Call<String> stringCall = dataService.CheckDataUser(idkhachhang);
        stringCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("ok")){
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", idkhachhang);
                    bundle.putString("email", email);
                    bundle.putString("avatar", avatar);
                    intent.putExtra("datauser", bundle);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(LoginActivity.this, AddInformationActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", idkhachhang);
                    bundle.putString("email", email);
                    bundle.putString("avatar", "");
                    intent.putExtra("data", bundle);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void anhXa() {
        edpass = findViewById(R.id.edpassword);
        eduser = findViewById(R.id.edemailuser);
        btnDangnhap = findViewById(R.id.btnlogin);
        tvdangky = findViewById(R.id.tvdangky);
        tvboqua = findViewById(R.id.tvboqua);
        linearLayout = findViewById(R.id.linearlayoutlogin);
        progressBar = findViewById(R.id.progressbarlogin);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("FAIL", connectionResult+"");
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
