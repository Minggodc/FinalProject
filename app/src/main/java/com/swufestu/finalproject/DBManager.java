package com.swufestu.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private DBHelper dbHelper;

    public DBManager(Context context){
        dbHelper  = new DBHelper(context);
    }

    public String getNameByID(String dbname,String id){
        String query = "select NAME from "+dbname+" where ID=?";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,new String[]{id});
        cursor.moveToFirst();
        String name = cursor.getString(0);
        return name;
    }

    public String getXueyuanByID(String dbname,String id){
        String query = "select XUEYUAN from "+dbname+" where ID=?";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,new String[]{id});
        cursor.moveToFirst();
        String xueyuan = cursor.getString(0);
        return xueyuan;
    }
}
