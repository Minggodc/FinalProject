package com.swufestu.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    public static final String DB_NAME = "mydatabase.db";
    public static final String student = "student";
    public static final String teacher = "teacher";
    public static final String leave_request = "leave_request";
    public static final String on_leave = "on_leave";

     public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
         super(context, name, factory, version);
     }
     public DBHelper(Context context){
         super(context, DB_NAME, null, VERSION);
     }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+student+"(ID TEXT PRIMARY KEY, NAME TEXT, " +
                "XUEYUAN TEXT, MAJOR TEXT, GRADE TEXT, PWD TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+teacher+"(ID TEXT PRIMARY KEY, NAME TEXT, " +
                "XUEYUAN TEXT, GRADE TEXT, TEL TEXT, PWD TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+leave_request+"(ID TEXT, TEACHER_ID TEXT, " +
                "STARTTIME TEXT, ENDTIME TEXT, REASON TEXT, STU_TEL TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS "+on_leave+"(ID TEXT, TEACHER_ID TEXT, " +
                "STARTTIME TEXT, ENDTIME TEXT, REASON TEXT, STU_TEL TEXT, CANCEL INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
