package com.swufestu.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RequestPage extends AppCompatActivity {
    public static final String TAG = "RequestPage";
    EditText startTime,endTime,reason,tel;
    String now,id,start,end;
    DBHelper dbHelper;
    TextView show_result;
    String status;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_page);

        startTime = findViewById(R.id.starttime);
        endTime = findViewById(R.id.endtime);
        reason = findViewById(R.id.reason);
        tel = findViewById(R.id.stu_tel);
        show_result = findViewById(R.id.show_result);

        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        status = intent.getStringExtra("status");
        dbHelper = new DBHelper(getApplicationContext());

        submit = findViewById(R.id.submit);
        if(status.equals("change")){
            submit.setText("修改申请信息");
            String rea = intent.getStringExtra("reason");
            String t = intent.getStringExtra("tel");
            end = intent.getStringExtra("end");
            start = intent.getStringExtra("start");
            reason.setText(rea);
            tel.setText(t);
            endTime.setText(end);
            startTime.setText(start);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //获取系统当前时间
        Calendar calendar = Calendar.getInstance();
        now = formatter.format(calendar.getTime());


        startTime.setText(now);
        endTime.setText(now);

        //日期选择
        showCalendar(startTime);
        showCalendar(endTime);

    }

    public void submit(View view){
        String st = startTime.getText().toString().trim();
        String et = endTime.getText().toString().trim();
        String rea = reason.getText().toString().trim();
        String stu_tel = tel.getText().toString().trim();
        if(st.isEmpty()||et.isEmpty()||rea.isEmpty()||stu_tel.isEmpty()){
            Toast.makeText(RequestPage.this, "输入不能为空！！！", Toast.LENGTH_LONG).show();
        }else{

            //判断起终时间是否合法（起终时间不能在同一天）
            long diff1 = -1;
            long diff2 = -1;
            try {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date startTime = df.parse(st);
                Date endTime = df.parse(et);
                Date nowTime = df.parse(now);
                diff1 = (endTime.getTime() - startTime.getTime())/(1000 * 60 * 60 * 24);
                diff2 = (startTime.getTime() - nowTime.getTime())/(1000 * 60 * 60 * 24);
            }catch (Exception e){
                Toast.makeText(RequestPage.this, "时间有误！！！", Toast.LENGTH_LONG).show();
            }

            if(diff1<=0||diff2<0){
                Toast.makeText(RequestPage.this, "开始结束时间不合法！！！", Toast.LENGTH_LONG).show();
            }else{

                if (status.equals("request")){ //申请假条
                    SQLiteDatabase db3 = dbHelper.getReadableDatabase();
                    String query3 = "select * from leave_request where ID=? and STARTTIME=?";
                    Cursor cursor3 = db3.rawQuery(query3,new String[]{id,st});
                    if (cursor3!=null){
                        Toast.makeText(RequestPage.this, "已存在有相同开始时间的待审批假条！！！", Toast.LENGTH_LONG).show();
                    }else{
                        SQLiteDatabase db4 = dbHelper.getReadableDatabase();
                        String query4 = "select * from on_leave where ID=? and STARTTIME=?";
                        Cursor cursor4 = db4.rawQuery(query4,new String[]{id,st});
                        if(cursor4!=null){
                            Toast.makeText(RequestPage.this, "已存在有相同开始时间的假条！！！", Toast.LENGTH_LONG).show();
                        }else{
                            SQLiteDatabase db0 = dbHelper.getReadableDatabase();
                            String query_stuinfo = "select XUEYUAN,GRADE from student where ID=?";
                            Cursor cursor = db0.rawQuery(query_stuinfo,new String[]{id});
                            cursor.moveToFirst();
                            String xueyuan = cursor.getString(0);
                            String grade = cursor.getString(1);
                            cursor.close();
                            db0.close();

                            SQLiteDatabase db1 = dbHelper.getReadableDatabase();
                            String query_tid = "select ID from teacher where XUEYUAN=? and GRADE=?";
                            Cursor cursor1 = db1.rawQuery(query_tid,new String[]{xueyuan,grade});
                            cursor1.moveToFirst();
                            String tea_id = cursor1.getString(0);
                            cursor1.close();
                            db1.close();

                            SQLiteDatabase db2 = dbHelper.getWritableDatabase();
                            String query_leave_request = "insert into leave_request values(\""+id+"\",\""+tea_id+
                                    "\",\""+st+"\",\""+et+"\",\""+rea+"\",\""+stu_tel+"\");";
                            db2.execSQL(query_leave_request);
                            db2.close();
                            startTime.setText("");
                            endTime.setText("");
                            reason.setText("");
                            tel.setText("");
                            show_result.setText("申请成功，请到请假记录中查看");
                        }
                        cursor4.close();
                        db4.close();
                    }
                    cursor3.close();
                    db3.close();
                }else if (status.equals("change")){ //修改未通过申请的假条
                    SQLiteDatabase db4 = dbHelper.getReadableDatabase();
                    String query4 = "select * from on_leave where ID=? and STARTTIME=?";
                    Cursor cursor4 = db4.rawQuery(query4,new String[]{id,st});
                    if(cursor4!=null){
                        Toast.makeText(RequestPage.this, "已存在有相同开始时间的假条！！！", Toast.LENGTH_LONG).show();
                    }else{
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        String query = "update leave_request set STAETTIME=\""+startTime.getText().toString().trim()+"\",ENDTIME=\""+endTime.getText().toString().trim()+
                                "\",REASON=\""+reason.getText().toString().trim()+"\",STU_TEL=\""+tel.getText().toString().trim()+"\" where ID=\""+id+
                                "\" and STARTTIME=\""+start+"\"";
                        db.execSQL(query);
                        db.close();
                        show_result.setText("修改成功，请到请假记录中查看");
                    }
                }
            }
        }
    }

    public void requestReturn(View view){
        finish();
    }


    //调出日历进行日期选择
    public void showCalendar(EditText mEditText){
        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    showDatePickDlg(mEditText);
                    return true;
                }

                return false;
            }
        });
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    showDatePickDlg(mEditText);
                }
            }
        });
    }
    protected void showDatePickDlg(EditText mEditText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(RequestPage.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mEditText.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }



}