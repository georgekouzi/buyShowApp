package com.example.buyshow.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyshow.Interface.ItemClickListner;
import com.example.buyshow.R;

public class MessageHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView titleText;
    public ItemClickListner listner;

    public MessageHolder(@NonNull View itemView) {
        super(itemView);
        titleText = (TextView) itemView.findViewById(R.id.title_m);


    }
    public void setItemClickListner(ItemClickListner listner ){
        this.listner=listner;

    }

    @Override
    public void onClick(View view) {
        listner.onClick(view,getAdapterPosition(),false);

    }






}
