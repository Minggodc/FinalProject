package com.swufestu.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DetailStu extends AppCompatActivity {


    DBHelper dbHelper;
    TextView Tid,Tname,Tmajor,Tgrade,Tstart,Tend,Treason,Ttel,show;
    String id,start,result,end,reason,tel,status;
    RadioGroup yn;
    Intent intent;
    public static final String TAG = "DetailStu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_stu);
        dbHelper = new DBHelper(getApplicationContext());

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


        if(status.equals("student")){
            if(result.equals("no")){
                show.setText("???????????????");
            }else if (result.equals("?????????")){
                show.setText("???????????????");
            }
            else{
                show.setText("Error");
            }
            SQLiteDatabase db1 = dbHelper.getReadableDatabase();
            String query1 = "select ENDTIME,REASON,STU_TEL from on_leave where ID=? and STARTTIME=?";
            Cursor cursor1 = db1.rawQuery(query1,new String[]{id,start});
            cursor1.moveToFirst();
            Tend.setText(cursor1.getString(0));
            Treason.setText(cursor1.getString(1));
            Ttel.setText(cursor1.getString(2));
            cursor1.close();
            db1.close();
        }else if(status.equals("teacher")){
            show.setText("???????????????");
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
    }

    public void detailSubmit(View view){
        //??????
        yn = findViewById(R.id.y_or_n);
        String choose = null;
        for (int i = 0; i < yn.getChildCount(); i++) {
            RadioButton rd = (RadioButton) yn.getChildAt(i);
            if (rd.isChecked()) {
                choose = rd.getText().toString();
                break;
            }
        }
        if(status.equals("student")){
            if(result.equals("no")){
                if (choose.equals("???")){
                    //??????
                    String cancel = "yes";
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    String query = "update on_leave set CANCEL=\""+cancel+"\" "+"where ID=\""+id+"\" and STARTTIME=\""+start+"\"";
                    db.execSQL(query);
                    db.close();
                    Toast.makeText(DetailStu.this, "?????????????????????", Toast.LENGTH_LONG).show();
                    setResult(1,intent);
                    finish();
                }else{
                    Toast.makeText(DetailStu.this, "No action?????????", Toast.LENGTH_LONG).show();
                }
            }else if (result.equals("?????????")){
                if (choose.equals("???")){
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    String query = "delete from on_leave where ID=\""+id+"\" and STARTTIME=\""+start+"\" and CANCEL=\""+result+"\"";
                    db.execSQL(query);
                    db.close();
                    Toast.makeText(DetailStu.this, "?????????????????????", Toast.LENGTH_LONG).show();
                    setResult(1,intent);
                    finish();
                }else{
                    Toast.makeText(DetailStu.this, "No action?????????", Toast.LENGTH_LONG).show();
                }
            } else{
                show.setText("Error");
                Toast.makeText(DetailStu.this, "Error?????????", Toast.LENGTH_LONG).show();
            }
        }else if(status.equals("teacher")){  //???????????????
            String cancel;
            if(choose.equals("???")){
                cancel="no";
            }else{
                cancel="?????????";
            }
            SQLiteDatabase db1 = dbHelper.getReadableDatabase();
            String query1 = "select * from leave_request where ID=? and STARTTIME=?";
            Cursor cursor1 = db1.rawQuery(query1,new String[]{id,start});
            cursor1.moveToFirst();
            String query2 = "insert into on_leave values(\""+cursor1.getString(0)+"\",\""+cursor1.getString(1)+
                    "\",\""+cursor1.getString(2)+"\",\""+cursor1.getString(3)+"\",\""+cursor1.getString(4)+"\",\""+
                    cursor1.getString(5)+"\",\""+cancel+"\")";
            String query3 = "delete from leave_request where ID=\""+id+"\" and STARTTIME=\""+start+"\"";
            cursor1.close();
            db1.close();
            SQLiteDatabase db2 = dbHelper.getWritableDatabase();
            db2.execSQL(query2);
            db2.execSQL(query3);
            db2.close();
            Toast.makeText(DetailStu.this, "?????????????????????", Toast.LENGTH_LONG).show();
            setResult(2,intent);
            finish();
        }else{
            show.setText("Error");
            Toast.makeText(DetailStu.this, "Error?????????", Toast.LENGTH_LONG).show();
        }
    }


    //??????
    public void detailReturn(View view){
        if (status.equals("student")){
            setResult(1,intent);
        }else if(status.equals("teacher")){
            setResult(2,intent);
        }
        else{
            Toast.makeText(DetailStu.this, "DetailStuError?????????", Toast.LENGTH_LONG).show();
        }
        finish();
    }
}