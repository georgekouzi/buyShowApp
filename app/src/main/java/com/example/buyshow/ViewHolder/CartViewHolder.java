package com.example.buyshow.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyshow.Interface.ItemClickListner;
import com.example.buyshow.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txtProductQuantity,txtProductName,txtProductPrice;
    public ItemClickListner listner;

    public CartViewHolder(View itemView) {
        super(itemView);
        txtProductQuantity = itemView.findViewById(R.id.cart_product_quantity  );
        txtProductName = itemView.findViewById(R.id.cart_product_name);
        txtProductPrice =  itemView.findViewById(R.id.cart_product_price);


    }

    public void setItemClickListner(ItemClickListner listner ){
        this.listner=listner;

    }

    @Override
    public void onClick(View view) {
        listner.onClick(view,getAdapterPosition(),false);

    }
}


