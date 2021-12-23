package com.example.buyshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.buyshow.Model.SellerOrders;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.rey.material.widget.TextView;

public class SellerNewOrderActivity extends AppCompatActivity {
    private RecyclerView orderList;
    private DatabaseReference ordesRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_new_order);

        ordesRef= FirebaseDatabase.getInstance().getReference().child("Orders");
        orderList =findViewById(R.id.order_list);
        orderList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<SellerOrders> options= new FirebaseRecyclerOptions.Builder<SellerOrders>().setQuery(ordesRef,SellerOrders.class).build();
//        FirebaseRecyclerAdapter<SellerOrders , SellerOrdersViewHolder>adpter=new FirebaseRecyclerAdapter<SellerOrders, SellerOrdersViewHolder>(options) {
//                    @Override
//                    protected void onBindViewHolder(@NonNull SellerOrdersViewHolder holder,int position, @NonNull SellerOrders model) {
//                        holder.buyerName.setText("Name: "+ model.getName());
//                        holder.buyerPhoneNumber.setText("Phone: "+ model.getPhone());
//                        holder.buyerTotalPrice.setText("Total Amount: "+ model.getTotalAmount());
//                        holder.buyerDateTime.setText("Order at: "+ model.getDate()+"  "+model.getTime());
//                        holder.buyerShippingAddress.setText("Shipping Address: "+ model.getAddress()+" "+model.getCity());
//
//                        holder.ShowOrderBtn.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view)
//                            {
//                                String uID=getRef(position).getKey();
//
//                                Intent intent =new Intent(SellerNewOrderActivity.this,SellerBuyerProductActivity.class);
//                                intent.putExtra("uid",uID);
//                                startActivity(intent);
//                            }
//                        });
//
//
//                        holder.itemView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view)
//                            {
//                                CharSequence options []= new CharSequence[]
//                                        {
//                                                "Yes",
//                                                "NO"
//                                        };
//                                AlertDialog.Builder builder = new AlertDialog.Builder(SellerNewOrderActivity.this);
//                                builder.setTitle("Have you shipped this order products ?");
//
//                                builder.setItems(options, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i)
//                                    {
//                                        if(i==0)
//                                        {
//                                            String uID= getRef(position).getKey();
//
//                                            RemoverOrder(uID);
//                                        }
//                                        else
//                                         {
//                                            finish();
//                                         }
//                                    }
//                                });
//                                builder.show();
//                            }
//                        });
//                    }
//
//                    @NonNull
//                    @Override
//                    public SellerOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent , false);
//                            return new SellerOrdersViewHolder(view);
//                    }
//                };


//        orderList.setAdapter(adpter);
//        adpter.startListening();
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
    private void RemoverOrder(String uID)
    {

        ordesRef.child(uID).removeValue();
    }
}