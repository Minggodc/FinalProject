package com.swufestu.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MyInfoPage extends AppCompatActivity {

    public static final String TAG = "MyInfoPage";
    DBHelper dbHelper;
    TextView infoid,infoname,infoxueyuan,infomajor,infograde,infoteacher,info_tea_tel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info_page);

        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");


        dbHelper = new DBHelper(getApplicationContext());

        SQLiteDatabase db1 = dbHelper.getReadableDatabase();
        String query1 = "select NAME,XUEYUAN,MAJOR,GRADE from student where ID=?";
        Cursor cursor1 = db1.rawQuery(query1,new String[]{id});
        cursor1.moveToFirst();
        String name = cursor1.getString(0);
        String xueyuan = cursor1.getString(1);
        String major = cursor1.getString(2);
        String grade = cursor1.getString(3);
        cursor1.close();
        db1.close();
        Log.i(TAG, "xueyuan: "+xueyuan);
        Log.i(TAG, "grade: "+grade);


        SQLiteDatabase db2 = dbHelper.getReadableDatabase();
        String query2 = "select NAME,TEL from teacher where XUEYUAN=? and GRADE=?";
        Cursor cursor2 = db2.rawQuery(query2,new String[]{xueyuan,grade});
        cursor2.moveToFirst();
        String tea_name = cursor2.getString(0);
        String tea_tel = cursor2.getString(1);
        cursor2.close();
        db2.close();

        infoid = findViewById(R.id.infoid);
        infoname = findViewById(R.id.infoname);
        infoxueyuan = findViewById(R.id.infoxueyuan);
        infomajor = findViewById(R.id.infomajor);
        infograde = findViewById(R.id.infograde);
        infoteacher = findViewById(R.id.infoteacher);
        info_tea_tel = findViewById(R.id.info_tea_tel);


        infoid.setText(id);
        infoname.setText(name);
        infoxueyuan.setText(xueyuan);
        infomajor.setText(major);
        infograde.setText(grade);
        infoteacher.setText(tea_name);
        info_tea_tel.setText(tea_tel);
    }

    public void infoReturn(View view){
        finish();
    }
}