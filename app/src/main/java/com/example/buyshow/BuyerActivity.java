package com.example.buyshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buyshow.Model.products;
import com.example.buyshow.ViewHolder.ProductHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class BuyerActivity extends AppCompatActivity {
    private Button LogoutBtn;
    private DatabaseReference productsRef;
    private RecyclerView recyclerView;
//    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);
        productsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        recyclerView = (RecyclerView)findViewById(R.id.buyer_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



//        recyclerView.setHasFixedSize(true);
//        layoutManager = new LinearLayoutManager(this);



    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<products> options = new FirebaseRecyclerOptions.Builder<products>().setQuery(productsRef,products.class).build();


        FirebaseRecyclerAdapter<products, ProductHolder> adapter = new FirebaseRecyclerAdapter<products, ProductHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductHolder holder, int position, @NonNull products model) {
            holder.txtProductName.setText(model.getName());
            holder.txtProductPrice.setText(model.getPrice());
            holder.txtProductDescription.setText(model.getDescription());
            Picasso.get().load(model.getImage()).into(holder.imageView);
            }

            @NonNull
            @Override
            public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.products_item_layout,parent,false);
                ProductHolder holder =new ProductHolder(view);
                return holder;

            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }

//    public static class  productHolder extends  RecyclerView.ViewHolder{
//        public TextView txtProductDescription,txtProductName,txtProductPrice;
//        public ImageView imageView;
//
//        public productHolder(@NonNull View itemView) {
//            super(itemView);
//            imageView = (ImageView) itemView.findViewById(R.id.product_image_new);
//            txtProductDescription = (TextView) itemView.findViewById(R.id.product_description_new);
//            txtProductName = (TextView) itemView.findViewById(R.id.product_name_new);
//            txtProductPrice = (TextView) itemView.findViewById(R.id.product_price_new);
//
//        }
//    }




}