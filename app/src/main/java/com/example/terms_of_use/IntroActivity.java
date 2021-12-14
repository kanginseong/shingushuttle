package com.example.terms_of_use;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class IntroActivity extends AppCompatActivity {

    private Handler handler;
    private String advertisingID;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        // 디바이스 식별자
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

        handler = new Handler();

        // intro 3초 동안 이용약관 사용자 여부 확인
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) { // 동의의 성공한 경우
//                                Toast.makeText(getApplicationContext(), "이용약관 동의 O", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else { // 실패
//                                Toast.makeText(getApplicationContext(), "이용약관 동의 X.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(IntroActivity.this, Terms_Of_UseActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                Terms_Of_Use_CheckRequest toscRequest = new Terms_Of_Use_CheckRequest(advertisingID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(IntroActivity.this);
                queue.add(toscRequest);

                finish();
            }
        }, 3000);
    }
}
