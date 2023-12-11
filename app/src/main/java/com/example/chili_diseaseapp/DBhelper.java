package com.example.chili_diseaseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    public static final String dbname= "login.db";
    public DBhelper(Context context) {
        super(context, "login.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users( email TEXT primary key, password TEXT, confirm_password Text)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newVersion) {
        db.execSQL("drop table if exists users");
    }

    public Boolean insertData( String email, String password){
        SQLiteDatabase sqliteDB = this.getWritableDatabase();
        ContentValues contextvalues = new ContentValues();

        contextvalues.put("email",email);
        contextvalues.put("password",password);



        long result=sqliteDB.insert("users",null,contextvalues);
        if(result == -1) {
            return false;
        }else {
            return true;
        }

    }
    public Boolean checkEmail (String email){
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        Cursor cursor = sqlDB.rawQuery("select * from users where email=?", new String[]{email});

        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    public Boolean checkEmailPassword (String email, String password){
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        Cursor cursor = sqlDB.rawQuery("select * from users where email=? and password=?", new String[]{email,password});
        if(cursor.getCount()>0){
            return true;
        }else{
            return false;
        }
    }


}
