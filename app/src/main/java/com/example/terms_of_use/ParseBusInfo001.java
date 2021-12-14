package com.example.terms_of_use;

import android.os.StrictMode;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;

// module_001 버스 위치 정보
public class ParseBusInfo001 {

    boolean initem = false, inbus_id = false, inbus_latitude = false, inbus_longtitude = false, inbus_velocity = false;
    String bus_id = null,bus_latitude = null, bus_longtitude = null, bus_velocity = null;


    public void  dataParse() throws Exception {

            StrictMode.enableDefaults(); //db연결 필수수

            URL dburl = new URL("http://101.101.218.76/bus_time.php?bus_id=module_001");

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(dburl.openStream(), null);

            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행

                        if (parser.getName().equals("bus_id")) { //title 만나면 내용을 받을수 있게 하자

                            inbus_id = true;
                        }
                        if (parser.getName().equals("bus_latitude")) { //title 만나면 내용을 받을수 있게 하자

                            inbus_latitude = true;
                        }
                        if (parser.getName().equals("bus_longtitude")) { //address 만나면 내용을 받을수 있게 하자

                            inbus_longtitude = true;
                        }
                        if (parser.getName().equals("bus_velocity")) { //mapx 만나면 내용을 받을수 있게 하자

                            inbus_velocity = true;
                        }

                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때

                        if (inbus_id) { //isTitle이 true일 때 태그의 내용을 저장.

                            bus_id = parser.getText();
                            inbus_id = false;
                        }
                        if (inbus_latitude) { //isTitle이 true일 때 태그의 내용을 저장.

                            bus_latitude = parser.getText();
//                            t1.setText(bus_latitude + " ");
                            inbus_latitude = false;
                        }
                        if (inbus_longtitude) { //isAddress이 true일 때 태그의 내용을 저장.

                            bus_longtitude = parser.getText();
//                            t2.setText(bus_longtitude + " ");
                            inbus_longtitude = false;
                        }
                        if (inbus_velocity) { //isMapx이 true일 때 태그의 내용을 저장.

                            bus_velocity = parser.getText();
//                            t3.setText(bus_velocity + " ");
                            inbus_velocity = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {

                            setBus_id(bus_id);
                            setBus_latitude(bus_latitude);
                            setBus_longtitude(bus_longtitude);
                            setBus_velocity(bus_velocity);
//
                            initem = false;
                        }
                        break;
                    }
                    parserEvent = parser.next();
                }
        }

    public String getBus_id() {
        return bus_id;
    }
    public void setBus_id(String bus_id) {
        this.bus_id = bus_id;
    }

    public String getBus_latitude() {
        return bus_latitude;
    }
    public void setBus_latitude(String bus_latitude) {
        this.bus_latitude = bus_latitude;
    }

    public String getBus_longtitude() {
        return bus_longtitude;
    }
    public void setBus_longtitude(String bus_longtitude) {
        this.bus_longtitude = bus_longtitude;
    }

    public String getBus_velocity() {
        return bus_velocity;
    }
    public void setBus_velocity(String bus_velocity) {
        this.bus_velocity = bus_velocity;
    }







}


