package edu.fvtc.grocerylistdb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;

import edu.fvtc.grocerylistdb.api.RestClient;
import edu.fvtc.grocerylistdb.api.VolleyCallback;
import edu.fvtc.grocerylistdb.db.ProductListDataSource;
import edu.fvtc.grocerylistdb.models.MenuOptions;
import edu.fvtc.grocerylistdb.models.Product;
import edu.fvtc.grocerylistdb.models.WriteMode;
import edu.fvtc.grocerylistdb.utils.FileIO;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final String FILENAME = "products.txt";
    public String API = "https://fvtcdp.azurewebsites.net/api/groceryList/";
    Product product = null;
    ArrayList<Product> products;
    ArrayList<String> productData;
    ProductListDataSource ds = new ProductListDataSource(this);
    SharedPreferences sp;
    public static String  name;

    ProductAdapter productAdapter;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            int id = products.get(position).getId();
            Product product = products.get(position);

            Intent intent = new Intent(MainActivity.this, ProductEditActivity.class);
            intent.putExtra("productId", product.getId());
            startActivity(intent);
        }
    };

    private CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) buttonView.getTag();
            int position = viewHolder.getAdapterPosition();
            int id = products.get(position).getId();
            products.get(position).setIsInCart(isChecked);
            RestClient.execPutRequest(products.get(position), API.substring(0, 49) + id, MainActivity.this, new VolleyCallback() {
                @Override
                public void onSuccess(ArrayList<Product> result) {
                    rebindScreen();
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get sharePreference
        sp = getApplicationContext().getSharedPreferences("ownerPref", Context.MODE_PRIVATE);

        name = sp.getString("name", "");

        if (name.equals("")){
            Intent intent = new Intent(this, SetOwner.class );
            startActivity(intent);
        }else{
            API += name;
            readFromAPI(MenuOptions.MASTER_LIST.toString(), "Master List");
        }

        //showList(MenuOptions.MASTER_LIST.toString(), "Master List");
       // initDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if (item.getItemId() == R.id.showMaster) {
            readFromAPI(MenuOptions.MASTER_LIST.toString(), "Master List");
            return true;
        }else if (item.getItemId() == R.id.showShopList) {
            readFromAPI(MenuOptions.SHOPPING_LIST.toString(), "Shopping List for "+name);
            return true;
        }else if (item.getItemId() == R.id.addItem) {
            addItemDialog();
            return true;
        }else if (item.getItemId() == R.id.clearAll) {
            clearAllCheckboxes();
            return true;
        }else if (item.getItemId() == R.id.delChecked) {
            deleteChecked();
            recreate();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void addItemDialog() {

        Intent intent = new Intent(MainActivity.this, ProductAddActivity.class);
        startActivity(intent);
        /*
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View addView = layoutInflater.inflate(R.layout.add_item, null);

        new AlertDialog.Builder(this)
                .setTitle("Add Item")
                .setView(addView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        TextInputEditText txtItem = addView.findViewById(R.id.txtAddItem);
                        String item = txtItem.getText().toString();

                        product = new Product(item, false,false);

                        ds.open();
                            ds.insert(product);
                        ds.close();

                        //Refresh List
                        showList(MenuOptions.MASTER_LIST.toString(), "Master List");

                        Toast.makeText(getApplication(), "Item Added", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d(TAG, "onClick: Cancel");
                    }
                }).show();

*/
    }

    private void showList(String option, String title){

        setTitle(title);

        /*
        ds.open();
        if (MenuOptions.MASTER_LIST.toString().equals(option))
            products = ds.getAll(0);
        else
            products = ds.getAll(1);
        ds.close();
        */

        RecyclerView recyclerView = findViewById(R.id.rvMasterList);

        ProductAdapter adapter = new ProductAdapter(products, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(adapter);
    }

    public void rebindScreen(){

        RecyclerView recyclerView = findViewById(R.id.rvMasterList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter(products, this);
        productAdapter.setOnItemClickListener(onClickListener);
        productAdapter.setOnItemCheckedChangedListener(onCheckedChangeListener);
        recyclerView.setAdapter(productAdapter);

        productAdapter.notifyDataSetChanged();


    }

    private void clearAllCheckboxes() {

        ArrayList<Product> prods = new ArrayList<Product>();

        try{
            for (Product product : products) {
                prods.add(new Product(product.getId(), product.getProductDescription(), product.getIsOnShoppingList(), false, product.getOwner(), product.getLongitue(), product.getLatitude()));
            }

            RestClient.updateBulkRequest(prods, getAPI(), this, new VolleyCallback() {
                @Override
                public void onSuccess(ArrayList<Product> result) {

                    products = result;

                    readFromAPI(MenuOptions.MASTER_LIST.toString(), "Master List");
                }
            });

            //rebindScreen();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        /*
        try {
                ds.open();
                products = ds.getAll(1);
                ds.updateBulk(products);
                ds.close();

        }catch (Exception ex){
            Log.d(TAG, "clearAllCheckboxes: "+ex.getMessage());
        }

        ProductAdapter adapter = new ProductAdapter(products, this);*/
    }

    private void deleteChecked() {

        ArrayList<Product> prods = new ArrayList<Product>();

        try{
            for (Product product : products) {

                if (!product.getIsInCart())
                    continue;

                prods.add(new Product(product.getId(), product.getProductDescription(), product.getIsOnShoppingList(), false, product.getOwner(), product.getLongitue(), product.getLatitude()));
            }

            if (getTitle().toString().equals("Master List")){

                Log.d(TAG, "deleteChecked Master List : "+getTitle());

                RestClient.deleteBulkRequest(prods, getAPI(), this, new VolleyCallback() {
                    @Override
                    public void onSuccess(ArrayList<Product> result) {

                        products = result;

                        for ( Product prd: products) {
                            Log.d(TAG, "onSuccess:-> "+ product.toString());
                        }

                        readFromAPI(MenuOptions.MASTER_LIST.toString(), "Master List");
                    }
                });
            } else {

                Log.d(TAG, "deleteChecked Shopping List : "+getTitle());
                RestClient.updateBulkRequest(prods, getAPI(), this, new VolleyCallback() {
                    @Override
                    public void onSuccess(ArrayList<Product> result) {

                        products = result;

                        for ( Product prd: products) {
                            Log.d(TAG, "onSuccess update :-> "+ product.toString());
                        }



                        readFromAPI(MenuOptions.MASTER_LIST.toString(), "Master List");


                    }
                });
            }

            rebindScreen();

            //rebindScreen();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        /*
        ds.open();
        products = ds.getAll(1);
        ds.close();
                if (!getTitle().toString().equals("Master List")){
                   ds.open();
                    ds.updateBulk(products);
                    ds.close();
                }else{

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                    builder.setMessage("Do you really want to delete the selected items?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ds.open();
                            int rows = ds.deleteBulk(products);
                            ds.close();

                            showList(MenuOptions.MASTER_LIST.toString(), "Master List");

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

        ProductAdapter adapter = new ProductAdapter(products, this);
        */


    }
    private void initDatabase(){
        /*
        ds.open();
        products = ds.getAll(0);

        if (products.size() == 0){
            ds.refreshData();
            products = ds.getAll(0);
        }
        ds.close();*/
    }

    //Read from API
    private void readFromAPI(String option, String title){

        setTitle(title);

        try{
            RestClient.getRequest(getAPI(),this, new VolleyCallback() {
                @Override
                public void onSuccess(ArrayList<Product> result) {
                    products = result;
                    rebindScreen();
                }
            }, option);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public String getAPI(){
        return this.API;
    }
}