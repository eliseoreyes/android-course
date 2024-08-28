package edu.fvtc.databasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.fvtc.databasedemo.db.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";
    DatabaseHelper helper;
    SQLiteDatabase db;
    TextView tvInfo;
    Button btnGetItems, btnInsert, btnUpdate, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetItems = findViewById(R.id.btnGetItems);
        btnInsert = findViewById(R.id.btnInsert);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        tvInfo = findViewById(R.id.tvInfo);

        btnGetItems.setOnClickListener(this);
        btnInsert.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        helper = new DatabaseHelper(this, "items.db", null, 1);
        db = helper.getWritableDatabase();
        insert();
    }

    @Override
    public void onClick(View view) {
        int buttonId = view.getId();

        if (buttonId == R.id.btnGetItems) {
            Log.d(TAG, "onClick: GetItems");
            getItems();
        }else if (buttonId == R.id.btnInsert) {
            Log.d(TAG, "onClick: Insert");
            insert();
        }else if (buttonId == R.id.btnUpdate) {
            Log.d(TAG, "onClick: Update");
            update();
        }else {
            Log.d(TAG, "onClick: Delete");
            delete();
        }
    }

    private void getItems() {

        if (db != null){
            Cursor cursor = db.rawQuery("SELECT * FROM tblItem;", null );
            String msg = "";

            while (cursor.moveToNext()){
                int id = cursor.getInt(0);
                String description = cursor.getString(1);
                Log.d(TAG, "getItems -> "+id+":"+description);
                msg += id + ") " + description + "\r\n";
            }
            cursor.close();
            tvInfo.setText(msg);
        }
    }
    private void insert() {
        try {
            if(db != null){
                ContentValues contentValues = new ContentValues();
                contentValues.put("Description", "Birdbath");
                db.insert("tblItem", null, contentValues);
                getItems();
            }
        }catch(Exception ex){
            Log.d(TAG, "insert: "+ex.getMessage());
        }
    }
    private void update() {
        try {
            if(db != null) {
                String whereClause = "Id == 2";
                ContentValues contentValues = new ContentValues();
                db.update("tblItem", contentValues, whereClause, null);
                getItems();
            }
        }catch(Exception ex){
            Log.d(TAG, "insert: "+ex.getMessage());
        }
    }
    private void delete() {
        try {
            if (db != null){
                //Cursor cursor = db.rawQuery("delete from tblItem", null);
                // or
                db.delete("tblItem", null, null);
                db.execSQL("delete from tblItem");
                //cursor.close();

                Log.d(TAG, "delete: deletedddd");
                getItems();
                db.close();
            }
        }catch(Exception ex){
            Log.d(TAG, "insert: "+ex.getMessage());
        }
    }






}