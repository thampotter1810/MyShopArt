package com.example.myshopart.Fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.myshopart.R;

public class HomeFragment extends Fragment {
    TextView tvGetAllGirl, tvGetAllBoy;
    View view;

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        view = inflater.inflate(R.layout.fragment_home, container, false);
        anhxa();
        onclick();
        return view;
    }

    private void onclick() {
        tvGetAllGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                GirlStyleFragment fragment = new GirlStyleFragment();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.nav_home,fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
            }
        });
    }

    private void anhxa() {
        tvGetAllBoy = view.findViewById(R.id.tvgetallboy);
        tvGetAllGirl = view.findViewById(R.id.tvgetallgirl);
    }

}