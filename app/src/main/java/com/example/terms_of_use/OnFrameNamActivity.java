package com.example.terms_of_use;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class OnFrameNamActivity extends Fragment {

    private ImageView namButton;
    private ImageView rightScrl_st;
    ImageView timeline,marker;
    TextView timetext;
    LinearLayout markergroup;

    public final static int REPEAT_DELAY = 10000;
    private Handler handler;

    Animation scrl_animation;
    TranslateAnimation namtranslateAnimation;

    RemainingTimeCalc time = new RemainingTimeCalc();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View namPage = (View)inflater.inflate(R.layout.onframe_nam,container,false);

        Toast.makeText(getActivity(), "준비중 입니다. 잠시만 기다려주세요",Toast.LENGTH_SHORT).show();

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        StrictMode.enableDefaults();

        rightScrl_st = namPage.findViewById(R.id.rightScroll_st);
        namButton = namPage.findViewById(R.id.nam);

        timeline = (ImageView)namPage.findViewById(R.id.timeline);
        marker = (ImageView)namPage.findViewById(R.id.marker);
        timetext = (TextView)namPage.findViewById(R.id.timetext);
        markergroup = (LinearLayout)namPage.findViewById(R.id.markergrp);

        scrl_animation = new AlphaAnimation(0.0f,1.0f);
        scrl_animation.setDuration(1000);
        scrl_animation.setStartOffset(50);
        scrl_animation.setRepeatMode(Animation.REVERSE);
        scrl_animation.setRepeatCount(Animation.INFINITE);
        rightScrl_st.startAnimation(scrl_animation);

        handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);

                time.remainingTime();
                int busTime = time.Remaining_Minutes;

                Log.d("Remaining_Minutes", "" + busTime);

                if (busTime > 10) {
                    timetext.setText(busTime + "분 전");
                    namtranslateAnimation = new TranslateAnimation(410f, 410f, 0, 0);
                    namtranslateAnimation.setDuration(60000);
                    namtranslateAnimation.setRepeatCount(Animation.INFINITE);
                    markergroup.startAnimation(namtranslateAnimation);
                } else if (busTime <= 10 && busTime >= 0) {
                    switch (busTime) {
                        case 10:
                            timetext.setText("10분 전");
                            namtranslateAnimation = new TranslateAnimation(410f, 0, 0, 0);
                            break;
                        case 9:
                            timetext.setText(busTime + "분 전");
                            namtranslateAnimation = new TranslateAnimation(370f, 0, 0, 0);
                            break;
                        case 8:
                            timetext.setText(busTime + "분 전");
                            namtranslateAnimation = new TranslateAnimation(330f, 0, 0, 0);
                            break;
                        case 7:
                            timetext.setText(busTime + "분 전");
                            namtranslateAnimation = new TranslateAnimation(290f, 0, 0, 0);
                            break;
                        case 6:
                            timetext.setText(busTime + "분 전");
                            namtranslateAnimation = new TranslateAnimation(250f, 0, 0, 0);
                            break;
                        case 5:
                            timetext.setText(busTime + "분 전");
                            namtranslateAnimation = new TranslateAnimation(210f, 0, 0, 0);
                            break;
                        case 4:
                            timetext.setText(busTime + "분 전");
                            namtranslateAnimation = new TranslateAnimation(170f, 0, 0, 0);
                            break;
                        case 3:
                            timetext.setText(busTime + "분 전");
                            namtranslateAnimation = new TranslateAnimation(130f, 0, 0, 0);
                            break;
                        case 2:
                            timetext.setText(busTime + "분 전");
                            namtranslateAnimation = new TranslateAnimation(90f, 0, 0, 0);
                            break;
                        case 1:
                            timetext.setText(busTime + "분 전");
                            namtranslateAnimation = new TranslateAnimation(50f, 0, 0, 0);
                            break;
                        case 0:
                            timetext.setText("곧 도착");
                            namtranslateAnimation = new TranslateAnimation(10f, 10f, 0, 0);
                            break;

                    }
                    namtranslateAnimation.setDuration(10000);
                    namtranslateAnimation.setRepeatCount(Animation.INFINITE);
                    markergroup.startAnimation(namtranslateAnimation);

                }

                this.sendEmptyMessageDelayed(0, REPEAT_DELAY);        // REPEAT_DELAY 간격으로 계속해서 반복하게 만들어준다

            }

        };

// 시작하고 싶은 곳에다가
//        handler.sendEmptyMessage(0);

// 중단하고 싶은 곳에다가
//        handler.removeMessage(0);

        // 정류장 위치, 내 위치 찾는 버튼
        namButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), NamStationActivity.class);
                startActivity(intent);
            }
        });

        return namPage;
    }
}
