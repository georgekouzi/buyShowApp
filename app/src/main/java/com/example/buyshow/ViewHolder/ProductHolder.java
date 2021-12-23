package com.example.buyshow.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyshow.Interface.ItemClickListner;
import com.example.buyshow.R;

public class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
public TextView txtProductDescription,txtProductName,txtProductPrice,txtRank;
public ImageView imageView;
public  ItemClickListner listner;
public RatingBar ratingBar;

    public ProductHolder(@NonNull View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.product_image_new);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description_new);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name_new);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price_new);
        txtRank = (TextView) itemView.findViewById(R.id.rank);
        ratingBar = (RatingBar) itemView.findViewById(R.id.rating);


    }

    public void setItemClickListner(ItemClickListner listner ){
        this.listner=listner;

    }

    @Override
    public void onClick(View view) {
        listner.onClick(view,getAdapterPosition(),false);

    }
}
