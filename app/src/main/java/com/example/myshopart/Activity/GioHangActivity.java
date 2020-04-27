package com.example.myshopart.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.myshopart.Adapters.GioHangAdapter;
import com.example.myshopart.R;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvKiemTra;
    static TextView tvTongGia;
    SwipeMenuListView lvGioHang;
    Button btnThanhToan, btnTiepTucMua;
    GioHangAdapter adapter;
    boolean restart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        anhxa();
        if (restart == true){
            adapter.notifyDataSetChanged();
        }
        setUpToolBar();
        getData();
        EventPlus();
        eventClickButton();
    }

    private void eventClickButton() {
        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(GioHangActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("username", MainActivity.username);
                bundle.putString("email", MainActivity.email);
                bundle.putString("avatar", MainActivity.avatar);
                intent.putExtra("datauser", bundle);
                startActivity(intent);
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("AAAAA",MainActivity.listgiohang.size() +" ");
                if (MainActivity.listgiohang.size() > 0){
                    Intent intent = new Intent(GioHangActivity.this, ThongTinKhachHangActivity.class);
                    startActivity(intent);
                    restart = true;
                    finish();
                }else{
                    Toast.makeText(GioHangActivity.this, "Giỏ hàng của bạn đang trống!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lvGioHang.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        break;
                    case 1:
                        // delete
                        final AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                        builder.setTitle("Thông báo!");
                        builder.setIcon(R.drawable.ic_alert);
                        builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?");
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (MainActivity.listgiohang.size() <= 0){
                                    Log.e("AAA","Giỏ hàng trống");
                                    tvKiemTra.setVisibility(View.VISIBLE);
                                }else {
                                    MainActivity.listgiohang.remove(position);
                                    adapter.notifyDataSetChanged();
                                    EventPlus();
                                    if (MainActivity.listgiohang.size() <= 0){
                                        tvKiemTra.setVisibility(View.VISIBLE);
                                    }else{
                                        tvKiemTra.setVisibility(View.INVISIBLE);
                                        adapter.notifyDataSetChanged();
                                        EventPlus();
                                    }
                                }
                            }
                        });
                        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.notifyDataSetChanged();
                                EventPlus();
                            }
                        });
                        builder.show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    public static void EventPlus() {
        long tongTien = 0;
        for (int i = 0; i < MainActivity.listgiohang.size(); i++){
            tongTien += MainActivity.listgiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvTongGia.setText(decimalFormat.format(tongTien)+" VND");
    }

    private void getData() {
        if (MainActivity.listgiohang.size() <= 0){

            tvKiemTra.setVisibility(View.VISIBLE);
            lvGioHang.setVisibility(View.INVISIBLE);
        }else {
            tvKiemTra.setVisibility(View.INVISIBLE);
            lvGioHang.setVisibility(View.VISIBLE);

            lvGioHang.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            swipeMenu();
        }
    }

    private void swipeMenu() {

        /**
         * Swipe direction
         */
        // Right
       // mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);

        // Left
        //mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(170);
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);

                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        lvGioHang.setMenuCreator(creator);
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

    private void anhxa() {
        toolbar = findViewById(R.id.toolbargiohang);
        tvKiemTra = findViewById(R.id.tvkiemtragiohang);
        tvTongGia = findViewById(R.id.tvtonggia);
        lvGioHang = findViewById(R.id.lvgiohang);
        btnThanhToan = findViewById(R.id.btnthanhtoan);
        btnTiepTucMua = findViewById(R.id.btntieptucmua);
        adapter = new GioHangAdapter(GioHangActivity.this,MainActivity.listgiohang);
    }
}
