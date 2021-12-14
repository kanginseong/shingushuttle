package com.example.terms_of_use;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import java.net.URL;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InfoShowActivity extends AppCompatActivity {

    WebView infowebview;
    int listNum;
    InfoDataRequest infoDataRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_show_activity);
        Intent infointent = getIntent();
//        infoActivity = new InfoActivity();
        infoDataRequest = new InfoDataRequest();
        infowebview = (WebView)findViewById(R.id.infoWebView);

        //position 받기
        listNum = infointent.getIntExtra("position",0);
        System.out.println("listNum is "+listNum);
        try {
            infoDataRequest.dataParse(listNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        infowebview.setWebChromeClient(new WebChromeClient());
        String url = infoDataRequest.getInfo_addr();
        System.out.println("url is "+ url);
        infowebview.getSettings().setJavaScriptEnabled(true);
        infowebview.loadUrl(url);//밖에 나와야함
        infowebview.setWebChromeClient(new WebChromeClient());
        onBackPressed();
    }


}