package com.example.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObservable;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class database extends SQLiteOpenHelper {
   private static final String dbname = "signup.db";
    private static final String table_users = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    public database(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        String a = "CREATE TABLE " + table_users + "(" + KEY_ID  + "INTEGER PRIMARY KEY,"+ KEY_NAME + "TEXT," + KEY_EMAIL + "TEXT," + KEY_PASSWORD + "TEXT" + ")";
        sqLiteDatabase.execSQL(a);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + table_users);
        onCreate(sqLiteDatabase);
    }


    /// insert data query
    public boolean insert_data(String name, String email, String password) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues c=new ContentValues();
        c.put(KEY_NAME, name);
        c.put(KEY_EMAIL, email);
        c.put(KEY_PASSWORD, password);
        long r=db.insert(table_users, null, c);
        if (r==-1) return false;
         else
            return true;
    }


    /// update data query
    public boolean update_data(String name, String email, String password){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put(KEY_NAME, name);
        c.put(KEY_PASSWORD, password);
        Cursor cursor=db.rawQuery("select * from users where email=?", new String[]{KEY_EMAIL});
        if (cursor.getCount()>0){
            long r = db.update("users", c, "email=?", new String[]{KEY_EMAIL});
            if (r==-1){
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }


    }


    /// Delete a particular data by email
    public boolean delete_data(String KEY_EMAIL){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from users where email=?", new String[]{KEY_EMAIL});
        if (cursor.getCount()>0){
            long r = db.delete("users", "email=?", new String[]{KEY_EMAIL});
            if (r==-1){
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


    /// Get All data query
    public Cursor getinfo(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from " + table_users, null);
        return cursor;
    }
}
