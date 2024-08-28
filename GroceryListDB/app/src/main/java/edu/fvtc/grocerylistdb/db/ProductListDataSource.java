package edu.fvtc.grocerylistdb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import edu.fvtc.grocerylistdb.models.Product;

public class ProductListDataSource {

    public static final String TAG = "ProductListDataSource";
    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public ProductListDataSource(Context context){
        //context.deleteDatabase("products.db");
        dbHelper = new DbHelper(context);
    }
/*
    public void open() throws SQLException {
        open(false);
    }

    public void open(boolean refresh) throws SQLException{
        database = dbHelper.getWritableDatabase();
        if (refresh) refreshData();
    }
    public void close(){
        dbHelper.close();
    }

    //Insert
    public int insert (Product product){

        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_ITEM, product.getProductDescription());
        values.put(DbHelper.COLUMN_IS_ON_SHOPPING, product.getIsOnShoppingList());
        values.put(DbHelper.COLUMN_IS_ON_CART, product.getIsInCart());

       int result = (int) database.insert(DbHelper.TABLE_NAME,null, values);

       return result;
    }

    //Get
    public Product get(int id){

        Product product = null;

        try {
            String query = String.format("SELECT * FROM %s WHERE _id = %d", DbHelper.TABLE_NAME, id);
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                Log.d(TAG, "get: While Start");
                product = new Product(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
                cursor.moveToNext();
            }
        }catch (Exception ex){
            Log.d(TAG, "get: "+ex.getMessage());
            ex.printStackTrace();
        }

        return product;
    }

    //Get All
    public List<Product> getAll(int shoppingList) throws SQLException{

        Log.d(TAG, "getAll: ");
        List<Product> products = new ArrayList<Product>();
        String query;

        try {

            if (shoppingList == 0)
                query = String.format("SELECT * FROM %s",DbHelper.TABLE_NAME);
            else
                query = String.format("SELECT * FROM %s WHERE %s = %d",DbHelper.TABLE_NAME, DbHelper.COLUMN_IS_ON_CART, shoppingList);

            Cursor cursor = database.rawQuery(query, null);
            Product product;

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                products.add(new Product(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3)));
                cursor.moveToNext();
            }

        }catch (Exception ex){
            Log.d(TAG, "getAll: "+ex.getMessage());
        }

        return products;
    }

    //Delete Product
    public int delete(Product product){
        int rowsAffected = 0;
        try{
            int id = product.getId();

            if (id < 1)
                rowsAffected = 0;
            rowsAffected = delete(id);

        }catch(Exception ex){
            Log.d(TAG, "delete: "+ex.getMessage());
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    //Delete Product
    public int deleteBulk(List<Product> products){
        int rowsAffected = 0;
        try{
            for (Product product : products) {
                int id = product.getId();

                if (product.getIsInCart() == 1)
                    rowsAffected += delete(id);
            }

        }catch(Exception ex){
            Log.d(TAG, "delete: "+ex.getMessage());
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    //Delete
    public int delete(int id){
        int rowsAffected = 0;

        try {
            rowsAffected = database.delete(DbHelper.TABLE_NAME, "_id = "+id, null);
        }catch(Exception ex){
            Log.d(TAG, "delete: "+ex.getMessage());
            ex.printStackTrace();
        }
        return rowsAffected;
    }


    //Delete All
    public int deleteAll(){

        int rowsAffected = 0;
        try {
            rowsAffected = database.delete(DbHelper.TABLE_NAME, null, null);
        }catch (Exception ex){
            Log.d(TAG, "deleteAll: "+ex.getMessage());
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    //Update
    public int update(Product product){

        int rowsAffected = 0;

        Log.d(TAG, "update before: "+product.toString());

        if (product.getId() < 1)
            return  insert(product);

        try {
            ContentValues values = new ContentValues();

            values.put(DbHelper.COLUMN_ITEM, product.getProductDescription());
            values.put(DbHelper.COLUMN_IS_ON_SHOPPING, product.getIsOnShoppingList());
            values.put(DbHelper.COLUMN_IS_ON_CART, product.getIsInCart());

            String WHERE_CLAUSE = "_id = " + product.getId();

            Log.d(TAG, "updating: "+WHERE_CLAUSE);
            rowsAffected = (int) database.update(DbHelper.TABLE_NAME, values, WHERE_CLAUSE, null);

        }catch(Exception ex){
            Log.d(TAG, "update: "+ex.getMessage());
            ex.printStackTrace();
        }
        return rowsAffected;
    }

    //Update
    public int updateBulk(List<Product> products){

        int rowsAffected = 0;

        try {
            ContentValues values = new ContentValues();

            for (Product product : products) {
                values.put(DbHelper.COLUMN_ITEM, product.getProductDescription());
                values.put(DbHelper.COLUMN_IS_ON_SHOPPING, product.getIsOnShoppingList());
                values.put(DbHelper.COLUMN_IS_ON_CART, 0);

                String WHERE_CLAUSE = "_id = " + product.getId();

                rowsAffected += (int) database.update(DbHelper.TABLE_NAME, values, WHERE_CLAUSE, null);

            }

        }catch(Exception ex){
            Log.d(TAG, "update: "+ex.getMessage());
            ex.printStackTrace();
        }
        return rowsAffected;
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

 */
}
