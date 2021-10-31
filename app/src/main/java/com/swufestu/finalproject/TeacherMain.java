package com.swufestu.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TeacherMain extends AppCompatActivity {

    TextView Tname,Txueyuan;
    String name,xueyuan,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);

        Tname = findViewById(R.id.TeaMainName);
        Txueyuan = findViewById(R.id.TeaMainXueyuan);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        xueyuan = intent.getStringExtra("xueyuan");
        id = intent.getStringExtra("ID");

        Tname.setText("姓名："+name);
        Txueyuan.setText("学院："+xueyuan);
    }

    //审核假条
    public void check(View view){
        Intent toRequest = new Intent(this, CheckPage.class);
        toRequest.putExtra("ID",id);
        startActivityForResult(toRequest, 6);
    }

    //查看未销假记录
    public void leaveInfo(View view){
        Intent toRequest = new Intent(this, TeacherQueryCancel.class);
        toRequest.putExtra("ID",id);
        startActivityForResult(toRequest, 7);
    }

    //查询请假记录
    public void queryInfo(View view){
        Intent toRequest = new Intent(this, TeacherQueryRecord.class);
        toRequest.putExtra("ID",id);
        startActivityForResult(toRequest, 8);
    }
}