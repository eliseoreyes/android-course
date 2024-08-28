package edu.fvtc.teams;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class TeamDBHelper extends SQLiteOpenHelper {
    public static final String TAG = "TeamDBHelper";
    public static final String DATABASE_NAME = "teams.db";
    public static final int DATABASE_VERSION = 1;

    public static final String CREATE_TEAMS_SQL =
            "CREATE table tblTeam (_id integer primary key autoincrement,"
                    + "name text not null, "
                    + "city text not null, "
                    + "imgId integer not null, "
                    + "isFavorite bit, "
                    + "rating float, "
                    + "phone text not null, "
                    + "latitude float, "
                    + "longitude float)";

    public TeamDBHelper(@Nullable Context context,
                        @Nullable String name,
                        @Nullable SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: " + CREATE_TEAMS_SQL);
        db.execSQL(CREATE_TEAMS_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: " + oldVersion + " : " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS tblTeam");
        onCreate(db);
    }
}
