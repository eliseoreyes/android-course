package edu.fvtc.grocerylist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.fvtc.grocerylist.models.Product;
import edu.fvtc.grocerylist.models.WriteMode;
import edu.fvtc.grocerylist.utils.FileIO;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    public static final String TAG = "ProductAdapter";
    private ArrayList<Product> productList;

    private String [] products;
    private Context context;

    public ProductAdapter(ArrayList<Product> productList, Context context){
        this.productList = productList;
        this.context = context;
    }
    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {

        String name = productList.get(position).getProductDescription();
        boolean isOnList = (productList.get(position).getIsInCart() == 1)?true:false;
        holder.tvProductDesc.setText(name);
        holder.chkOnList.setChecked(isOnList);


        holder.chkOnList.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                String [] products;

                if (isChecked){
                    productList.set(position, new Product(productList.get(position).getId(),productList.get(position).getProductDescription(),0, 1));
                }else {
                    productList.set(position, new Product(productList.get(position).getId(),productList.get(position).getProductDescription(),0, 0));
                }

                products = reverseArray(convertToArray(productList));
                FileIO.writeFile(MainActivity.FILENAME, WriteMode.MODE_PRIVATE, (MainActivity) context, products);
            }
        });
    }

    public String[] convertToArray(ArrayList<Product> products){

        String[] items = new String [products.size()];

        int size = products.size();
        for (Product product : products) {
            items[--size] = product.toString();
        }
        return items;
    }

    public String[] reverseArray(String[] items){

        String[] item = new String[items.length];
        int index = 0;

        for (int i = items.length-1; i >= 0; i--) {
            item[index] = items[i];
            index++;
        }
        return item;
    }
    @Override
    public int getItemCount() {

        return productList.size();
    }

    public String[] getProducts() {

        return products;
    }

    public void setProducts(String[] products) {

        this.products = products;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView tvProductDesc;
        public CheckBox chkOnList;
        public ProductViewHolder(@NonNull View itemView){
            super(itemView);
            this.tvProductDesc = itemView.findViewById(R.id.tvProdDesc);
            this.chkOnList = itemView.findViewById(R.id.chkOnList);
        }
    }
}
