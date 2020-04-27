package com.example.myshopart.Fragment.mainfragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myshopart.Adapters.BannerAdapter;
import com.example.myshopart.Models.SanPham;
import com.example.myshopart.R;
import com.example.myshopart.Services.APIService;
import com.example.myshopart.Services.DataService;
import com.example.myshopart.unlimit.CheckConnection;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Banner extends Fragment {
    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    ImageView img;
    BannerAdapter adapter;
    Runnable runnable;
    Handler handler;
    int curentitem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_banner,container,false);

        anhXa();
        if (CheckConnection.haveNetworkConnection(getActivity())){
            getData();
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


    private void getData() {
        DataService dataService = APIService.getService();
        Call<List<SanPham>> listCall = dataService.GetDataBanner();
        listCall.enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                ArrayList<SanPham> spquangcao = (ArrayList<SanPham>) response.body();
                adapter = new BannerAdapter(getContext(), spquangcao);
                viewPager.setAdapter(adapter);
                circleIndicator.setViewPager(viewPager);
                //circleIndicator.setAnimation();
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        curentitem = viewPager.getCurrentItem();
                        curentitem++;
                        if (curentitem >= 5){
                            curentitem = 0;
                        }
                        viewPager.setCurrentItem(curentitem, true);
                        handler.postDelayed(runnable,4500);
                    }
                };
                handler.postDelayed(runnable,4500);

            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {

            }
        });
    }

    private void anhXa() {
        viewPager = view.findViewById(R.id.viewpagerquangcao);
        circleIndicator = view.findViewById(R.id.indicatordefault);
//        img = view.findViewById(R.id.imgtest);
    }
}
