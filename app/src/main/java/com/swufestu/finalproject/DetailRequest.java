package com.swufestu.finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DetailRequest extends AppCompatActivity {
    DBHelper dbHelper;
    TextView Tid,Tname,Tmajor,Tgrade,Tstart,Tend,Treason,Ttel;
    String id,start,result,end,reason,tel;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_request);

        dbHelper = new DBHelper(getApplicationContext());
        Tid = findViewById(R.id.detailrequest_ID);
        Tname = findViewById(R.id.detailrequest_name);
        Tmajor = findViewById(R.id.detailrequest_major);
        Tgrade = findViewById(R.id.detailrequest_grade);
        Tstart = findViewById(R.id.detailrequest_start);
        Tend = findViewById(R.id.detailrequest_end);
        Treason = findViewById(R.id.detailrequest_reason);
        Ttel = findViewById(R.id.detailrequest_tel);

        intent = getIntent();
        id = intent.getStringExtra("ID");
        start = intent.getStringExtra("starttime");
        result = intent.getStringExtra("result");
        showInfo();
    }

    public void showInfo(){
        Tid.setText(id);
        Tstart.setText(start);

        SQLiteDatabase db2 = dbHelper.getReadableDatabase();
        String query2 = "select NAME,MAJOR,GRADE from student where id=?";
        Cursor cursor2 = db2.rawQuery(query2,new String[]{id});
        cursor2.moveToFirst();
        Tname.setText(cursor2.getString(0));
        Tmajor.setText(cursor2.getString(1));
        Tgrade.setText(cursor2.getString(2));

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
        db.close();
        Toast.makeText(DetailRequest.this, "撤销成功！！！", Toast.LENGTH_LONG).show();
        setResult(3,intent);
        finish();
    }

    public void returnStuRecord(View view){
        setResult(3,intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==11&&resultCode==4){
            start = data.getStringExtra("starttime");
            showInfo();
        }else{
            Toast.makeText(DetailRequest.this, "Error！！！", Toast.LENGTH_LONG).show();
        }
    }
}