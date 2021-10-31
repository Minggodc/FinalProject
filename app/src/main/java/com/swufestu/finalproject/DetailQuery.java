package com.swufestu.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailQuery extends AppCompatActivity {

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_query);

        TextView Tid,Tname,Tmajor,Tgrade,Tstart,Tend,Treason,Ttel;
        String id,start,end,reason,tel,status;

        dbHelper = new DBHelper(getApplicationContext());

        Tid = findViewById(R.id.detailquery_ID);
        Tname = findViewById(R.id.detailquery_name);
        Tmajor = findViewById(R.id.detailquery_major);
        Tgrade = findViewById(R.id.detailquery_grade);
        Tstart = findViewById(R.id.detailquery_start);
        Tend = findViewById(R.id.detailquery_end);
        Treason = findViewById(R.id.detailquery_reason);
        Ttel = findViewById(R.id.detailquery_tel);

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        start = intent.getStringExtra("starttime");
        status = intent.getStringExtra("status");
        Tid.setText(id);
        Tstart.setText(start);

        SQLiteDatabase db2 = dbHelper.getReadableDatabase();
        String query2 = "select NAME,MAJOR,GRADE from student where id=?";
        Cursor cursor2 = db2.rawQuery(query2,new String[]{id});
        cursor2.moveToFirst();
        Tname.setText(cursor2.getString(0));
        Tmajor.setText(cursor2.getString(1));
        Tgrade.setText(cursor2.getString(2));


        if(status.equals("cancel")){
            SQLiteDatabase db1 = dbHelper.getReadableDatabase();
            String query1 = "select ENDTIME,REASON,STU_TEL from on_leave where ID=? and STARTTIME=? and CANCEL=?";
            Cursor cursor1 = db1.rawQuery(query1,new String[]{id,start,"no"});
            cursor1.moveToFirst();
            end = cursor1.getString(0);
            reason = cursor1.getString(1);
            tel = cursor1.getString(2);
            Tend.setText(end);
            Treason.setText(reason);
            Ttel.setText(tel);
            cursor1.close();
            db1.close();
        }else if(status.equals("query")){
            SQLiteDatabase db1 = dbHelper.getReadableDatabase();
            String query1 = "select ENDTIME,REASON,STU_TEL from on_leave where ID=? and STARTTIME=? and CANCEL=? or CANCEL=?";
            Cursor cursor1 = db1.rawQuery(query1,new String[]{id,start,"yes","no"});
            cursor1.moveToFirst();
            end = cursor1.getString(0);
            reason = cursor1.getString(1);
            tel = cursor1.getString(2);
            Tend.setText(end);
            Treason.setText(reason);
            Ttel.setText(tel);
            cursor1.close();
            db1.close();
        }
    }

    //返回
    public void detailQueryReturn(View view){
        finish();
    }
}