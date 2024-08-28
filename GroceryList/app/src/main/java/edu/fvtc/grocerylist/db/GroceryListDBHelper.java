package edu.fvtc.grocerylist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class GroceryListDBHelper extends SQLiteOpenHelper {

    public static final String TAG = "GroceryListDBHelper";
    public static final String DATABASE_NAME = "product.db";
    public static final int DATABASE_VERSION = 1;


    public static final String CREATE_PRODUCT_SQL = "CREATE table tblGroceryList (_id integer primary key autoincrement,"
                                                    + "item text not null, "
                                                    + "is_on_shopping_list integer not null, "
                                                    + "is_on_cart integer not null)";

    public GroceryListDBHelper(@Nullable Context context,
                               @Nullable String name,
                               @Nullable SQLiteDatabase.CursorFactory factory,
                               int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: " + CREATE_PRODUCT_SQL);
        db.execSQL(CREATE_PRODUCT_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: " + oldVersion + " : " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS tblTeam");
        onCreate(db);
    }

}
