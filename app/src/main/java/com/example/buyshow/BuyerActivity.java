package com.example.buyshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

import io.paperdb.Paper;

/*
This is a class of the screen that the buyer moves to, where all the items that the sellers have uploaded are located. */
public class BuyerActivity extends AppCompatActivity {
    private DatabaseReference productsRef;
    private RecyclerView recyclerView;
    private Button buttonLogout ;
    private CardView counter;
    private String buyerPhone;
    private TextView textViewCounter;
    private ImageView message,cartButton;
    private int MessageN;
//In this function we correct the variables and request a reference for all products from the database.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer);
        buyerPhone =  getIntent().getExtras().get("phoneBuyer").toString();
        productsRef = FirebaseDatabase.getInstance().getReference();
        //RecycleView object - allows you to arrange list details for display
        recyclerView = (RecyclerView)findViewById(R.id.buyer_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        counter =(CardView) findViewById(R.id.counter_massage);
        textViewCounter = (TextView) findViewById(R.id.textViewCounter);
        message = (ImageView) findViewById(R.id.message_b);
        cartButton = (ImageView) findViewById(R.id.cart);

        productsRef.child("Buyer").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Toast.makeText(BuyerActivity.this,"innnnn.",Toast.LENGTH_SHORT).show();

                User user=snapshot.child(buyerPhone).getValue(User.class);
                MessageN = user.getMessageN();
                if(MessageN>0){
                    counter.setVisibility(View.VISIBLE);
                    textViewCounter.setText(String.valueOf(MessageN));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MessageN!=0) {
                    productsRef.child("Buyer").child(buyerPhone).child("messageN").setValue(0);
                    counter.setVisibility(View.INVISIBLE);
                }

                Intent intent=new Intent(BuyerActivity.this, MessageActivity.class);
                intent.putExtra("usertype","Buyer");
                intent.putExtra("phoneBuyer",buyerPhone);

                startActivity(intent);
            }





        });



        buttonLogout = (Button) findViewById(R.id.log_out_b);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                Intent intent = new Intent(BuyerActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BuyerActivity.this,CartActivity.class);
                intent.putExtra("phoneBuyer",buyerPhone);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<products> options = new FirebaseRecyclerOptions.Builder<products>().setQuery(productsRef.child("Products"),products.class).build();
        FirebaseRecyclerAdapter<products, ProductHolder> adapter = new FirebaseRecyclerAdapter<products, ProductHolder>(options) {
            @Override
        //We wish for the variables and override the initial text that was before.
            protected void onBindViewHolder(@NonNull ProductHolder holder, int position, @NonNull products model) {
            holder.txtProductName.setText(model.getName());
            holder.txtProductPrice.setText(model.getPrice());
            holder.txtProductDescription.setText(model.getDescription());
            productsRef.child("Seller").child(model.getPhone_id()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                User seller =  snapshot.getValue(User.class);
                    DecimalFormat df = new DecimalFormat("###.#");
                    double  rating =Double.parseDouble( df.format((double)(seller.getRank()) / (double)(seller.getSellCounter())));
                    if(seller.getSellCounter()!=0) {
                        holder.txtRank.setText("seller rating: "+String.valueOf(rating));
                        holder.ratingBar.setRating((float) rating);
                        holder.ratingBar.setIsIndicator(true);
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            Picasso.get().load(model.getImage()).into(holder.imageView);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(BuyerActivity.this,ProductDetailsActivity.class);
                    intent.putExtra("pid",model.getPid());
                    intent.putExtra("phone_id",model.getPhone_id());
                    intent.putExtra("phoneBuyer",buyerPhone);

                    startActivity(intent);
                }
            });
            }

            @NonNull
            @Override
            //This function gets the view:
            //And it sends it to a function
            //onBindViewHolder to update the
            //The details.
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