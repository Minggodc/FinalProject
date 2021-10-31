package com.swufestu.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TeacherQueryRecord extends AppCompatActivity implements AdapterView.OnItemClickListener{

    DBHelper dbHelper;
    ListView mylist2;
    EditText xmlid;
    String Tid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_query_record);

        mylist2 = findViewById(R.id.list_teacher_query_cancel);
        mylist2.setOnItemClickListener(this);

        Intent intent = getIntent();
        Tid = intent.getStringExtra("ID");
    }

    public void teacherQuery(View view){

        xmlid = findViewById(R.id.inputID);
        String id = xmlid.getText().toString().trim();
        if(id.isEmpty()){
            Toast.makeText(TeacherQueryRecord.this, "输入不能为空！！！", Toast.LENGTH_LONG).show();
        }else{
            dbHelper = new DBHelper(getApplicationContext());
            ArrayList<StuGetRecord> list2 = new ArrayList<StuGetRecord>();
            dbHelper = new DBHelper(getApplicationContext());

            SQLiteDatabase db1 = dbHelper.getReadableDatabase();
            String query1 = "select ID,STARTTIME,ENDTIME,CANCEL from on_leave where TEACHER_ID=? and ID=? order by datetime(STARTTIME) desc";
            Cursor cursor1 = db1.rawQuery(query1,new String[]{Tid,id});
            if (cursor1!=null){
                while(cursor1.moveToNext()){
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
                    temp.setResult(cursor1.getString(3));
                    list2.add(temp);
                }
                cursor1.close();
            }
            db1.close();

            //自定义列表布局
            StuRecordAdapter ma = new StuRecordAdapter(TeacherQueryRecord.this,R.layout.list_item_tea,list2);
            mylist2.setAdapter(ma);
        }

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Object itemAtPosition = mylist2.getItemAtPosition(position);
        StuGetRecord sgr = (StuGetRecord) itemAtPosition;

        Intent intent = new Intent(this, DetailQuery.class);
        intent.putExtra("ID",sgr.getId());
        intent.putExtra("starttime",sgr.getStart());
        intent.putExtra("status","query");
        startActivityForResult(intent, 12);
    }
}