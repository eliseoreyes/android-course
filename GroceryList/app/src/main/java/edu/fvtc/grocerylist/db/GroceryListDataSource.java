package edu.fvtc.grocerylist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import edu.fvtc.grocerylist.models.Product;

public class GroceryListDataSource {

    SQLiteDatabase db;
    GroceryListDBHelper dbHelper;
    public static final String TAG = "GroceryListDataSource";

    public GroceryListDataSource(Context context){
        Log.d(TAG, "GroceryListDataSource: "+ GroceryListDBHelper.DATABASE_NAME);
        dbHelper = new GroceryListDBHelper(context, GroceryListDBHelper.DATABASE_NAME, null, GroceryListDBHelper.DATABASE_VERSION);
        dbHelper.getReadableDatabase();
    }

    public void open() throws SQLException {
        try {
            open(false);
        }catch (SQLException ex){
            Log.d(TAG, "open: "+ex.getMessage());
            throw ex;
        }
    }

    public void open(boolean refresh) throws SQLException {

            db = dbHelper.getWritableDatabase();
            Log.d(TAG, "open: "+refresh);
            if (refresh)
                refreshData();

    }

    public void close() throws SQLException{

        try {
            dbHelper.close();
        }catch (SQLException ex){
            Log.d(TAG, "close: "+ex.getMessage());
            throw  ex;
        }
    }

    public void refreshData(){
        Log.d(TAG, "refreshData: ");
        ArrayList<Product> products = new ArrayList<Product>();

        products.add(new Product(1,"Lemon", 0, 0));
        products.add(new Product(2,"Beer", 0, 0));
        products.add(new Product(3,"Apple", 0, 0));
        products.add(new Product(4,"Lettuce", 0, 0));

        int results = 0;
        for (Product product : products) {
            results += insert(product);
        }
    }

    public ArrayList<Product> get(){

        ArrayList<Product> products = new ArrayList<>();

        Log.d(TAG, "get: ");
        try{
            String SQL = "SELECT * FROM tblGroceryList";
            Cursor cursor = db.rawQuery(SQL, null);
            Product product;
            cursor.moveToFirst();

            while (!cursor.isAfterLast()){
                product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
                products.add(product);
                cursor.moveToNext();
            }
        }catch (Exception ex){
            Log.d(TAG, "get: "+ex.getMessage());
        }
        return  products;
    }

    public int insert(Product product){
        Log.d(TAG, "insert: ");
        int rowsAffected = 0;

        try {

            ContentValues values = new ContentValues();
            values.put("Item", product.getProductDescription());
            values.put("is_on_shopping_list", product.getIsOnShoppingList());
            values.put("is_in_cart", product.getIsInCart());
            rowsAffected = (int) db.insert("tblGroceryList", null, values);

        }catch (Exception ex){
            Log.d(TAG, "insert: "+ex.getMessage());
        }

        return rowsAffected;
    }
}
