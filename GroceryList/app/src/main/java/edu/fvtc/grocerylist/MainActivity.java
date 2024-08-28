package edu.fvtc.grocerylist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import edu.fvtc.grocerylist.db.GroceryListDataSource;
import edu.fvtc.grocerylist.models.MenuOptions;
import edu.fvtc.grocerylist.models.Product;
import edu.fvtc.grocerylist.models.WriteMode;
import edu.fvtc.grocerylist.utils.FileIO;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final String FILENAME = "products.txt";

     ArrayList<Product> productList;
     ArrayList<String> productData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Load from db
        initDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if (item.getItemId() == R.id.showMaster) {
            showList(MenuOptions.MASTER_LIST.toString(), "Master List");
            return true;
        }else if (item.getItemId() == R.id.showShopList) {
            showList(MenuOptions.SHOPPING_LIST.toString(), "Shopping List");
            return true;
        }else if (item.getItemId() == R.id.addItem) {
            addItemDialog();
            return true;
        }else if (item.getItemId() == R.id.clearAll) {
            clearAllCheckboxes();
            showList(MenuOptions.MASTER_LIST.toString(), "Master List");
            return true;
        }else if (item.getItemId() == R.id.delChecked) {
            deleteChecked();
            showList(MenuOptions.MASTER_LIST.toString(), "Master List");
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void addItemDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View addView = layoutInflater.inflate(R.layout.add_item, null);

        String[] products = new String[1];

        new AlertDialog.Builder(this)
                .setTitle("Add Item")
                .setView(addView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        TextInputEditText txtItem = addView.findViewById(R.id.txtAddItem);
                        String item = txtItem.getText().toString();
                        products[0] = String.format("%s|%s|%s",item.toString(), "0", "0");

                        FileIO.writeFile(FILENAME, WriteMode.MODE_APPEND, MainActivity.this, products);
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
    }

    private void showList(String option, String title){

        setTitle(title);

        productList = new ArrayList<Product>();
        productData = FileIO.readFile(FILENAME, MainActivity.this);

        RecyclerView recyclerView = findViewById(R.id.rvMasterList);

        int id = 0;

        for (String s : productData) {
            String[] data = s.split("\\|");

            id++;

            if (MenuOptions.SHOPPING_LIST.toString().equals(option) && Integer.parseInt(data[2]) == 0)
                continue;

            productList.add(new Product(id, data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2])));
        }

        ProductAdapter adapter = new ProductAdapter(productList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));

        //Drawable divider = ContextCompat.getDrawable(this, R.drawable.rec_view_div);

        recyclerView.setAdapter(adapter);
    }
    private void clearAllCheckboxes() {

        productList = new ArrayList<Product>();
        productData = FileIO.readFile(FILENAME, MainActivity.this);

        for (String s : productData) {
            String[] data = s.split("\\|");
            productList.add(new Product(data[0], 0, 0));
        }

        ProductAdapter adapter = new ProductAdapter(productList, this);

        String[] products = adapter.reverseArray(adapter.convertToArray(productList));

        FileIO.writeFile(FILENAME, WriteMode.MODE_PRIVATE, this, products );
    }

    private void deleteChecked() {

        productList = new ArrayList<Product>();
        productData = FileIO.readFile(FILENAME, MainActivity.this);
        int isInCar = 0;

        for (String s : productData) {
            String[] data = s.split("\\|");

            if (Integer.parseInt(data[2]) == 1){
                if (!getTitle().toString().equals("Shopping List")){
                   continue;
                }else{
                    isInCar = 0;
                }
            }

            productList.add(new Product(data[0], Integer.parseInt(data[1]), isInCar));
        }

        ProductAdapter adapter = new ProductAdapter(productList, this);

        //adapter.writeFile(FILENAME, productList);

        String[] products = adapter.reverseArray(adapter.convertToArray(productList));

        FileIO.writeFile(FILENAME, WriteMode.MODE_PRIVATE, this, products );
    }

    public void initDatabase(){
        Log.d(TAG, "initDatabase: ");
        GroceryListDataSource ds = new GroceryListDataSource(this);
        ds.open();

        productList = ds.get();

        if (productList.size() == 0) {
            ds.refreshData();
            productList = ds.get();
        }
        ds.close();
        Log.d(TAG, "initDatabase: "+productList.size());
    }
}