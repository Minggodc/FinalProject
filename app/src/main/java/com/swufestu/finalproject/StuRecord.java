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
import java.util.HashMap;
import java.util.List;

public class StuRecord extends AppCompatActivity implements AdapterView.OnItemClickListener{
    DBHelper dbHelper;
    ListView mylist1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_record);
        ArrayList<StuGetRecord> list1 = new ArrayList<StuGetRecord>();
        mylist1 = findViewById(R.id.list_stu_record);
        mylist1.setOnItemClickListener(this);

        Intent intent = getIntent();
        String id = intent.getStringExtra("ID");
        String name = intent.getStringExtra("name");

        dbHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase db1 = dbHelper.getReadableDatabase();
        String query1 = "select STARTTIME,ENDTIME from leave_request where id=?";
        Cursor cursor1 = db1.rawQuery(query1,new String[]{id});
        if(cursor1!=null){
            while(cursor1.moveToNext()){
                StuGetRecord temp = new StuGetRecord();
                temp.setId(id);
                temp.setName(name);
                temp.setStart(cursor1.getString(0));
                temp.setEnd(cursor1.getString(1));
                temp.setResult("待审核");
                list1.add(temp);
            }
            cursor1.close();
        }
        db1.close();

        SQLiteDatabase db2 = dbHelper.getReadableDatabase();
        String query2 = "select STARTTIME,ENDTIME,CANCEL from on_leave where id=?";
        Cursor cursor2 = db2.rawQuery(query2,new String[]{id});
        if(cursor2!=null){
            while(cursor2.moveToNext()){
                StuGetRecord temp = new StuGetRecord();
                temp.setId(id);
                temp.setName(name);
                temp.setStart(cursor2.getString(0));
                temp.setEnd(cursor2.getString(1));
                temp.setResult(cursor2.getString(2));
                list1.add(temp);
            }
            cursor2.close();
        }
        db2.close();

        //自定义列表布局
        StuRecordAdapter ma = new StuRecordAdapter(StuRecord.this,R.layout.list_item_stu,list1);
        mylist1.setAdapter(ma);

    }


    //自动刷新页面
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Object itemAtPosition = mylist1.getItemAtPosition(position);
        StuGetRecord sgr = (StuGetRecord) itemAtPosition;

        if(sgr.getResult().equals("待审核")||sgr.getResult().equals("no")||sgr.getResult().equals("未通过")){
            Intent intent = new Intent(this, Detail.class);
            intent.putExtra("ID",sgr.getId());
            intent.putExtra("starttime",sgr.getStart());
            intent.putExtra("result",sgr.getResult());
            intent.putExtra("status","student");
            startActivityForResult(intent, 9);
        }
    }
}