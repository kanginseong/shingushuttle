package com.example.terms_of_use;

import java.net.URL;

public class InfoListItem {

    private String infotag;
    private String title;
    private String date;
//    private URL url;

    public InfoListItem(String infotag, String title, String date) {
        this.infotag = infotag;
        this.title = title;
        this.date = date;
//        this.url = url;
    }

    public String getInfotag() {
        return infotag;
    }

    public void setInfotag(String infotag) {
        this.infotag = infotag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

//
//    public URL getUrl() {
//        return url;
//    }
//
//    public void setUrl(URL url) {
//        this.url = url;
//    }
}