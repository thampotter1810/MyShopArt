package com.example.myshopart.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.myshopart.Adapters.AdapterMainViewPager;
import com.example.myshopart.Fragment.quanly.DanhSachDonHangFragment;
import com.example.myshopart.Fragment.quanly.QuanLySanPhamFragment;
import com.example.myshopart.Fragment.quanly.UploadSanPhamFragment;
import com.example.myshopart.R;
import com.example.myshopart.unlimit.NonSwipeableViewPager;
import com.google.android.material.tabs.TabLayout;

public class QuanLyActivity extends AppCompatActivity {

    TabLayout tabLayout;
    NonSwipeableViewPager viewPager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly);

        anhXa();
        setUpToolBar();
        init();
    }

    private void init() {
        AdapterMainViewPager adapterMainViewPager = new AdapterMainViewPager(getSupportFragmentManager());
        adapterMainViewPager.addFragment(new QuanLySanPhamFragment(),"Quản lý sản phẩm");
        adapterMainViewPager.addFragment(new DanhSachDonHangFragment(),"Đơn hàng");
        adapterMainViewPager.addFragment(new UploadSanPhamFragment(),"Thêm sản phẩm");
        viewPager.setAdapter(adapterMainViewPager);
        viewPager.setPagingEnable(false);
        tabLayout.setupWithViewPager(viewPager);
        /*tabLayout.getTabAt(0).setIcon(R.drawable.icontrangchu);
        tabLayout.getTabAt(1).setIcon(R.drawable.iconsearch);*/
    }

    private void setUpToolBar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.ic_quan_ly);    //Icon muốn hiện thị
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setTitle("Quản lý sản phẩm");
    }

    private void anhXa() {
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);
        toolbar = findViewById(R.id.toolbarquanly);
    }
}
