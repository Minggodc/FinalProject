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

public class TeacherQueryCancel extends AppCompatActivity implements AdapterView.OnItemClickListener {
    DBHelper dbHelper;
    ListView mylist2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_query_cancel);

        mylist2 = findViewById(R.id.list_teacher_query_cancel);
        mylist2.setOnItemClickListener(this);

        Intent intent = getIntent();
        String Tid = intent.getStringExtra("ID");

        dbHelper = new DBHelper(getApplicationContext());
        ArrayList<StuGetRecord> list2 = new ArrayList<StuGetRecord>();

        SQLiteDatabase db1 = dbHelper.getReadableDatabase();
        String query1 = "select ID,STARTTIME,ENDTIME from on_leave where TEACHER_ID=? and CANCEL=? order by datetime(STARTTIME) asc";
        Cursor cursor1 = db1.rawQuery(query1,new String[]{Tid,"no"});
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
                temp.setResult("no");
                list2.add(temp);
            }
            cursor1.close();
        }
        db1.close();

        //自定义列表布局
        StuRecordAdapter ma = new StuRecordAdapter(TeacherQueryCancel.this,R.layout.list_item_tea,list2);
        mylist2.setAdapter(ma);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Object itemAtPosition = mylist2.getItemAtPosition(position);
        StuGetRecord sgr = (StuGetRecord) itemAtPosition;

        Intent intent = new Intent(this, DetailQuery.class);
        intent.putExtra("ID",sgr.getId());
        intent.putExtra("starttime",sgr.getStart());
        intent.putExtra("status","cancel");
        startActivityForResult(intent, 12);
    }
}