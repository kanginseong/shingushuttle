package com.example.terms_of_use;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Terms_Of_UseActivity extends AppCompatActivity {

    // 뒤로가기 클릭 시, 이용약관 재방문 X
    public static Activity terms_of_use_activity;

    private AlertDialog dialog;

    public int TERMS_AGREE_1 = 0;
    public int TERMS_AGREE_2 = 0;
    public int TERMS_AGREE_3 = 0;

    CheckBox term_check_all, term_check1, term_check2;
    Button term_of_use_agree;

    private String advertisingID;
    private String devices_agree_YN = null;

    View.OnClickListener onClickListener;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.terms_of_use);

        terms_of_use_activity = Terms_Of_UseActivity.this;

        // 광고ID 가져오는 쓰레드
        // 디바이스ID는 법적인 문제로 쓸 수 없어서 광고ID를 디바이스 식별자로 씀
        AsyncTask<Void, Void, String> taskAdId = new AsyncTask<Void, Void, String>() {

            protected String doInBackground(Void... params) {

                AdvertisingIdClient.Info adInfo = null;
                try {

                    adInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
                } catch (GooglePlayServicesNotAvailableException e){

                } catch (GooglePlayServicesRepairableException e) {

                } catch (IOException e){

                }

                String strAdId = null;
                try{

                    strAdId = adInfo.getId();
                } catch (NullPointerException e) {

                }
                return strAdId;
            }

            protected  void onPostExecute(String strAdId) {

                advertisingID = strAdId;
            }
        };
        taskAdId.execute();

        term_check_all = findViewById(R.id.term_check_all);
        term_check1 = findViewById(R.id.term_check1);
        term_check2 = findViewById(R.id.term_check2);
        term_of_use_agree = findViewById(R.id.term_of_use_agree);

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(v.getId() == R.id.term_check_all) {

                    if(term_check_all.isChecked()) {

                        TERMS_AGREE_3 = 1;
                        term_check1.setChecked(true);
                        term_check2.setChecked(true);
                        term_of_use_agree.setTextColor(getResources().getColor(R.color.tone_orange));
                    } else {

                        TERMS_AGREE_3 = 0;
                        term_check1.setChecked(false);
                        term_check2.setChecked(false);
                        term_of_use_agree.setTextColor(getResources().getColor(R.color.tone_Gray));
                    }
                }

                switch (v.getId()){

                    case R.id.term_check1:
                        if (term_check1.isChecked()) {

                            TERMS_AGREE_1 = 1;
                        } else if (!term_check1.isChecked()){

                            TERMS_AGREE_3 = 0;
                            TERMS_AGREE_1 = 0;
                            term_check_all.setChecked(false);
                            term_of_use_agree.setTextColor(getResources().getColor(R.color.tone_Gray));
                        } else {

                            TERMS_AGREE_1 = 0;
                        }
                        break;

                    case R.id.term_check2:
                        if (term_check2.isChecked()) {

                            TERMS_AGREE_2 = 1;
                        } else if (!term_check1.isChecked()) {

                            TERMS_AGREE_3 = 0;
                            TERMS_AGREE_2 = 0;
                            term_check_all.setChecked(false);
                            term_of_use_agree.setTextColor(getResources().getColor(R.color.tone_Gray));
                        } else {

                            TERMS_AGREE_2 = 0;
                        }
                        break;
                }

                if (term_check1.isChecked() && term_check2.isChecked()){

                    TERMS_AGREE_3 = 1;
                    term_check_all.setChecked(true);
                    term_of_use_agree.setTextColor(getResources().getColor(R.color.tone_orange));
                } else if (!term_check1.isChecked() && !term_check2.isChecked()){

                    TERMS_AGREE_3 = 0;
                    term_check_all.setChecked(false);
                    term_of_use_agree.setTextColor(getResources().getColor(R.color.tone_Gray));
                } else {

                    TERMS_AGREE_3 = 0;
                    term_check_all.setChecked(false);
                    term_of_use_agree.setTextColor(getResources().getColor(R.color.tone_Gray));
                }

                if (v.getId() == R.id.term_of_use_agree) {

                    if (TERMS_AGREE_3 != 1) {

                        if (TERMS_AGREE_2 == 1) {

                            if (TERMS_AGREE_1 == 1) {

                                devices_agree_YN = "Y";
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response);
                                            boolean success = jsonObject.getBoolean("success");
                                            if (success) { // 동의의 성공한 경우
//                                                Toast.makeText(getApplicationContext(), "동의", Toast.LENGTH_SHORT).show();
                                                TERMS_AGREE_1 = 0;
                                                TERMS_AGREE_2 = 0;
                                                TERMS_AGREE_3 = 0;
                                                Intent intent = new Intent(Terms_Of_UseActivity.this, MainActivity.class);
                                                startActivity(intent);
                                            } else { // 실패
//                                                Toast.makeText(getApplicationContext(), "에러", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                // 서버로 Volley를 이용해서 요청을 함.
                                Terms_Of_UseRequest terms_of_useRequest = new Terms_Of_UseRequest(advertisingID, devices_agree_YN, responseListener);
                                RequestQueue queue = Volley.newRequestQueue(Terms_Of_UseActivity.this);
                                queue.add(terms_of_useRequest);
                            } else {
                                // 이용약관 미동의시 알림
                                AlertDialog.Builder builder = new AlertDialog.Builder(Terms_Of_UseActivity.this);
                                dialog = builder.setMessage("이용약관 동의를 해주세요")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                return;
                            }

                        } else {
                            // 이용약관 미동의시 알림
                            AlertDialog.Builder builder = new AlertDialog.Builder(Terms_Of_UseActivity.this);
                            dialog = builder.setMessage("이용약관 동의를 해주세요")
                                    .setPositiveButton("확인", null)
                                    .create();
                            dialog.show();
                            return;
                        }

                    } else {

                        devices_agree_YN = "Y";
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if (success) { // 동의의 성공한 경우
//                                        Toast.makeText(getApplicationContext(), "동의", Toast.LENGTH_SHORT).show();
                                        //초기화
                                        TERMS_AGREE_1 = 0;
                                        TERMS_AGREE_2 = 0;
                                        TERMS_AGREE_3 = 0;
                                        //메인페이지로 화면전환
                                        Intent intent = new Intent(Terms_Of_UseActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    } else { // 실패
                                        Toast.makeText(getApplicationContext(), "에러", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        // 서버로 Volley를 이용해서 요청을 함.
                        Terms_Of_UseRequest terms_of_useRequest = new Terms_Of_UseRequest(advertisingID, devices_agree_YN, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(Terms_Of_UseActivity.this);
                        queue.add(terms_of_useRequest);
                    }
                }
            }
        };

        term_check1.setOnClickListener(onClickListener);
        term_check2.setOnClickListener(onClickListener);
        term_check_all.setOnClickListener(onClickListener);
        term_of_use_agree.setOnClickListener(onClickListener);

        // 사용자의 ID가 디바이스 테이블에 담겨있는지 확인 후 담겨있다면 이용약관 페이지 스킵
        // 인트로 화면에 들어가야하지 않을까 싶음
        // 메인 페이지 보여주기 전에 확인해야 하기때문에

    }


}
