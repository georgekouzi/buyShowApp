package com.example.buyshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.buyshow.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SellerBuyerProductActivity extends AppCompatActivity {

    private RecyclerView productsList;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference cartListRef;

    private String BuyerID=" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_buyer_product);


        BuyerID = getIntent().getStringExtra("uid");
        productsList= findViewById(R.id.products_list);
        productsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        productsList.setLayoutManager(layoutManager);

        cartListRef = FirebaseDatabase.getInstance().getReference()
                .child("Cart List").child("Seller View").child(BuyerID).child("Products");
    }
//        @Override
//        protected void onStart()
//        {
//            super.onStart();
//
//
//            FirebaseRecyclerOptions<Cart>options=
//                    new FirebaseRecyclerOptions.Builder<Cart>()
//                    .setQuery(cartListRef,Cart.class)
//                    .build();
//
//
//            FirebaseRecyclerOptions<Cart, CartViewHolder>adapter=new FirebaseRecyclerAdapter<Cart,CartViewHolder>(options){
//                @Override
//                protected void  onBindViewHolder(@NonNull CartViewHolderv holder, int postioins ,@NonNull Cart model){
//                    holder.txtproductQuantity.setText("Quantity = "+ cart.getQuantity());
//                    holder.txtproductprice.setText("Price "+ cart.getPrice_INR() +"₹");
//                    holder.txtproductname.setText(cart.getProduct_name());
//
//                }
//                @NonNull
//                @Override
//                public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType){
//                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent , false);
//                    CartViewHolder holder = new CartViewHolder(view);
//                    return  holder;
//                }
//
//            };
//            productsList.setAdapter(adapter);
//            adapter.startListening();


//        }






}