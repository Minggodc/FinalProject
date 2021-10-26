package com.swufestu.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class StudentMain extends AppCompatActivity {
    public static final String TAG="StudentMain";
    String name,id;
    TextView xh,xm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        xh = findViewById(R.id.StuMainID);
        xm = findViewById(R.id.StuMainName);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        id = intent.getStringExtra("ID");

        xh.setText("学号："+id);
        xm.setText("姓名："+name);
    }

    public void request(View view){
        Intent toRequest = new Intent(this, RequestPage.class);
        toRequest.putExtra("ID",id);
        startActivityForResult(toRequest, 2);
    }

    public void record(View view){

    }

    public void myInfo(View view){

    }
}