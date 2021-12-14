package com.example.terms_of_use;

import android.database.Cursor;
import android.os.StrictMode;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;

public class InfoRowNum {

    Boolean intotal=false;
    int totalRow;
    Cursor cursor;

    InfoRowNum() {

    }

    public int getTotalRow() {
        return totalRow;
    }

    public void setTotalRow(int totalRow) {
        this.totalRow = totalRow;
    }

    public void rowCountParse() throws Exception {
        StrictMode.enableDefaults(); //db연결 필수

        URL dburl = new URL("http://101.101.218.76/info_row_num.php?");

        XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
        XmlPullParser parser = parserCreator.newPullParser();

        parser.setInput(dburl.openStream(), null);

        int parserEvent = parser.getEventType();

        while (parserEvent != XmlPullParser.END_DOCUMENT) {
            switch (parserEvent) {
                case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행

                    if (parser.getName().equals("total")) { //title 만나면 내용을 받을수 있게 하자

                        intotal = true;
                    }

                    break;
                case XmlPullParser.TEXT://parser가 내용에 접근했을때

                    if (intotal) { //isTitle이 true일 때 태그의 내용을 저장.

                        totalRow = (int)Integer.parseInt(parser.getText());
                        intotal = false;
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("item")) {

                        setTotalRow(totalRow);
                        intotal = false;
                    }
                    break;
            }
            parserEvent = parser.next();
        }
    }
}