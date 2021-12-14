package com.example.terms_of_use;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.SwitchPreference;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.viewpager.widget.ViewPager;

public class SettingEvent extends PreferenceFragmentCompat {

    SwitchPreference alarmset;
    ListPreference mainset;

    Preference.OnPreferenceChangeListener changeListener;
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.fragment_setting,rootKey);
        alarmset = (SwitchPreference)findPreference("alarming");
        mainset = (ListPreference)findPreference("toSchool");

        //clickListener
        changeListener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                String value = (String)newValue;

                if(preference == alarmset){ //알람설정코드
//                    adaptAlarmSet(alarmset);
                }else if(preference == mainset){ //메인세팅코드
                    sharedPreferences = getActivity().getSharedPreferences("toSchoolSetting",0);
                    //sharedpreference 파일 toSchoolSetting을 만듬. Mode : 0은 읽기쓰기 가능
                    switch (value){
                        case "fromNam":
//                            Toast.makeText(getActivity(),"fromNam선택",Toast.LENGTH_LONG).show();
                            editor = sharedPreferences.edit();  //sharedpreference editor변수
                            editor.putBoolean("fromNam",true);  //key fromNam을 value true로 설정
                            if(sharedPreferences.contains("fromDan")){  //fromNam을 추가하고 fromDan은 삭제
                                editor.remove("fromDan");
                            }
                            editor.commit();    //editor commit
                            break;
                        case "fromDan":
//                            Toast.makeText(getActivity(),"fromDan선택",Toast.LENGTH_LONG).show();
                            editor = sharedPreferences.edit();
                            editor.putBoolean("fromDan",true);
                            if(sharedPreferences.contains("fromNam")){
                                editor.remove("fromNam");
                            }
                            editor.commit();
                            break;
                    }
                }
                return true;
            }
        };

        mainset.setOnPreferenceChangeListener(changeListener);

    }

    public void adaptAlarmSet(SwitchPreference switchPreference){
        //
        if(switchPreference.isChecked()){

        }
        else if(!(switchPreference.isChecked())){

        }
    }

    //    NotificationCompat.Builder headupAlarm_builder;
//    NotificationManager notificationManager;

//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        addPreferencesFromResource(R.xml.fragment_setting);
//
//        alarmset = (SwitchPreference)findPreference("alarming");
//        mainset = (ListPreference)findPreference("toSchool");
//
////        setting_SharedPreferences
////                = PreferenceManager.getDefaultSharedPreferences(getActivity());
//        //build.gradle(module.app) 추가
//
//
//    }

}