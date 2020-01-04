package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountDatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    String create_account_sql = "create table account(" +
            "id integer primary key autoincrement," +
            "username text," +
            "password text," +
            "level integer)";

    public AccountDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_account_sql);
        ContentValues values = new ContentValues();
        values.clear();
        values.put("id", 1);
        values.put("username", "member");
        values.put("password", "member");
        values.put("level", "MEMBER");
        db.insert("account", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
