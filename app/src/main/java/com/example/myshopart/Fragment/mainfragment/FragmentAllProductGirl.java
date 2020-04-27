package com.example.myshopart.Fragment.mainfragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshopart.Activity.InfomationActivity;
import com.example.myshopart.Adapters.AllProductAdapter;
import com.example.myshopart.Adapters.AllProductAdapter2;
import com.example.myshopart.Models.SanPham;
import com.example.myshopart.R;
import com.example.myshopart.Services.APIService;
import com.example.myshopart.Services.DataService;
import com.example.myshopart.unlimit.CheckConnection;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAllProductGirl extends Fragment {

    View view;
    Context context;
    AllProductAdapter2 adapter;
    RecyclerView recyclerView;
    ArrayList<SanPham> list;
    GridLayoutManager gridLayoutManager;
    int page = 1;
    ProgressBar progressBar;
    boolean isLoading = false;
    int currentItem, totalItem, scrollOutItem;
    mHandler mHandler;
    boolean limitData = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_products,null);
        anhxa();
        if (CheckConnection.haveNetworkConnection(getActivity())){
            getData(page);
            loadMoreData();
        }else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Thông báo")
                    .setMessage("Hãy kiểm tra lại kết nối internet của bạn!!")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            getActivity().finish();
                            getActivity().startActivity(getActivity().getIntent());
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(R.drawable.ic_alert)
                    .show();
            CheckConnection.ShowAlert(getActivity(),"Hãy kiểm tra lại kết nối của bạn");
        }
        return view;
    }

    private void loadMoreData() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItem = gridLayoutManager.getChildCount();
                totalItem = gridLayoutManager.getItemCount();

                scrollOutItem = gridLayoutManager.findFirstVisibleItemPosition();
                //Toast.makeText(getActivity(), ""+currentItem + " "+totalItem + " "+ scrollOutItem, Toast.LENGTH_SHORT).show();
                if (!isLoading && (currentItem + scrollOutItem == totalItem) && limitData == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    public class mHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    //progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Đang tải dữ liệu vui lòng chờ", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    getData(++page);
                    isLoading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }

    private void anhxa() {
        recyclerView = view.findViewById(R.id.recyclerviewall);
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mHandler = new mHandler();
        list = new ArrayList<>();
        progressBar = view.findViewById(R.id.progressbar);
    }

    private void getData(int page) {
        progressBar.setVisibility(View.VISIBLE);
        DataService dataService = APIService.getService();
        Call<List<SanPham>> listCall = dataService.GetAllProductsGirl(page);
        listCall.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                if (response != null && !response.body().isEmpty()){
                    //ArrayList<SanPham> sanPhams = (ArrayList<SanPham>) response.body();
                    list.addAll(response.body());
                    gridLayoutManager = new GridLayoutManager(getContext(),2);
                    gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    adapter = new AllProductAdapter2(list,getContext());
                    recyclerView.setLayoutManager(gridLayoutManager);
                    adapter.notifyDataSetChanged();
                    adapter.setOnItemClickListener(new AllProductAdapter2.ItemClickListener() {
                        @Override
                        public void onItemClick(int position, String masp) {
                            Intent intent = new Intent(getActivity(), InfomationActivity.class);
                            intent.putExtra("sanpham", list.get(position));
                            startActivity(intent);
                        }
                    });
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setAdapter(adapter);
                }else {
                    limitData = true;
                    CheckConnection.ShowAlert(getActivity(),"Đã hết dữ liệu");
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                Toast.makeText(getContext(), "không thể tải dữ liệu từ server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
