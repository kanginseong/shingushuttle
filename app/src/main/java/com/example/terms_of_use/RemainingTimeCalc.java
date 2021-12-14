package com.example.terms_of_use;

import java.util.Timer;

public class RemainingTimeCalc {

    Timer timer;

    ParseBusInfo001 parseBusInfo001 = new ParseBusInfo001();

    String bus_latitude = "0", bus_longtitude = "0", bus_velocity = "0";

    int Remaining_Minutes;

    private double station_lat = 37.444580, station_long = 127.156187; //단대오거리역

    public void remainingTime(){

        try {
            parseBusInfo001.dataParse();
            bus_latitude = parseBusInfo001.getBus_latitude();
            bus_longtitude = parseBusInfo001.getBus_longtitude();
            bus_velocity = parseBusInfo001.getBus_velocity();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Double dist = distance(station_lat, station_long, Double.valueOf(bus_latitude), Double.valueOf(bus_longtitude));

        double i = getTime(dist)*60; // getTime이 0이 나올 수 있어서 분으로 바꾸고 형변환 해줘야한다.
        Remaining_Minutes = (int) i;


    }

    public double distance(double lat1,double lon1, double lat2, double lon2) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))* Math.sin(deg2rad(lat2)) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                        Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344; // 단위 km

        return (dist);
    }

    public double deg2rad(double deg) {

        return(deg * Math.PI /180.0);
    }

    public double rad2deg(double rad) {

        return(rad * 180 / Math.PI);
    }

    public double getTime(Double Distance) {

        Double vel = Double.valueOf(bus_velocity); //km/h
        double time = (Distance/vel);

        return time;
    }

}

