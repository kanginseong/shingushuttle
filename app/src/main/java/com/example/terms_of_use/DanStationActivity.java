package com.example.terms_of_use;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

public class DanStationActivity extends AppCompatActivity implements MapReverseGeoCoder.ReverseGeoCodingResultListener {

    private static final String LOG_TAG = "DanStationActivity";

    private MapView mapView;
    private ImageView myLocation, stationLocation;

    // 버스 경도, 위도, 속도로 버스 위치
    private ParseBusInfo001 parseBusInfo001;

    // 마커
    private MapPOIItem dan_mark, bus_mark;

    // 버튼 유지
    private boolean loc_btn_check = false;
    private boolean bus_btn_check = false;

    // 10초동안 버스위치 표시
    public Handler handler;

    // 딜레이 시간
    public final static int REPEAT_DELAY = 10000;

    View.OnClickListener onClickListener;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danstation);

        myLocation = findViewById(R.id.mylocation);
        stationLocation = findViewById(R.id.stationlocation);

        parseBusInfo001 = new ParseBusInfo001();

        mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        // 단대오거리 마커
        dan_mark = new MapPOIItem();
        dan_mark.setItemName("단대오거리역 정류장");
        dan_mark.setTag(0);
        dan_mark.setMapPoint(MapPoint.mapPointWithGeoCoord(37.444464, 127.156504));
        dan_mark.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        dan_mark.setSelectedMarkerType(MapPOIItem.MarkerType.BluePin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        bus_mark = new MapPOIItem();
        // 10초마다 버스 위치 지도에 표시

        handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                super.handleMessage(msg);

                try{
                    mapView.removePOIItem(bus_mark);

                    parseBusInfo001.dataParse();
                    bus_mark.setItemName("셔틀버스");
                    bus_mark.setTag(1);
                    bus_mark.setMapPoint(MapPoint.mapPointWithGeoCoord(Double.valueOf(parseBusInfo001.getBus_latitude()), Double.valueOf(parseBusInfo001.getBus_longtitude())));
//                        bus_mark.setMarkerType(MapPOIItem.MarkerType.CustomImage);
//                        bus_mark.setCustomImageResourceId(R.drawable.bus_icon); // 마커 이미지.
                    bus_mark.setMarkerType(MapPOIItem.MarkerType.YellowPin);
                    mapView.setZoomLevel(1, true);

                    mapView.addPOIItem(bus_mark);

                } catch (Exception e){
                    e.printStackTrace();
                }
                this.sendEmptyMessageDelayed(0, REPEAT_DELAY);        // REPEAT_DELAY 간격으로 계속해서 반복하게 만들어준다

            }
        };

// 시작하고 싶은 곳에다가
        handler.sendEmptyMessage(0);

// 중단하고 싶은 곳에다가
//      handler.removeMessage(0);

        myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(loc_btn_check == false) {

                    loc_btn_check = true;

                    myLocation.setImageResource(R.drawable.loc_btn_on);
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                    mapView.setShowCurrentLocationMarker(true);
                    mapView.setDefaultCurrentLocationMarker();
                    mapView.setZoomLevel(1,true);

                    bus_btn_check = false;
                    stationLocation.setImageResource(R.drawable.bus_btn_off);

                } else {

                    loc_btn_check = false;
                    myLocation.setImageResource(R.drawable.loc_btn_off);
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
                    mapView.setShowCurrentLocationMarker(false);
                }
            }
        });


        stationLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(bus_btn_check == false) {

                    bus_btn_check = true;
                    mapView.removePOIItem(dan_mark);
                    stationLocation.setImageResource(R.drawable.bus_btn_on);
                    mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.444464, 127.156504), 1, true);
                    mapView.addPOIItem(dan_mark);


                    loc_btn_check = false;
                    myLocation.setImageResource(R.drawable.loc_btn_off);
                    mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
                    mapView.setShowCurrentLocationMarker(false);
                } else {

                    bus_btn_check = false;
                    stationLocation.setImageResource(R.drawable.bus_btn_off);

                }
            }
        });


        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        }else {
            checkRunTimePermission();
        }
    }

    public void onBackPressed() {
//        Toast.makeText(this, "Back button pressed.", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
        handler.removeMessages(0);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mapView.setShowCurrentLocationMarker(false);
    }

//    @Override
//    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
//        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
//        Log.i(LOG_TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
//    }
//
//    @Override
//    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {
//    }
//
//    @Override
//    public void onCurrentLocationUpdateFailed(MapView mapView) {
//    }
//
//    @Override
//    public void onCurrentLocationUpdateCancelled(MapView mapView) {
//    }

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        mapReverseGeoCoder.toString();
        onFinishReverseGeoCoding(s);
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
        onFinishReverseGeoCoding("Fail");
    }

    private void onFinishReverseGeoCoding(String result) {
//        Toast.makeText(NamStationActivity.this, "Reverse Geo-coding : " + result, Toast.LENGTH_SHORT).show();
    }


    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {
                Log.d("@@@", "start");
                //위치 값을 가져올 수 있음
                mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.444464, 127.156504), 1,true);

                mapView.addPOIItem(dan_mark);
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {

                    Toast.makeText(DanStationActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();


                }else {

                    Toast.makeText(DanStationActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(DanStationActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음
            mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(37.444464, 127.156504), 1,true);

            mapView.addPOIItem(dan_mark);


        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(DanStationActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(DanStationActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(DanStationActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(DanStationActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }



    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(DanStationActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}