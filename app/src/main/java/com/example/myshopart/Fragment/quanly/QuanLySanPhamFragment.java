package com.example.myshopart.Fragment.quanly;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.myshopart.Activity.EditProductsActivity;
import com.example.myshopart.Activity.GioHangActivity;
import com.example.myshopart.Activity.MainActivity;
import com.example.myshopart.Adapters.QuanLySanPhamAdapter;
import com.example.myshopart.Models.SanPham;
import com.example.myshopart.R;
import com.example.myshopart.Services.APIService;
import com.example.myshopart.Services.DataService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuanLySanPhamFragment extends Fragment {

    SwipeMenuListView listView;
    ArrayList<SanPham> list;
    QuanLySanPhamAdapter adapter;

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quanlysanpham,container,false);

        anhXa();
        getData();
        onClick();
        return view;
    }

    @Override
    public void onResume() {
        getData();
        super.onResume();
    }

    public void updateFragment1ListView(){
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }

    private void onClick() {
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Intent intent = new Intent(getActivity(), EditProductsActivity.class);
                        intent.putExtra("product", list.get(position));
                        startActivity(intent);
                        break;
                    case 1:
                        // delete
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Thông báo!");
                        builder.setIcon(R.drawable.ic_alert);
                        builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?");
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //delete Products
                                //Toast.makeText(getActivity(), "okk", Toast.LENGTH_SHORT).show();

                                deleteProducts(position);
                                dialog.dismiss();

                            }
                        });
                        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                        break;
                }
                return false;
            }
        });
    }

    private void deleteProducts(int position) {
        DataService dataService = APIService.getService();
        Call<Integer> callback = dataService.DeleteProduct(list.get(position).getMasanpham());
        callback.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.body() == 1){
                    Toast.makeText(getActivity(), "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    private void getData() {
        DataService dataService = APIService.getService();
        Call<ArrayList<SanPham>> callback = dataService.GetAllProducts();
        callback.enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                list = response.body();
                adapter = new QuanLySanPhamAdapter(list, getActivity());
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                swipeMenu();
            }

            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {
                Toast.makeText(getActivity(), "Hãy kiểm tra lại kết nối mạng của bạn", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void swipeMenu() {

        /**
         * Swipe direction
         */
        // Right
        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);

        // Left
        //listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xF9,
                        0xCE)));
                // set item width
                openItem.setWidth(170);
                // set item title
                openItem.setIcon(R.drawable.ic_edit);
                openItem.setTitle("absadhva");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
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
        listView.setMenuCreator(creator);
    }

    private void anhXa() {
        listView = view.findViewById(R.id.lvquanlysanpham);
    }
}
