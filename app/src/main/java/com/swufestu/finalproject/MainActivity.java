package com.swufestu.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String TAG="MainActivity";

    EditText ID,password;
    RadioGroup choosen;
    private DBHelper dbHelper;
    private String tableStu,tableTea,tableRequest,tableLeave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ID = findViewById(R.id.id);
        password = findViewById(R.id.password);
    }

    public void Login(View view) {
        if (ID.getText().toString().isEmpty()||password.getText().toString().isEmpty()){
            Toast.makeText(MainActivity.this, "输入不能为空！！！", Toast.LENGTH_LONG).show();
        }else{
            //判断学生还是导员
            choosen = findViewById(R.id.choosen);
            String choose = null;
            for (int i = 0; i < choosen.getChildCount(); i++) {
                RadioButton rd = (RadioButton) choosen.getChildAt(i);
                if (rd.isChecked()) {
                    choose = rd.getText().toString();
                    break;
                }
            }

            //判断密码是否正确并实现页面跳转
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            if(choose.equals("学生")){
                Cursor cursor = db.rawQuery("select ID,PWD,NAME from student where ID=?",new String[]{ID.getText().toString().trim()});
                if(cursor.moveToFirst()&&cursor.getCount()!=0){
                    String db_id = cursor.getString(0);
                    String db_pwd = cursor.getString(1);
                    String db_name = cursor.getString(2);
                    if (db_pwd.equals(password.getText().toString().trim())){
                        //跳转页面到学生页
                        Intent toRequest = new Intent(this, StudentMain.class);
                        toRequest.putExtra("ID",db_id);
                        toRequest.putExtra("name",db_name);
                        startActivityForResult(toRequest, 1);
                    }else{
                        Toast.makeText(MainActivity.this, "密码错误！！！", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "登录失败或账号不存在！！！", Toast.LENGTH_LONG).show();
                }
                cursor.close();
            }else if(choose.equals("辅导员")){
                Cursor cursor = db.rawQuery("select ID,PWD,XUEYUAN,NAME from teacher where ID=?",new String[]{ID.getText().toString().trim()});
                if(cursor.moveToFirst()&&cursor.getCount()!=0){
                    String db_id = cursor.getString(0);
                    String pwd = cursor.getString(1);
                    String xueyuan = cursor.getString(2);
                    String db_name = cursor.getString(3);
                    if (pwd.equals(password.getText().toString().trim())){
                        //跳转页面到辅导员页
                        Intent toRequest = new Intent(this, TeacherMain.class);
                        toRequest.putExtra("ID",db_id);
                        toRequest.putExtra("name",db_name);
                        toRequest.putExtra("xueyuan",xueyuan);
                        startActivityForResult(toRequest, 2);
                    }else{
                        Toast.makeText(MainActivity.this, "密码错误！！！", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "登录失败或账号不存在！！！", Toast.LENGTH_LONG).show();
                }
                cursor.close();
            }else{
                Toast.makeText(MainActivity.this, "登录失败！！！", Toast.LENGTH_LONG).show();
            }
            db.close();
        }
    }

    public void toChangePWD(View view){
        Intent intent = new Intent(this,ChangePwd.class);
        startActivityForResult(intent,111);
    }
}