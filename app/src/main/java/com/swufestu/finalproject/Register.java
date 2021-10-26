package com.swufestu.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    public static final String TAG="Register";
    private DBHelper dbHelper;
    private String student;
    EditText xuehao,xingming,xueyuan,zhuanye,nianji,mima,queren;
    GetStuInfo stu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

       dbHelper = new DBHelper(getApplicationContext());
       student = dbHelper.student;
       xuehao = findViewById(R.id.xuehao);
       xingming = findViewById(R.id.xingming);
       xueyuan = findViewById(R.id.xueyuan);
       zhuanye = findViewById(R.id.zhuanye);
       nianji = findViewById(R.id.nianji);
       mima = findViewById(R.id.mima);
       queren = findViewById(R.id.queren);

    }

    public void regStu(View view){
        if (xuehao.getText().toString().isEmpty()&&xingming.getText().toString().isEmpty()
                &&xueyuan.getText().toString().isEmpty()&&zhuanye.getText().toString().isEmpty()
                &&nianji.getText().toString().isEmpty()&&mima.getText().toString().isEmpty()
                &&queren.getText().toString().isEmpty()){
            Toast.makeText(Register.this, "输入不能为空！！！", Toast.LENGTH_LONG).show();
        }else{
            if(mima.getText().toString().trim().equals(queren.getText().toString().trim())){
                stu = new GetStuInfo();
                stu.setId(xuehao.getText().toString().trim());
                stu.setName(xingming.getText().toString().trim());
                stu.setXueyuan(xueyuan.getText().toString().trim());
                stu.setMajor(zhuanye.getText().toString().trim());
                stu.setGrade(nianji.getText().toString().trim());
                stu.setPwd(mima.getText().toString().trim());
                AddToStu();
            }else{
                Toast.makeText(Register.this, "两次密码输入不一致！！！", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void AddToStu(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "INSERT INTO "+student+" VALUES(\""+stu.getId()+"\",\""+stu.getName()+"\",\""+stu.getXueyuan()+
                "\",\""+stu.getMajor()+"\",\""+stu.getGrade()+"\",\""+stu.getPwd()+"\")";
        db.execSQL(sql);
        Log.i(TAG, "Insert sql: "+sql);
    }

}