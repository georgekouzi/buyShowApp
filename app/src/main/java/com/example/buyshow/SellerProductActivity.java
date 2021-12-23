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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buyshow.Model.User;
import com.example.buyshow.Model.products;
import com.example.buyshow.ViewHolder.ProductHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class SellerProductActivity extends AppCompatActivity {
    private DatabaseReference productsRef;
    private RecyclerView recyclerView;
    private String phone_id;
    private Button backToOption;
    private RatingBar ratingBar;
    private TextView rank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_product);
        productsRef = FirebaseDatabase.getInstance().getReference();
        recyclerView = (RecyclerView) findViewById(R.id.seller_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        phone_id = getIntent().getExtras().get("phone_id").toString();
        ratingBar = (RatingBar) findViewById(R.id.ratingBar_s);
        rank = (TextView) findViewById(R.id.rating_s);



        backToOption = (Button)findViewById(R.id.back_s);
        backToOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerProductActivity.this,SellersActivity.class);
                startActivity(intent);
            }
        });

        productsRef.child("Seller").child(phone_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User seller =  snapshot.getValue(User.class);
                DecimalFormat df = new DecimalFormat("###.#");
                double  rating =Double.parseDouble( df.format((double)(seller.getRank()) / (double)(seller.getSellCounter())));
                if(seller.getSellCounter()!=0) {
                    rank.setText(String.valueOf(rating));
                    ratingBar.setRating((float) rating);
                    ratingBar.setIsIndicator(true);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<products> options = new FirebaseRecyclerOptions.Builder<products>().setQuery(productsRef.child("Products"),products.class).build();
        FirebaseRecyclerAdapter<products, ProductHolder> adapter = new FirebaseRecyclerAdapter<products, ProductHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductHolder holder, int position, @NonNull products model) {
                if(model.getPhone_id().equals(phone_id)) {

                    holder.txtProductName.setText(model.getName());
                    holder.txtRank.setVisibility(View.INVISIBLE);
                    holder.ratingBar.setVisibility(View.INVISIBLE);
                    holder.txtProductPrice.setText(model.getPrice());
                    holder.txtProductDescription.setText(model.getDescription());
                    Picasso.get().load(model.getImage()).into(holder.imageView);

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(SellerProductActivity.this,SellerProductDetailsActivity.class);
                            intent.putExtra("pid",model.getPid());
                            intent.putExtra("phone_id",model.getPhone_id());
                            intent.putExtra("Image_uri",model.getImage());

                            startActivity(intent);
                        }
                    });

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