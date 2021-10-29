package com.swufestu.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CheckPageAdapter extends ArrayAdapter {
    public CheckPageAdapter(@NonNull Context context, int resource, @NonNull ArrayList<StuGetRecord> list) {
        super(context, resource, list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_tea,
                    parent,
                    false);
        }
        StuGetRecord sgr =  (StuGetRecord) getItem(position);
        TextView list_name1 = itemView.findViewById(R.id.list_name1);
        TextView list_result1 = itemView.findViewById(R.id.list_result1);
        TextView list_start1 = itemView.findViewById(R.id.list_start1);
        TextView list_end1 = itemView.findViewById(R.id.list_end1);
        list_name1.setText(sgr.getName());
        String result;
        if (sgr.getResult().equals("yes")){
            result = "已销假";
        }else if(sgr.getResult().equals("no")){
            result = "待销假";
        }else if(sgr.getResult().equals("待审核")){
            result = "待审核";
        }else if(sgr.getResult().equals("未通过")){
            result = "未通过";
        }else{
            result = "Error";
        }
        list_result1.setText(result);
        list_start1.setText(sgr.getStart());
        list_end1.setText(sgr.getEnd());
        return itemView;
    }
}
