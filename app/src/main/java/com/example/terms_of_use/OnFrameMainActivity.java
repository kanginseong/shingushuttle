package com.example.terms_of_use;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.SharedPreferencesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.preference.ListPreference;
import androidx.viewpager.widget.ViewPager;

public class OnFrameMainActivity extends Fragment {

    ViewPager viewPager_main;

    SharedPreferences sharedPreferences;
    int pageNum=1;
    Boolean readkey;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup view_main = (ViewGroup) inflater.inflate(R.layout.onframe,container,false);

        viewPager_main = (ViewPager)view_main.findViewById(R.id.content_main);
        return  view_main;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //preferencd값 읽어오기
        Context context = getActivity();
        sharedPreferences = getActivity().getSharedPreferences("toSchoolSetting",0);
        if(sharedPreferences.contains("fromNam")){
            pageNum = 0;
        }else if(sharedPreferences.contains("fromDan")){
            pageNum = 1;
        }
//        String tsVal = sharedPreferences.getString();

        setupViewPager(viewPager_main,pageNum);
    }

    public void setupViewPager(ViewPager viewPager, int id){
        OnFrameMainActivity.ViewPagerAdapter main_adapther
                = new OnFrameMainActivity.ViewPagerAdapter(getChildFragmentManager());

        main_adapther.addFragment(new OnFrameNamActivity());
        main_adapther.addFragment(new OnFrameDanActivity());


        viewPager.setAdapter(main_adapther);
        viewPager.setCurrentItem(id);


    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        List<Fragment> fragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super (fragmentManager); }

        @NonNull
        @Override
        public Fragment getItem(int position) {
//            System.out.println("pos"+position);
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {

            return fragmentList.size();
        }

        public void addFragment(Fragment fragment){

            fragmentList.add(fragment);
        }

    }

}