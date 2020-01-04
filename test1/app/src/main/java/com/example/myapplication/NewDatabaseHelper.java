package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class NewDatabaseHelper extends SQLiteOpenHelper {

    private Context context;

    String create_user_sql =  "create table userlist(" +
            "Id text primary key," +
            "name text," +
            "email text," +
            "address text," +
            "password text," +
            "mobile text," +
            "level Integer)";

    String delete_user_sql = "delete from userlist";

    String select_user_sql = "select * from userlist";

    String create_new_sql = "create table newlist(" +
            "newId text primary key," +
            "title text," +
            "context text)";

    String delete_new_sql = "delete from newlist";

    String select_new_sql = "select * from newlist";

    String create_course_sql = "create table courselist(" +
            "courseId text primary key," +
            "instrucotrId text," +
            "courseName text," +
            "remindTime Integer," +
            "studentNumber Integer," +
            "content text," +
            "time text)";

    String delete_course_sql = "delete from courselist";

    String select_course_sql = "select * from courselist";

    public NewDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_new_sql);
        db.execSQL(create_course_sql);
        db.execSQL(create_user_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void updateCourses(SQLiteDatabase db, List<Course> courseList){
        db.execSQL(this.delete_course_sql);
        ContentValues values = new ContentValues();
        for(int i = 0 ; i < courseList.size(); i++)
        {
            values.put("courseId", courseList.get(i).getCourseid());
            values.put("instrucotrId", courseList.get(i).getDescription());
            values.put("courseName", courseList.get(i).getCoursename());
            values.put("remindTime", courseList.get(i).getRemindtime());
            values.put("content", courseList.get(i).getContent());
            values.put("time", courseList.get(i).getTime());
            db.insert("courselist",null , values);
        }
    }

    public List<Course> getCourses(SQLiteDatabase db){
        Cursor cursor = db.rawQuery(select_course_sql, null);
        ArrayList<Course> courseList = new ArrayList<Course>();

        if(cursor.moveToFirst()){
            do{
                String courseId = cursor.getString(cursor.getColumnIndex("id"));
                String courseName = cursor.getString(cursor.getColumnIndex("name"));
                String code = cursor.getString(cursor.getColumnIndex("code"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String price = cursor.getString(cursor.getColumnIndex("price"));
                String openDate = cursor.getString(cursor.getColumnIndex("openDate"));
                /*int remindTime = cursor.getInt(cursor.getColumnIndex("remindTime"));
                int studentNumber = cursor.getInt(cursor.getColumnIndex("studentNumber"));
                String content = cursor.getString(cursor.getColumnIndex("content"));*/
                String level = cursor.getString(cursor.getColumnIndex("level"));
                //String time = cursor.getString(cursor.getColumnIndex("time"));

                Course acourse = new Course(courseId, courseName, code, description,price,openDate,level);
                courseList.add(acourse);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return courseList;
    }

    public void updateUsers(SQLiteDatabase db, User auser){
        db.execSQL(this.delete_user_sql);
        ContentValues values = new ContentValues();
        values.put("Id", auser.getUserId());
        values.put("name", auser.getName());
        values.put("email", auser.getEmail());
        values.put("password", auser.getPassword());
        values.put("mobile", auser.getMobile());
        values.put("level", auser.getRoal());
        db.insert("userlist",null , values);
    }

    public User getUsers(SQLiteDatabase db){
        Cursor cursor = db.rawQuery(select_user_sql, null);
        User auser = null;

        if(cursor.moveToFirst()){
            do{
                String id = cursor.getString(cursor.getColumnIndex("Id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String address = cursor.getString(cursor.getColumnIndex("address"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String mobile = cursor.getString(cursor.getColumnIndex("mobile"));
                String level = cursor.getString(cursor.getColumnIndex("level"));

                auser = new User(id, name, email, password, mobile, level);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return auser;
    }
}
