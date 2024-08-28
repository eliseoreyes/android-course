package edu.fvtc.databasedemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseHelper";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE tblItem (id integer primary key autoincrement, description text );";
        Log.d(TAG, "onCreate: tblItem -> "+sql);
        sqLiteDatabase.execSQL(sql);

        sql = "INSERT INTO tblItem VALUES (1, 'Book');";
        sqLiteDatabase.execSQL(sql);

        sql = "CREATE TABLE IF NOT EXISTS tblResult(id integer primary key autoincrement NOT NULL, changeDate text NOT NULL, correct integer NOT NULL);";
        Log.d(TAG, "onCreate: tblResult -> "+sql);

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG, "onUpgrade: "+i+":"+i1 );


    }
}
