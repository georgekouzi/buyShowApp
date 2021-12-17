package com.example.buyshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.buyshow.Model.User;
import com.example.buyshow.Model.products;
import com.example.buyshow.ViewHolder.ProductHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SellerProductActivity extends AppCompatActivity {
    private Button LogoutBtn;
    private DatabaseReference productsRef;
    private RecyclerView recyclerView;
    private String phone_id;
    //    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_product);
        productsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        recyclerView = (RecyclerView) findViewById(R.id.seller_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        phone_id = getIntent().getExtras().get("phone_id").toString();

    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<products> options = new FirebaseRecyclerOptions.Builder<products>().setQuery(productsRef,products.class).build();
        FirebaseRecyclerAdapter<products, ProductHolder> adapter = new FirebaseRecyclerAdapter<products, ProductHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductHolder holder, int position, @NonNull products model) {
                if(model.getPhone_id().equals(phone_id)) {
//                    Toast.makeText(SellerProductActivity.this,"innnnnnnnnnnnnnnnn",Toast.LENGTH_SHORT).show();

                    holder.txtProductName.setText(model.getName());
                    holder.txtProductPrice.setText(model.getPrice());
                    holder.txtProductDescription.setText(model.getDescription());
                    Picasso.get().load(model.getImage()).into(holder.imageView);
                }
                else{
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                    }

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



}