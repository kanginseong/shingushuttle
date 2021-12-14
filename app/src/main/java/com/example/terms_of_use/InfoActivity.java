package com.example.terms_of_use;

import android.content.Intent;
import android.graphics.Point;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

// 앱 정보, 안내, 시간표
public class InfoActivity extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    //추가
    ListView infolistview;
    InfoAdapter infoAdapter;
    ArrayList<InfoListItem> info_listitem_arrlist;

    RelativeLayout listArea;
    AdapterView.OnItemClickListener onItemClickListener;

    Intent infoIntent;
    InfoDataRequest infoDataRequest ;
    InfoRowNum infoRowNum;

    int infoColoumCount;
    int posi;

    InfoShowActivity infoShowActivity;

    public InfoActivity(){

    }

    public static InfoActivity newInstance(String param1, String param2) {
        InfoActivity fragment = new InfoActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.main_info, container, false);

        listArea = (RelativeLayout)view.findViewById(R.id.listdata);
        infolistview = (ListView)view.findViewById(R.id.info_listview);
        info_listitem_arrlist = new ArrayList<InfoListItem>();

        infoDataRequest = new InfoDataRequest();
        infoRowNum = new InfoRowNum();
        try {
            infoRowNum.rowCountParse();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //list에 데이터 추가
        //디비의 컬럼수 가져오는거
//        infoColoumCount = 2;
        infoColoumCount = infoRowNum.getTotalRow();
        int i = 0;

        while(i<infoColoumCount){
            try {
                infoDataRequest.dataParse(i);

            } catch (Exception e) {
                e.printStackTrace(); }

            info_listitem_arrlist.add(
                    new InfoListItem(
                            infoDataRequest.getInfo_tag(),
                            infoDataRequest.getInfo_title(),
                            infoDataRequest.getInfo_date()) );
            i++;
        }

        onItemClickListener = new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(),position+"클릭",Toast.LENGTH_LONG).show();
//                position 전달
                infoIntent = new Intent(getContext(), InfoShowActivity.class);
                infoIntent.putExtra("position",position);
                startActivity(infoIntent);

            }
        };
        infolistview.setOnItemClickListener(onItemClickListener);

        infoAdapter = new InfoAdapter(getActivity(),info_listitem_arrlist);
        infolistview.setAdapter(infoAdapter);

        // Inflate the layout for this fragment
        return view;
    }


}