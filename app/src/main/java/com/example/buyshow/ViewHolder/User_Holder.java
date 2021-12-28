package com.example.buyshow.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buyshow.Interface.ItemClickListner;
import com.example.buyshow.R;

public class User_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtUserRank,txtUserName,txtUserPhone,txtUserType;
    public  ItemClickListner listner;
    public RatingBar ratingBar;
    public Button buttonDelete;

    public User_Holder(@NonNull View itemView) {
        super(itemView);
        txtUserName = (TextView) itemView.findViewById(R.id.user_name_new);
        txtUserPhone = (TextView) itemView.findViewById(R.id.user_phone_new);
        txtUserType = (TextView) itemView.findViewById(R.id.user_type_new);
        txtUserRank = (TextView) itemView.findViewById(R.id.user_rank_u);
        ratingBar = (RatingBar) itemView.findViewById(R.id.user_rating_u);
        buttonDelete = (Button) itemView.findViewById(R.id.delete_user);
    }

    public void setItemClickListner(ItemClickListner listner ){

        this.listner=listner;

    }

    @Override
    public void onClick(View view) {

        listner.onClick(view,getAdapterPosition(),false);

    }
}
