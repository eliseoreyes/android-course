package edu.fvtc.grocerylistdb;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.fvtc.grocerylistdb.models.Product;


public class ProductAdapter extends RecyclerView.Adapter {
    public static final String TAG = "ProductAdapter";
    boolean isDeleting;
    private ArrayList<Product> productList;

    private View.OnClickListener onItemClickListener;
    private CompoundButton.OnCheckedChangeListener onItemCheckedChangedListener;

    private Context parentContext;

    public void setDelete(boolean b) {
        isDeleting = b;
    }

    public ProductAdapter(ArrayList<Product> data, Context context){
        productList = data;
        parentContext = context;
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private TextView tvProductDesc;
        private CheckBox chkOnList;
        private ImageButton imageButtonPhoto;
        private Button btnDelete;

        private View.OnClickListener onClickListener;
        private CompoundButton.OnCheckedChangeListener onCheckedChangeListener;

        public ProductViewHolder(@NonNull View itemView){
            super(itemView);
            this.tvProductDesc = itemView.findViewById(R.id.tvProdDesc);
            this.chkOnList = itemView.findViewById(R.id.chkOnList);
            this.imageButtonPhoto = itemView.findViewById(R.id.imgPhoto);
            itemView.setTag(this);

            itemView.setOnClickListener(onItemClickListener);

            chkOnList.setTag(this);
            chkOnList.setOnCheckedChangeListener(onCheckedChangeListener);
        }

        public TextView getTvProductDesc() {
            return tvProductDesc;
        }

        public void setTvProductDesc(TextView tvProductDesc) {
            this.tvProductDesc = tvProductDesc;
        }

        public CheckBox getChkOnList() {
            return chkOnList;
        }

        public void setChkOnList(CheckBox chkOnList) {
            this.chkOnList = chkOnList;
        }

        public ImageButton getImageButtonPhoto() {
            return imageButtonPhoto;
        }

    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener)
    {
        onItemClickListener = itemClickListener;
    }

    public void setOnItemCheckedChangedListener(CompoundButton.OnCheckedChangeListener listener)
    {
        Log.d(TAG, "setOnItemCheckedChangedListener: ");
        onItemCheckedChangedListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_view, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Product product = productList.get(position);

        ProductViewHolder productViewHolder = (ProductViewHolder) holder;
        productViewHolder.getTvProductDesc().setText(product.getProductDescription());
        productViewHolder.getChkOnList().setChecked(product.getIsInCart());
        Bitmap productPhoto = product.getPhoto();


        if(productPhoto != null)
        {
            productViewHolder.getImageButtonPhoto().setImageBitmap(productPhoto);
        }
        else {
            int resourceId = getDrawableResourceId(parentContext, productViewHolder.getTvProductDesc().getText().toString().toLowerCase());

            if (resourceId != 0) {
                productViewHolder.getImageButtonPhoto().setImageResource(resourceId);
            }else {
                productViewHolder.getImageButtonPhoto().setImageResource(R.drawable.groceries_svgrepo_com);
            }
        }

        productViewHolder.getChkOnList().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckedChanged: " + isChecked);
                onItemCheckedChangedListener.onCheckedChanged(buttonView, isChecked);
            }
        });
    }

    private int getDrawableResourceId(Context context, String drawable){
        Resources resources = context.getResources();
        return resources.getIdentifier(drawable, "drawable", context.getPackageName());
    }
    @Override
    public int getItemCount() {

        return productList.size();
    }
}
