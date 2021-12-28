package com.example.buyshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.buyshow.Model.Cart;
import com.example.buyshow.Model.message;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class OrderActivity extends AppCompatActivity {
private EditText name, phone,city,country,address;
private Button confirmation;
private ProgressDialog loadingBar;
private String totalPrice,phoneBuyer;
    private DatabaseReference OrderListRF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        name=(EditText)findViewById(R.id.name_Order);
        phone=(EditText)findViewById(R.id.phone_Order);
        city=(EditText)findViewById(R.id.city_Order);
        country=(EditText)findViewById(R.id.Country_Order);
        address=(EditText)findViewById(R.id.address_Order);
        totalPrice= getIntent().getExtras().get("totalPrice").toString();
        phoneBuyer= getIntent().getExtras().get("phoneBuyer").toString();


        loadingBar = new ProgressDialog(this);


        confirmation = (Button) findViewById(R.id.Confirmation_button);
        confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String order_name =name.getText().toString();
                String order_phone =phone.getText().toString();
                String order_city =city.getText().toString();
                String order_country =country.getText().toString();
                String order_address =address.getText().toString();


                if (TextUtils.isEmpty(order_name)){
                    Toast.makeText(OrderActivity.this,"Please write your name",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(order_phone)){
                    Toast.makeText(OrderActivity.this,"Please write your phone",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(order_city)){
                    Toast.makeText(OrderActivity.this,"Please write your city",Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(order_country)){
                    Toast.makeText(OrderActivity.this,"Please write your country",Toast.LENGTH_SHORT).show();

                }
                else if (TextUtils.isEmpty(order_address)){
                    Toast.makeText(OrderActivity.this,"Please write your address",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    loadingBar.setTitle("Order sending ");
                    loadingBar.setMessage("Pleas wait, while we are checking your order.");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    sendOrderToDB();
                }


            }
        });

    }

    private void sendOrderToDB() {
        String saveCurrentDate,saveCurrentTime;
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentData =new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentData.format(calendar.getTime());

        SimpleDateFormat currentTime =new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        String pidOrder=phoneBuyer+" "+saveCurrentDate.toString()+saveCurrentTime.toString();

        OrderListRF= FirebaseDatabase.getInstance().getReference().child("Orders").child(pidOrder);
        HashMap<String,Object> OrdersMap = new HashMap<>();
        OrdersMap.put("BuyerID",phoneBuyer);
        OrdersMap.put("pidOrder",pidOrder);
        OrdersMap.put("totalPrice",totalPrice);
        OrdersMap.put("date",saveCurrentDate);
        OrdersMap.put("time",saveCurrentTime);
        OrdersMap.put("state","not send");
        OrdersMap.put("name",name.getText().toString());
        OrdersMap.put("phone",phone.getText().toString());
        OrdersMap.put("city",city.getText().toString());
        OrdersMap.put("country",country.getText().toString());
        OrdersMap.put("address",address.getText().toString());



                OrderListRF.updateChildren(OrdersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                   if(task.isSuccessful()){

                       DatabaseReference RootRef;
                       RootRef = FirebaseDatabase.getInstance().getReference();
                       RootRef.child("Sellers order").addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                               for(DataSnapshot ds : snapshot.getChildren()){
                                 if(ds.child("BuyerID").getValue().equals(phoneBuyer)&&ds.child("buyerIDOrders").getValue().equals("-1")){
                                     ds.child("buyerIDOrders").getRef().setValue(pidOrder);
                                 }


                               }
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {

                           }
                       });

                       RootRef= FirebaseDatabase.getInstance().getReference();
                       RootRef.child("Cart List").child(phoneBuyer).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){


                                    HashMap<String, Object> OrdersMessageMap = new HashMap<>();
                                    OrdersMessageMap.put("type", 1);
                                    OrdersMessageMap.put("phone_id", phoneBuyer);
                                    OrdersMessageMap.put("title", "Your order has been sent to sellers");
                                    OrdersMessageMap.put("newMessage", "Dear customer, thanks for your purchase,Soon your order will be confirmed by the sellers,And will send to you soon...");
                                    OrdersMessageMap.put("pid", pidOrder);
                                    OrdersMessageMap.put("from", "automaton");

                                    OrderListRF = FirebaseDatabase.getInstance().getReference().child("Message").child("Buyer");
                                    OrderListRF.child(pidOrder).updateChildren(OrdersMessageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){


                                                Toast.makeText(OrderActivity.this, "Order send to the sellers", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(OrderActivity.this, BuyerActivity.class);
                                                intent.putExtra("phoneBuyer", phoneBuyer);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();




                                            }



                                        }
                                    });






                                }
                            }
                        });


//                       @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        FirebaseDatabase.getInstance().getReference().child("Cart List").child(phoneBuyer).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()) {
//////
//                                    HashMap<String, Object> OrdersMessageMap = new HashMap<>();
//                                    OrdersMessageMap.put("type", 1);
//                                    OrdersMessageMap.put("phone_id", phoneBuyer);
//                                    OrdersMessageMap.put("title", "Your order has been sent to sellers");
//                                    OrdersMessageMap.put("newMessage", "Dear customer, thanks for your purchase,Soon your order will be confirmed by the sellers,And will send to you soon...");
//                                    OrdersMessageMap.put("pid", pidOrder);
//                                    OrdersMessageMap.put("from", "automaton");
//
//                                    OrderListRF = FirebaseDatabase.getInstance().getReference().child("Message").child("Buyer");
//
//
//                                    OrderListRF.child(pidOrder).updateChildren(OrdersMessageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if(task.isSuccessful()) {
//
//                                                Toast.makeText(OrderActivity.this, "Order send to the sellers", Toast.LENGTH_SHORT).show();
//                                                Intent intent = new Intent(OrderActivity.this, BuyerActivity.class);
//                                                intent.putExtra("phoneBuyer", phoneBuyer);
//                                                startActivity(intent);
//
//                                            }
//
//
//                                        }
//                                    });
////
//
//
//
//
//                                }
//                            }
//                        });
//
//
//                    }
//                });








                   }






                    }
                });
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        FirebaseDatabase.getInstance().getReference().child("Cart List").child(phoneBuyer).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()) {
//////
//                                    HashMap<String, Object> OrdersMessageMap = new HashMap<>();
//                                    OrdersMessageMap.put("type", 1);
//                                    OrdersMessageMap.put("phone_id", phoneBuyer);
//                                    OrdersMessageMap.put("title", "Your order has been sent to sellers");
//                                    OrdersMessageMap.put("newMessage", "Dear customer, thanks for your purchase,Soon your order will be confirmed by the sellers,And will send to you soon...");
//                                    OrdersMessageMap.put("pid", pidOrder);
//                                    OrdersMessageMap.put("from", "automaton");
//
//                                    OrderListRF = FirebaseDatabase.getInstance().getReference().child("Message").child("Buyer");
//
//
//                                    OrderListRF.child(pidOrder).updateChildren(OrdersMessageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
//                                            if(task.isSuccessful()) {
//
//                                                Toast.makeText(OrderActivity.this, "Order send to the sellers", Toast.LENGTH_SHORT).show();
//                                                Intent intent = new Intent(OrderActivity.this, BuyerActivity.class);
//                                                intent.putExtra("phoneBuyer", phoneBuyer);
//                                                startActivity(intent);
//
//                                            }
//
//
//                                        }
//                                    });
////
//
//
//
//
//                                }
//                            }
//                        });
//
//
//                    }
//                });
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//
//                        if(task.isSuccessful()) {
//                            DatabaseReference cartListSellerRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
//                            cartListSellerRef.child(phoneBuyer).child("Products").addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    if(snapshot.exists()) {
//                                        Cart cart = snapshot.getValue(Cart.class);
//                                        HashMap<String, Object> OrdersSellerMap = new HashMap<>();
//                                        OrdersSellerMap.put("pid", cart.getPid());
//                                        OrdersSellerMap.put("SellerID", cart.getSellerID());
//                                        OrdersSellerMap.put("BuyerID", cart.getBuyerID());
//                                        OrdersSellerMap.put("pidOrder", cart.getPidOrder());
//                                        OrdersSellerMap.put("pname", cart.getPname());
//                                        OrdersSellerMap.put("price", cart.getPrice());
//                                        OrdersSellerMap.put("date", saveCurrentDate);
//                                        OrdersSellerMap.put("time", saveCurrentTime);
//                                        OrdersSellerMap.put("quantity", cart.getQuantity());
//                                        OrdersSellerMap.put("discount", pidOrder);
//                                        DatabaseReference SellerCartListRF = FirebaseDatabase.getInstance().getReference().child("Sellers order");
//
////
//                                        SellerCartListRF.child(cart.getPidOrder()).updateChildren(OrdersSellerMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<Void> task) {
//                                                if(task.isSuccessful()){
//
//                                                    FirebaseDatabase.getInstance().getReference().child("Cart List").child(phoneBuyer).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                        @Override
//                                                        public void onComplete(@NonNull Task<Void> task) {
//
//
//                                                            if(task.isSuccessful()) {
////
//                                            HashMap<String,Object> OrdersMessageMap = new HashMap<>();
//                                            OrdersMessageMap.put("type",1);
//                                            OrdersMessageMap.put("phone_id",phoneBuyer);
//                                            OrdersMessageMap.put("title","Your order has been sent to sellers");
//                                            OrdersMessageMap.put("newMessage","Dear customer, thanks for your purchase,Soon your order will be confirmed by the sellers,And will send to you soon...");
//                                            OrdersMessageMap.put("pid",pidOrder);
//                                            OrdersMessageMap.put("from","automaton");
//
//
//                                            OrderListRF= FirebaseDatabase.getInstance().getReference().child("Message").child("Buyer");
//                                            OrderListRF.child(pidOrder).updateChildren(OrdersMessageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//
//
//                                                    if(task.isSuccessful()) {
//
//                                                        Toast.makeText(OrderActivity.this,"Order send to the sellers",Toast.LENGTH_SHORT).show();
//                                                        Intent intent = new Intent(OrderActivity.this,BuyerActivity.class);
//                                                        intent.putExtra("phoneBuyer",phoneBuyer);
//                                                        startActivity(intent);
//
//                                                    }
//
//
//
//                                                }
//                                            });
//
//
//
//
//
//
//                                                            }
//
//
//
//
//
//
//                                                        }
//                                                    });
//
//
//
//
//
//                                                }
//
//
//
//
//                                            }
//                                        });
//
//
//
//
//
//
//
//
//
//
//
//                                    }
//
//
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
//
//
//
//
//
//
//
//
//
//                        }
//
//
//                    }
//                });




    }
}