package com.example.myshopart.Adapters;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class AdapterMainViewPager extends FragmentPagerAdapter {
    private ArrayList<Fragment> arrFrag = new ArrayList<>();
    private ArrayList<String> arrTitle = new ArrayList<>();

    public AdapterMainViewPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return arrFrag.get(position);
    }

    @Override
    public int getCount() {
        return arrFrag.size();
    }

    public void addFragment(Fragment fragment, String title){
        arrFrag.add(fragment);
        arrTitle.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return arrTitle.get(position);
    }
}
