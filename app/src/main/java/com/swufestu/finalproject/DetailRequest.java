package com.swufestu.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailRequest extends AppCompatActivity {
    DBHelper dbHelper;
    TextView Tid,Tname,Tmajor,Tgrade,Tstart,Tend,Treason,Ttel,show;
    String id,start,result,end,reason,tel;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_request);

        Tid = findViewById(R.id.detail_ID);
        Tname = findViewById(R.id.detail_name);
        Tmajor = findViewById(R.id.detail_major);
        Tgrade = findViewById(R.id.detail_grade);
        Tstart = findViewById(R.id.detail_start);
        Tend = findViewById(R.id.detail_end);
        Treason = findViewById(R.id.detail_reason);
        Ttel = findViewById(R.id.detail_tel);
        show = findViewById(R.id.detail_show);

        intent = getIntent();
        id = intent.getStringExtra("ID");
        start = intent.getStringExtra("starttime");
        result = intent.getStringExtra("result");

        SQLiteDatabase db1 = dbHelper.getReadableDatabase();
        String query1 = "select ENDTIME,REASON,STU_TEL from leave_request where ID=? and STARTTIME=?";
        Cursor cursor1 = db1.rawQuery(query1,new String[]{id,start});
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

    public void changeRequest(View view){
        Intent toRequest = new Intent(this, RequestPage.class);
        toRequest.putExtra("ID",id);
        toRequest.putExtra("status","change");
        toRequest.putExtra("start",start);
        toRequest.putExtra("end",end);
        toRequest.putExtra("reason",reason);
        toRequest.putExtra("tel",tel);
        startActivityForResult(toRequest, 11);
    }

    public void deleteRequest(View view){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "delete from leave_request where ID=\""+id+"\"";
        db.execSQL(query);
    }

    public void returnStuRecord(View view){
        setResult(3,intent);
        finish();
    }
}