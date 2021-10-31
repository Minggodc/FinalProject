package com.swufestu.finalproject;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class StuRecord extends AppCompatActivity implements AdapterView.OnItemClickListener{
    DBHelper dbHelper;
    ListView mylist1;
    String id,name;
    public static final String TAG="StuRecord";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_record);

        mylist1 = findViewById(R.id.list_stu_record);
        mylist1.setOnItemClickListener(this);

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        name = intent.getStringExtra("name");
        showList();
    }

    public void showList(){
        ArrayList<StuGetRecord> list1 = new ArrayList<StuGetRecord>();
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
        String query2 = "select STARTTIME,ENDTIME,CANCEL from on_leave where id=? order by datetime(STARTTIME) desc";
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


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Object itemAtPosition = mylist1.getItemAtPosition(position);
        StuGetRecord sgr = (StuGetRecord) itemAtPosition;

        if(sgr.getResult().equals("no")||sgr.getResult().equals("未通过")){
            Intent intent = new Intent(this, DetailStu.class);
            intent.putExtra("ID",sgr.getId());
            intent.putExtra("starttime",sgr.getStart());
            intent.putExtra("result",sgr.getResult());
            intent.putExtra("status","student");
            startActivityForResult(intent, 9);
        }else if(sgr.getResult().equals("待审核")){
            Intent intent = new Intent(this,DetailRequest.class);
            intent.putExtra("ID",sgr.getId());
            intent.putExtra("starttime",sgr.getStart());
            intent.putExtra("result",sgr.getResult());
            startActivityForResult(intent, 10);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: "+resultCode);
        Log.i(TAG, "onActivityResult: "+requestCode);
        if (requestCode==9&&resultCode==1){
            showList();
        }else if(requestCode==10&&resultCode==3){
            showList();
        }else{
            Toast.makeText(StuRecord.this, "SutRecordError！！！", Toast.LENGTH_LONG).show();
        }
    }
}