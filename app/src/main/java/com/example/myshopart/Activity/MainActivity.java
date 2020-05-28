package com.example.myshopart.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;

import com.example.myshopart.Models.GioHang;
import com.example.myshopart.Models.ThongTinKhachHang;
import com.example.myshopart.R;
import com.example.myshopart.Services.APIService;
import com.example.myshopart.Services.DataService;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment = null;
    private FragmentManager fragmentManager;

    public static ArrayList<GioHang> listgiohang;
    public static String username;
    public static String email;
    public static String hoten;
    public static String sdt;
    public static String diachi;
    public static String avatar;

    ImageView imgavatar;
    TextView tvhoten, tvemail;
    NavigationView navigationView;
    Button btndangxuat;
    View header;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, GioHangActivity.class);
                startActivity(intent);
            }
        });
        if (listgiohang != null){

        }else {
            listgiohang = new ArrayList<>();
        }
        //get data user
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("datauser");
        username = bundle.getString("username");
        email = bundle.getString("email");
        avatar = bundle.getString("avatar");
        //Toast.makeText(MainActivity.this, ""+email, Toast.LENGTH_SHORT).show();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        header = navigationView.getHeaderView(0);

        anhXa();
        tvemail.setText(email);
        if (!username.isEmpty() && !email.isEmpty()){
            getInforUser();
        }else {
            tvhoten.setText("Bạn chưa đăng nhập");
        }

        if (!avatar.isEmpty()){
            Picasso.with(MainActivity.this).load(avatar).into(imgavatar);
        }

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        if (email.equals("")){
            btndangxuat.setVisibility(View.INVISIBLE);
        }
        logOutOnclick();

    }

    private void logOutOnclick() {
        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangXuat();
            }
        });
    }

    private void dangXuat() {
        if (LoginActivity.ok == 1){
            imgavatar.setImageResource(R.drawable.ic_launcher_foreground_21);
            tvhoten.setText("Bạn chưa đăng nhập");
            tvemail.setText("");
            username = "";
            email = "";
            hoten = "";
            sdt = "";
            diachi = "";
            avatar = "";
            Toast.makeText(MainActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
        }else {
            imgavatar.setImageResource(R.drawable.ic_launcher_foreground_21);
            tvhoten.setText("Bạn chưa đăng nhập");
            tvemail.setText("");
            username = "";
            email = "";
            hoten = "";
            sdt = "";
            diachi = "";
            avatar = "";
            Toast.makeText(MainActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
        }
    }

    private void getInforUser() {
        DataService dataService = APIService.getService();
        Call<ArrayList<ThongTinKhachHang>> callback = dataService.GetInforKhachHang(username);
        callback.enqueue(new Callback<ArrayList<ThongTinKhachHang>>() {
            @Override
            public void onResponse(Call<ArrayList<ThongTinKhachHang>> call, Response<ArrayList<ThongTinKhachHang>> response) {
                ArrayList<ThongTinKhachHang> list = response.body();
                ThongTinKhachHang ttkh = list.get(0);
                hoten = ttkh.getHoten();
                diachi = ttkh.getDiachi();
                sdt = ttkh.getSdt();
                tvhoten.setText(hoten);
                //Toast.makeText(MainActivity.this, ""+hoten, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<ArrayList<ThongTinKhachHang>> call, Throwable t) {
                Log.e("LOIIII",t.getMessage());
            }
        });
    }

    private void anhXa() {
        imgavatar = header.findViewById(R.id.imageView);
        tvemail = header.findViewById(R.id.tvemaildadangnhap);
        tvhoten = header.findViewById(R.id.tvhotendadangnhap);
        btndangxuat = navigationView.findViewById(R.id.btnlogout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_manager);
        if (MainActivity.email.equals("thamxinh1802@gmail.com")){
            menuItem.setVisible(true);
        }else {
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuseachview){
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        }
        if (item.getItemId()== R.id.menu_manager){
            //Toast.makeText(this, "abc", Toast.LENGTH_SHORT).show();
            if (MainActivity.email.equals("thamxinh1802@gmail.com")){
                Intent intent = new Intent(MainActivity.this,QuanLyActivity.class);
                startActivity(intent);
            }else {

                Toast.makeText(MainActivity.this, "Chức năng này chỉ dành cho admin", Toast.LENGTH_SHORT).show();
            }
        }
        if (item.getItemId() == R.id.menu_donhang){
            Intent intent = new Intent(MainActivity.this, DonHangActivity.class);
            intent.putExtra("email", email+"");
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

//    private void displayView(int position) {
//        fragment = null;
//        String fragmentTags = "";
//        switch (position) {
//            case 0:
//                fragment = new FragmentSearch();
//                break;
//
//            default:
//                break;
//        }
//
//        if (fragment != null) {
//            fragmentManager = getFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, fragmentTags).commit();
//        }
//    }
}
