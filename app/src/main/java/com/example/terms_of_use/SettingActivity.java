package com.example.terms_of_use;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class SettingActivity extends Fragment {

//    ImageView closeButton;
//    View.OnClickListener setCl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_setting,container,false);

        getChildFragmentManager().beginTransaction()
                .replace(R.id.settingView, new SettingEvent())
                .commit();

        return view;

    }
}