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
import android.widget.TextView;

import com.example.buyshow.Model.BuyerOrders;
import com.example.buyshow.Model.Cart;
import com.example.buyshow.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sellersNewOrderActivity extends AppCompatActivity {
    private RecyclerView orderList;
    private DatabaseReference ordesRef;
    private String phone_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellers_new_order);
        phone_id=getIntent().getExtras().get("phone_id").toString();
        ordesRef= FirebaseDatabase.getInstance().getReference().child("Orders");
        orderList =findViewById(R.id.order_list);
        orderList.setLayoutManager(new LinearLayoutManager(this));
    }
//    order_list
    @Override

    protected void onStart() {
        super.onStart();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Sellers order");
        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartListRef,Cart.class).build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
                if(model.getSellerID().equals(phone_id)){

                    holder.txtProductQuantity.setText("Quantity = "+model.getQuantity());
                    holder.txtProductPrice.setText("Price "+model.getPrice() + "$");
                    holder.txtProductName.setText(model.getPname());
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(sellersNewOrderActivity.this,sendProductActivity.class);
                            intent.putExtra("phone_id",model.getSellerID());
                            intent.putExtra("Orderpid",model.getBuyerIDOrders());
                            intent.putExtra("quantity",model.getQuantity());
                            intent.putExtra("Price",model.getPrice());
                            intent.putExtra("pidp",model.getPidOrder());
                            intent.putExtra("pname",model.getPname());


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
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };
        orderList.setAdapter(adapter);
        adapter.startListening();






//        FirebaseRecyclerOptions<BuyerOrders> options= new FirebaseRecyclerOptions.Builder<BuyerOrders>().setQuery(ordesRef, BuyerOrders.class).build();
//        FirebaseRecyclerAdapter<BuyerOrders, SellerOrdersViewHolder> adpter= new FirebaseRecyclerAdapter<BuyerOrders, SellerOrdersViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull SellerOrdersViewHolder holder, int position, @NonNull BuyerOrders model) {
//                        if()
//                        holder.buyerName.setText("Name: "+ model.getName());
//                        holder.buyerPhoneNumber.setText("Phone: "+ model.getPhone());
//                        holder.buyerTotalPrice.setText("Total Amount: "+ model.getTotalAmount());
//                        holder.buyerDateTime.setText("Order at: "+ model.getDate()+"  "+model.getTime());
//                        holder.buyerShippingAddress.setText("Shipping Address: "+ model.getAddress()+" "+model.getCity());
//
//
//
//            }
//
//            @NonNull
//            @Override
//            public SellerOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                return null;
//            }
//        };



    }

    public static class SellerOrdersViewHolder extends RecyclerView.ViewHolder{
        public TextView buyerName,buyerPhoneNumber,buyerTotalPrice,buyerDateTime,buyerShippingAddress;
        public Button ShowOrderBtn;

        public SellerOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            buyerName=itemView.findViewById(R.id.buyer_name);
            buyerPhoneNumber=itemView.findViewById(R.id.phone_number);
            buyerTotalPrice=itemView.findViewById(R.id.total_price);
            buyerDateTime=itemView.findViewById(R.id.date_time);
            buyerShippingAddress=itemView.findViewById(R.id.order_address);
            ShowOrderBtn=itemView.findViewById(R.id.show_all_products_btn);


        }
    }


}