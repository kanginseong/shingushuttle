package com.example.terms_of_use;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class InfoAdapter extends BaseAdapter {

    Context context;
    ArrayList<InfoListItem> list_items;

    TextView listtag,listtitle,listdate;

    public InfoAdapter(Context context, ArrayList<InfoListItem> list_items) {
        this.context = context;
        this.list_items = list_items;
    }


    @Override
    public int getCount() {
        return this.list_items.size();
    }

    @Override
    public Object getItem(int position) {
        return list_items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.info_item_list,null);
            listtag = (TextView)convertView.findViewById(R.id.listTag);
            listtitle = (TextView)convertView.findViewById(R.id.listTitle);
            listdate = (TextView)convertView.findViewById(R.id.listDate);
        }

        listtag.setText(list_items.get(position).getInfotag());
        if(listtag.getText()=="공지사항"){
            listtag.setBackgroundColor(convertView.getResources().getColor(R.color.tone_orange));
        }
        listtitle.setText(list_items.get(position).getTitle());
        listdate.setText(list_items.get(position).getDate());

        return convertView;
    }
}