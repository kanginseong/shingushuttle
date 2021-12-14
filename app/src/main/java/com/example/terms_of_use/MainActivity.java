package com.example.terms_of_use;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView info, main, setting;

    View.OnClickListener onClickListener;

    Fragment frame_frag;
    FragmentManager frame_manager;
    FragmentTransaction frame_tran;

    public void setDefaultPage(){

        Terms_Of_UseActivity terms_of_useActivity_main = (Terms_Of_UseActivity)Terms_Of_UseActivity.terms_of_use_activity;
        if(terms_of_useActivity_main!=null){
            terms_of_useActivity_main.finish();
        }


        main.setImageResource(R.drawable.menu_main_check);

        frame_manager = getSupportFragmentManager();

        if(frame_manager.findFragmentById(R.id.contentview)==null){
            frame_tran = frame_manager.beginTransaction();
            frame_frag = new OnFrameMainActivity();
            frame_tran.add(R.id.contentview,frame_frag);
            frame_tran.commit();
        }
        else if(frame_manager.findFragmentById(R.id.contentview)!=null){
            frame_tran = frame_manager.beginTransaction();
            frame_frag = new OnFrameMainActivity();
            frame_tran.replace(R.id.contentview,frame_frag);
            frame_tran.commit();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_frame);

        info = findViewById(R.id.info_btn);
        main = findViewById(R.id.main_btn);
        setting = findViewById(R.id.setting_btn);

        setDefaultPage();

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()){

                    case R.id.info_btn:

                        if(frame_manager.findFragmentById(R.id.contentview)==null){
                            frame_tran = frame_manager.beginTransaction();
                            frame_frag = new InfoActivity();
                            frame_tran.add(R.id.contentview,frame_frag);
                            frame_tran.commit();
                        }
                        else if(frame_manager.findFragmentById(R.id.contentview)!=null){
                            frame_tran = frame_manager.beginTransaction();
                            frame_frag = new InfoActivity();
                            frame_tran.replace(R.id.contentview,frame_frag);
                            frame_tran.commit();
                        }
                        setting.setImageResource(R.drawable.menu_setting);
                        main.setImageResource(R.drawable.menu_main);
                        info.setImageResource(R.drawable.menu_info_check);
                        break;

                    case R.id.main_btn:
                        if(frame_manager.findFragmentById(R.id.contentview)==null){
                            frame_tran = frame_manager.beginTransaction();
                            frame_frag = new OnFrameMainActivity();
                            frame_tran.add(R.id.contentview,frame_frag);
                            frame_tran.commit();
                        }
                        else if(frame_manager.findFragmentById(R.id.contentview)!=null){
                            frame_tran = frame_manager.beginTransaction();
                            frame_frag = new OnFrameMainActivity();
                            frame_tran.replace(R.id.contentview,frame_frag);
                            frame_tran.commit();
                        }
                        setting.setImageResource(R.drawable.menu_setting);
                        main.setImageResource(R.drawable.menu_main_check);
                        info.setImageResource(R.drawable.menu_info);
                        break;

                    case R.id.setting_btn:
                        if(frame_manager.findFragmentById(R.id.contentview)==null){
                            frame_tran = frame_manager.beginTransaction();
                            frame_frag = new SettingActivity();
                            frame_tran.add(R.id.contentview,frame_frag);
                            frame_tran.commit();
                        }
                        else if(frame_manager.findFragmentById(R.id.contentview)!=null){
                            frame_tran = frame_manager.beginTransaction();
                            frame_frag = new SettingActivity();
                            frame_tran.replace(R.id.contentview,frame_frag);
                            frame_tran.commit();
                        }
                        setting.setImageResource(R.drawable.menu_setting_check);
                        main.setImageResource(R.drawable.menu_main);
                        info.setImageResource(R.drawable.menu_info);
                        break;
                }
            }
        };

        info.setOnClickListener(onClickListener);
        main.setOnClickListener(onClickListener);
        setting.setOnClickListener(onClickListener);
    }
}