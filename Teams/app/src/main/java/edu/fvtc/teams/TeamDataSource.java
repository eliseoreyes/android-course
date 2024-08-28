package edu.fvtc.teams;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class TeamDataSource {
    SQLiteDatabase database;
    TeamDBHelper dbHelper;
    public static final String TAG = "TeamDataSource";

    public TeamDataSource(Context context){
        dbHelper = new TeamDBHelper(context, TeamDBHelper.DATABASE_NAME, null, TeamDBHelper.DATABASE_VERSION);
    }

    public void open() throws SQLException{
        open(false);
    }

    public void open(boolean refresh) throws SQLException{
        database = dbHelper.getWritableDatabase();
        Log.d(TAG, "open: " + database.isOpen());
        if(refresh) refreshData();
    }

    public void close()
    {
        dbHelper.close();
    }

    public void refreshData() {
        Log.d(TAG, "refreshData: ");
        ArrayList<Team> teams = new ArrayList<Team>();
        teams.add(new Team(1, "Packers", "Green Bay", "9203551234", 4.5f, true, R.drawable.packers, 0.0,0.0));
        teams.add(new Team(2, "Vikings", "Minneapolis", "11111111111", 2.5f, false, R.drawable.vikings, 0.0,0.0));
        teams.add(new Team(3, "Lions", "Detroit", "2222222222", 1.3f, true, R.drawable.lions, 0.0,0.0));
        teams.add(new Team(4, "Bears", "Chicago", "3333333333", 2.0f, false, R.drawable.bears, 0.0,0.0));

        int results = 0;
        for(Team team: teams)
        {
            results += insert(team);
        }
        Log.d(TAG, "refreshData: Inserted " + results + " rows...");
    }

    public Team get(int id)
    {
        ArrayList<Team> teams = new ArrayList<Team>();
        Team team = null;

        try{
            String query = "Select * from tblTeam where _id = " + id;
            Cursor cursor = database.rawQuery(query, null);

            //Cursor cursor = database.query("tblTeam",null, null, null, null, null, null);

            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                team = new Team();
                team.setId(cursor.getInt(0));
                team.setName(cursor.getString(1));
                team.setCity(cursor.getString(2));
                //team.setImgId(cursor.getInt(3));

                Boolean fav = (cursor.getInt(4) == 1);
                team.setIsfavorite(fav);

                team.setRating(cursor.getFloat(5));
                team.setCellphone(cursor.getString(6));

                team.setLatitude(cursor.getDouble(7));
                team.setLongitude(cursor.getDouble(8));

                Log.d(TAG, "get: " + team.toString());

                cursor.moveToNext();

            }
        }
        catch(Exception e)
        {
            Log.d(TAG, "get: " + e.getMessage());
            e.printStackTrace();
        }
        return team;
    }
    public ArrayList<Team> get(String sortBy, String sortOrder)
    {
        ArrayList<Team> teams = new ArrayList<Team>();
        Log.d(TAG, "get: Start");

        try{
            String query = "Select * from tblTeam ORDER BY " + sortBy + " " + sortOrder;
            Cursor cursor = database.rawQuery(query, null);

            Team team;
            cursor.moveToFirst();

            while(!cursor.isAfterLast())
            {
                team = new Team();
                team.setId(cursor.getInt(0));
                team.setName(cursor.getString(1));
                team.setCity(cursor.getString(2));
                //team.setImgId(cursor.getInt(3));

                Boolean fav = (cursor.getInt(4) == 1);
                team.setIsfavorite(fav);

                team.setRating(cursor.getFloat(5));
                team.setCellphone(cursor.getString(6));

                team.setLatitude(cursor.getDouble(7));
                team.setLongitude(cursor.getDouble(8));

                //if(team.getImgId() == 0)
                //    team.setImgId(R.drawable.photoicon);

                teams.add(team);
                Log.d(TAG, "get: " + team.toString());

                cursor.moveToNext();

            }
        }
        catch(Exception e)
        {
            Log.d(TAG, "get: " + e.getMessage());
            e.printStackTrace();
        }
        return teams;
    }

    public int deleteAll()
    {
        try{
            return database.delete("tblTeam", null, null);
        }
        catch(Exception e)
        {
            Log.d(TAG, "get: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(Team team)
    {
        try{
            int id = team.getId();
            if(id < 1)
                return 0;
            return delete(id);
        }
        catch(Exception e)
        {
            Log.d(TAG, "get: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public int delete(int id)
    {
        try{
            return database.delete("tblTeam", "_id = " + id, null);
        }
        catch(Exception e)
        {
            Log.d(TAG, "get: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public int getNewId()
    {
        int lastId;
        try{
            String query = "SELECT max(_id) from tblTeam";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            lastId = cursor.getInt(0) + 1;
            cursor.close();
        }
        catch(Exception e)
        {
            lastId = -1;
        }
        return lastId;

    }

    public int update(Team team)
    {
        Log.d(TAG, "update: Start" + team.toString());
        int rowsaffected = 0;

        if(team.getId() < 1)
            return insert(team);

        try{
            ContentValues values = new ContentValues();
            values.put("name", team.getName());
            values.put("city", team.getCity());
            //values.put("imgId", team.getImgId());
            values.put("isFavorite", team.getIsfavorite());
            values.put("rating", team.getRating());
            values.put("phone", team.getCellphone());
            values.put("latitude", team.getLatitude());
            values.put("longitude", team.getLongitude());

            String where = "_id = " + team.getId();

            rowsaffected = (int)database.update("tblTeam", values, where, null);
        }
        catch(Exception e)
        {
            Log.d(TAG, "get: " + e.getMessage());
            e.printStackTrace();
        }
        return rowsaffected;
    }
    public int insert(Team team)
    {
        Log.d(TAG, "insert: Start");
        int rowsaffected = 0;

        try{
            ContentValues values = new ContentValues();
            values.put("name", team.getName());
            values.put("city", team.getCity());
            //values.put("imgId", team.getImgId());
            values.put("isFavorite", team.getIsfavorite());
            values.put("rating", team.getRating());
            values.put("phone", team.getCellphone());
            values.put("latitude", team.getLatitude());
            values.put("longitude", team.getLongitude());

            rowsaffected = (int)database.insert("tblTeam", null, values);
        }
        catch(Exception e)
        {
            Log.d(TAG, "get: " + e.getMessage());
            e.printStackTrace();
        }
        return rowsaffected;

    }
}
