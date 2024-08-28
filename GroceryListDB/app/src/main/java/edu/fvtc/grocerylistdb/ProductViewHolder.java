package edu.fvtc.grocerylistdb;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    private View.OnClickListener onClickListener;
    public TextView tvProductDesc;
    public CheckBox chkOnList;
    public ProductViewHolder(@NonNull View itemView){
        super(itemView);
        this.tvProductDesc = itemView.findViewById(R.id.tvProdDesc);
        this.chkOnList = itemView.findViewById(R.id.chkOnList);
        itemView.setTag(this);
        itemView.setOnClickListener(onClickListener);
    }

    public TextView getTvProductDesc() {
        return tvProductDesc;
    }

    public CheckBox getChkOnList() {

        return chkOnList;
    }
}
