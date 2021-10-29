package com.swufestu.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class CheckPage extends AppCompatActivity implements AdapterView.OnItemClickListener{

    DBHelper dbHelper;
    ListView mylist2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_page);

        mylist2 = findViewById(R.id.list_check_page);
        mylist2.setOnItemClickListener(this);

        Intent intent = getIntent();
        String Tid = intent.getStringExtra("ID");

        dbHelper = new DBHelper(getApplicationContext());
        ArrayList<StuGetRecord> list2 = new ArrayList<StuGetRecord>();
        dbHelper = new DBHelper(getApplicationContext());

        SQLiteDatabase db1 = dbHelper.getReadableDatabase();
        String query1 = "select ID,STARTTIME,ENDTIME from leave_request where TEACHER_ID=?";
        Cursor cursor1 = db1.rawQuery(query1,new String[]{Tid});
        if (cursor1!=null){
            while(cursor1.moveToNext()){
                String id = cursor1.getString(0);
                StuGetRecord temp = new StuGetRecord();

                SQLiteDatabase db2 = dbHelper.getWritableDatabase();
                String query2 = "select NAME from student where id=?";
                Cursor cursor2 = db2.rawQuery(query2,new String[]{id});
                cursor2.moveToFirst();
                String name = cursor2.getString(0);
                cursor2.close();
                db2.close();

                temp.setId(id);
                temp.setName(name);
                temp.setStart(cursor1.getString(1));
                temp.setEnd(cursor1.getString(2));
                temp.setResult("待审核");
                list2.add(temp);
            }
            cursor1.close();
        }
        db1.close();

        //自定义列表布局
        StuRecordAdapter ma = new StuRecordAdapter(CheckPage.this,R.layout.list_item_tea,list2);
        mylist2.setAdapter(ma);
    }

    //自动刷新页面
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Object itemAtPosition = mylist2.getItemAtPosition(position);
        StuGetRecord sgr = (StuGetRecord) itemAtPosition;


        Intent intent = new Intent(this, Detail.class);
        intent.putExtra("ID",sgr.getId());
        intent.putExtra("starttime",sgr.getStart());
        intent.putExtra("result",sgr.getResult());
        intent.putExtra("status","teacher");
        startActivityForResult(intent, 10);
    }
}