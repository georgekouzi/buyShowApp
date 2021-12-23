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
    public ImageView imageView;
    public ItemClickListner listner;

    public CartViewHolder(View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.product_image_new);
        txtProductQuantity = itemView.findViewById(R.id.product_name  );
        txtProductName = itemView.findViewById(R.id.product_name);
        txtProductPrice =  itemView.findViewById(R.id.product_price_new);


    }

    public void setItemClickListner(ItemClickListner listner ){
        this.listner=listner;

    }

    @Override
    public void onClick(View view) {
        listner.onClick(view,getAdapterPosition(),false);

    }
}


