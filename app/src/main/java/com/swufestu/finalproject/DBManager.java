package com.swufestu.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {
    private DBHelper dbHelper;
    private String tableStu,tableTea,tableRequest,tableLeave;

    public DBManager(Context context){
        dbHelper = new DBHelper(context);
        tableStu = dbHelper.student;
        tableTea = dbHelper.teacher;
        tableRequest = dbHelper.leave_request;
        tableLeave = dbHelper.on_leave;
    }


}
