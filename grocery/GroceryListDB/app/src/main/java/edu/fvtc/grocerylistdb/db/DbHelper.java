package edu.fvtc.grocerylistdb.db;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static String TAG = "DbHelper";
    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "tblGroceryList";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ITEM = "item";
    public static final String COLUMN_IS_ON_SHOPPING = "is_on_shopping_list";
    public static final String COLUMN_IS_ON_CART = "is_on_cart";
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_ITEM + " TEXT NOT NULL," +
                    COLUMN_IS_ON_SHOPPING + " INTEGER," +
                    COLUMN_IS_ON_CART + " INTEGER)";
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: "+CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
