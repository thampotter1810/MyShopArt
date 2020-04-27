package com.example.myshopart.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.myshopart.Adapters.QuanLySanPhamAdapter;
import com.example.myshopart.Models.SanPham;
import com.example.myshopart.R;
import com.example.myshopart.Services.APIService;
import com.example.myshopart.Services.DataService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    ArrayList<SanPham> list;
    QuanLySanPhamAdapter adapter;
    ListView listView;
    Toolbar toolbar;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        anhXa();
        setUpToolBar();
        loadData();
        onClick();
    }

    private void onClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, InfomationActivity.class);
                intent.putExtra("sanpham", list.get(position));
                startActivity(intent);
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
        listView = findViewById(R.id.lvsearch);
        toolbar = findViewById(R.id.toolbartimkiem);
        progressBar = findViewById(R.id.progressbartimkiem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);

        MenuItem menuItem = menu.findItem(R.id.menuseachview);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("tìm kiếm theo tên hoặc mã");
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        loadDataForname(newText);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void loadDataForname(String name) {
        progressBar.setVisibility(View.VISIBLE);
        DataService dataService = APIService.getService();
        Call<ArrayList<SanPham>> callback = dataService.TimKiemSanPham(name);
        callback.enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                list = new ArrayList<>();
                list = response.body();
                progressBar.setVisibility(View.INVISIBLE);
                adapter = new QuanLySanPhamAdapter(list, SearchActivity.this);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {

            }
        });
    }
    private void loadData() {
        progressBar.setVisibility(View.VISIBLE);
        DataService dataService = APIService.getService();
        Call<ArrayList<SanPham>> callback = dataService.TimKiemSanPham("");
        callback.enqueue(new Callback<ArrayList<SanPham>>() {
            @Override
            public void onResponse(Call<ArrayList<SanPham>> call, Response<ArrayList<SanPham>> response) {
                list = new ArrayList<>();
                list = response.body();
                progressBar.setVisibility(View.INVISIBLE);
                adapter = new QuanLySanPhamAdapter(list, SearchActivity.this);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<SanPham>> call, Throwable t) {

            }
        });
    }
}
