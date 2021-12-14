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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OnFrameDanActivity extends Fragment {

    private ImageView danButton;
    private ImageView leftScrl_st;
    ImageView timeline, marker;
    TextView timetext;
    LinearLayout markergroup;

    public final static int REPEAT_DELAY = 10000;
    private Handler handler;

    Animation scrl_animation;
    TranslateAnimation dantranslateAnimation;

    RemainingTimeCalc time = new RemainingTimeCalc();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View danPage = (View)inflater.inflate(R.layout.onframe_dan,container,false);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        StrictMode.enableDefaults();

        leftScrl_st = danPage.findViewById(R.id.leftScroll_st);
        danButton = danPage.findViewById(R.id.dan);

        timeline = (ImageView)danPage.findViewById(R.id.timeline);
        marker = (ImageView)danPage.findViewById(R.id.marker);
        timetext = (TextView)danPage.findViewById(R.id.timetext);
        markergroup = (LinearLayout)danPage.findViewById(R.id.markergrp);

        scrl_animation = new AlphaAnimation(0.0f,1.0f);
        scrl_animation.setDuration(1000);
        scrl_animation.setStartOffset(50);
        scrl_animation.setRepeatMode(Animation.REVERSE);
        scrl_animation.setRepeatCount(Animation.INFINITE);
        leftScrl_st.startAnimation(scrl_animation);

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
                    dantranslateAnimation = new TranslateAnimation(410f, 410f, 0, 0);
                    dantranslateAnimation.setDuration(60000);
                    dantranslateAnimation.setRepeatCount(Animation.INFINITE);
                    markergroup.startAnimation(dantranslateAnimation);
                } else if (busTime <= 10 && busTime >= 0) {
                    switch (busTime) {
                        case 10:
                            timetext.setText("10분 전");
                            dantranslateAnimation = new TranslateAnimation(410f, 0, 0, 0);
                            break;
                        case 9:
                            timetext.setText(busTime + "분 전");
                            dantranslateAnimation = new TranslateAnimation(370f, 0, 0, 0);
                            break;
                        case 8:
                            timetext.setText(busTime + "분 전");
                            dantranslateAnimation = new TranslateAnimation(330f, 0, 0, 0);
                            break;
                        case 7:
                            timetext.setText(busTime + "분 전");
                            dantranslateAnimation = new TranslateAnimation(290f, 0, 0, 0);
                            break;
                        case 6:
                            timetext.setText(busTime + "분 전");
                            dantranslateAnimation = new TranslateAnimation(250f, 0, 0, 0);
                            break;
                        case 5:
                            timetext.setText(busTime + "분 전");
                            dantranslateAnimation = new TranslateAnimation(210f, 0, 0, 0);
                            break;
                        case 4:
                            timetext.setText(busTime + "분 전");
                            dantranslateAnimation = new TranslateAnimation(170f, 0, 0, 0);
                            break;
                        case 3:
                            timetext.setText(busTime + "분 전");
                            dantranslateAnimation = new TranslateAnimation(130f, 0, 0, 0);
                            break;
                        case 2:
                            timetext.setText(busTime + "분 전");
                            dantranslateAnimation = new TranslateAnimation(90f, 0, 0, 0);
                            break;
                        case 1:
                            timetext.setText(busTime + "분 전");
                            dantranslateAnimation = new TranslateAnimation(50f, 0, 0, 0);
                            break;
                        case 0:
                            timetext.setText("곧 도착");
                            dantranslateAnimation = new TranslateAnimation(10f, 10f, 0, 0);
                            break;

                    }
                    dantranslateAnimation.setDuration(10000);
                    dantranslateAnimation.setRepeatCount(Animation.INFINITE);
                    markergroup.startAnimation(dantranslateAnimation);

                }

                this.sendEmptyMessageDelayed(0, REPEAT_DELAY);        // REPEAT_DELAY 간격으로 계속해서 반복하게 만들어준다

            }

        };

// 시작하고 싶은 곳에다가
        handler.sendEmptyMessage(0);

// 중단하고 싶은 곳에다가
//        handler.removeMessage(0);

        danButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), DanStationActivity.class);
                startActivity(intent);
            }
        });

        return danPage;
    }
}
