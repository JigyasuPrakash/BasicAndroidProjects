package com.genwiser.allinone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyDatabase.db";
    public static final String TABLE_NAME = "student";
    public static final String NAME = "name";
    public static final String NUMBER = "number";

    public DBHelper(Context context){
        super (context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table student "+"(id integer primary key autoincrement, name text, number text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists student");
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String name, String number){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(NAME,name);
        content.put(NUMBER,number);
        database.insert(TABLE_NAME,null,content);
        return true;
    }

    public Cursor getData(){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.rawQuery("select * from "+TABLE_NAME,null);
    }
}
