package com.swufestu.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.media.DeniedByServerException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ChangePwd extends AppCompatActivity {
    Intent intent;
    DBHelper dbHelper;
    EditText cid,copwd,cnpwd,ccpwd;
    RadioGroup choosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        cid = findViewById(R.id.cpwdid);
        copwd = findViewById(R.id.oldpwd);
        cnpwd = findViewById(R.id.newpwd);
        ccpwd = findViewById(R.id.confirmpwd);
        dbHelper = new DBHelper(getApplicationContext());
        intent = getIntent();

    }

    public void changePwd(View view){

        //判断学生还是导员
        choosen = findViewById(R.id.choosenpwd);
        String choose = null;
        for (int i = 0; i < choosen.getChildCount(); i++) {
            RadioButton rd = (RadioButton) choosen.getChildAt(i);
            if (rd.isChecked()) {
                choose = rd.getText().toString();
                break;
            }
        }

        String id = cid.getText().toString().trim();
        String oldpwd = copwd.getText().toString().trim();
        String newpwd = cnpwd.getText().toString().trim();
        String confirmpwd = ccpwd.getText().toString().trim();
        if(id.isEmpty()||oldpwd.isEmpty()||newpwd.isEmpty()||confirmpwd.isEmpty()){
            Toast.makeText(ChangePwd.this, "输入不能为空！！！", Toast.LENGTH_LONG).show();
        }else{
            if(newpwd.equals(confirmpwd)){
                if(choose.equals("学生")){
                    SQLiteDatabase db1 = dbHelper.getReadableDatabase();
                    String query = "select PWD from student where id=?";
                    Cursor cursor = db1.rawQuery(query,new String[]{id});
                    if(cursor!=null&&cursor.moveToFirst()){
                        String dbpwd = cursor.getString(0);
                        if(dbpwd.equals(oldpwd)){
                            SQLiteDatabase db2 = dbHelper.getWritableDatabase();
                            String query1 = "update student set PWD=\""+newpwd+"\" where ID=\""+id+"\"";
                            db2.execSQL(query1);
                            db2.close();
                            Toast.makeText(ChangePwd.this, "修改成功！！！", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }else{
                        Toast.makeText(ChangePwd.this, "账号不存在！！！", Toast.LENGTH_LONG).show();
                    }
                    cursor.close();
                    db1.close();
                }else if(choose.equals("辅导员")){
                    SQLiteDatabase db1 = dbHelper.getReadableDatabase();
                    String query = "select PWD from student where id=?";
                    Cursor cursor = db1.rawQuery(query,new String[]{id});
                    if(cursor!=null&&cursor.moveToFirst()){
                        String dbpwd = cursor.getString(0);
                        if(dbpwd.equals(oldpwd)){
                            SQLiteDatabase db2 = dbHelper.getWritableDatabase();
                            String query1 = "update teacher set PWD=\""+newpwd+"\" where ID=\""+id+"\"";
                            db2.execSQL(query1);
                            db2.close();
                            Toast.makeText(ChangePwd.this, "修改成功！！！", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }else{
                        Toast.makeText(ChangePwd.this, "账号不存在！！！", Toast.LENGTH_LONG).show();
                    }
                    cursor.close();
                    db1.close();
                }
            }else{
                Toast.makeText(ChangePwd.this, "新密码与确认密码不相同！！！", Toast.LENGTH_LONG).show();
            }
        }
    }
}