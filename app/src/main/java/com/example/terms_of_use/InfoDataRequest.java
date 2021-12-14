package com.example.terms_of_use;

import android.os.StrictMode;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;

public class InfoDataRequest {

    //데이터베이스 행 수
    boolean inInfoItem = false,ininfo_index = false, ininfo_tag = false, ininfo_title=false, ininfo_date=false, ininfo_addr = false;
    String info_index = null, info_tag = null, info_title = null, info_date = null, info_addr=null;

    int count;
//    public AttemptInfo(int count){
//        this.count = count;
//    }

    public void  dataParse(int count) throws Exception {
        this.count = count;
        StrictMode.enableDefaults(); //db연결 필수

        URL dburl = new URL("http://101.101.218.76/info_align.php?info_index="+count);

        System.out.println("count is " +  count);
        XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
        XmlPullParser parser = parserCreator.newPullParser();

        parser.setInput(dburl.openStream(), null);

        int parserEvent = parser.getEventType();

        while (parserEvent != XmlPullParser.END_DOCUMENT) {
            switch (parserEvent) {
                case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행

                    if (parser.getName().equals("info_index")) { //title 만나면 내용을 받을수 있게 하자

                        ininfo_index = true;
                    }
                    if (parser.getName().equals("info_tag")) { //title 만나면 내용을 받을수 있게 하자

                        ininfo_tag = true;
                    }
                    if (parser.getName().equals("info_title")) { //address 만나면 내용을 받을수 있게 하자

                        ininfo_title = true;
                    }
                    if (parser.getName().equals("info_date")) { //mapx 만나면 내용을 받을수 있게 하자

                        ininfo_date = true;
                    }
                    if (parser.getName().equals("info_addr")) { //mapx 만나면 내용을 받을수 있게 하자

                        ininfo_addr = true;
                    }
                    break;
                case XmlPullParser.TEXT://parser가 내용에 접근했을때

                    if (ininfo_index) { //isTitle이 true일 때 태그의 내용을 저장.

                        info_index = parser.getText();
                        ininfo_index = false;
                    }
                    if (ininfo_tag) { //isTitle이 true일 때 태그의 내용을 저장.

                        info_tag = parser.getText();
//                            t1.setText(bus_latitude + " ");
                        ininfo_tag = false;
                    }
                    if (ininfo_title) { //isAddress이 true일 때 태그의 내용을 저장.

                        info_title = parser.getText();
//                            t2.setText(bus_longtitude + " ");
                        ininfo_title = false;
                    }
                    if (ininfo_date) { //isMapx이 true일 때 태그의 내용을 저장.

                        info_date = parser.getText();
//                            t3.setText(bus_velocity + " ");
                        ininfo_date = false;
                    }
                    if (ininfo_addr) { //isMapx이 true일 때 태그의 내용을 저장.

                        String addr_str = parser.getText();
                        addr_str.replace("&amp;","&");
                        info_addr = addr_str;
                        //                            t3.setText(bus_velocity + " ");
                        ininfo_addr = false;
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("item")) {

                        setInfo_index(info_index);
                        setInfo_tag(info_tag);
                        setInfo_title(info_title);
                        setInfo_date(info_date);
                        setInfo_addr(info_addr);
//
                        inInfoItem = false;
                    }
                    break;
            }
            parserEvent = parser.next();
        }
    }

    public String getInfo_index() {
        return info_index;
    }

    public void setInfo_index(String info_index) {
        this.info_index = info_index;
    }

    public String getInfo_tag() {
        return info_tag;
    }

    public void setInfo_tag(String info_tag) {
        this.info_tag = info_tag;
    }

    public String getInfo_title() {
        return info_title;
    }

    public void setInfo_title(String info_title) {
        this.info_title = info_title;
    }

    public String getInfo_date() {
        return info_date;
    }

    public void setInfo_date(String info_date) {
        this.info_date = info_date;
    }

    public String getInfo_addr() {
        return info_addr;
    }

    public void setInfo_addr(String info_addr) {
        this.info_addr = info_addr;
    }
}