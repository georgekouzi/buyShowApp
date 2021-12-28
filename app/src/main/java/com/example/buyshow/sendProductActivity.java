package com.example.buyshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buyshow.Model.BuyerOrders;
import com.example.buyshow.Model.Cart;
import com.example.buyshow.Model.products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class sendProductActivity extends AppCompatActivity {
private String phone_id,ordersPid,quantity,Price,pidp,pname;
private Button send;
private TextView Country,buyerName,PhoneNumber,TotalPrice,orderAddress,city,DateTime;
private float totalPrice=0;
private BuyerOrders buyerOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_product);

        Country = (TextView) findViewById(R.id.country_b);
        buyerName = (TextView) findViewById(R.id.buyer_name_b);
        PhoneNumber = (TextView) findViewById(R.id.phone_number_b);
        TotalPrice = (TextView) findViewById(R.id.total_price_b);
        orderAddress = (TextView) findViewById(R.id.order_address_b);
        city = (TextView) findViewById(R.id.city_b);
        DateTime = (TextView) findViewById(R.id.date_time_d);
        send = (Button) findViewById(R.id.send_b);

        phone_id=getIntent().getExtras().get("phone_id").toString();
        ordersPid=getIntent().getExtras().get("Orderpid").toString();
        quantity=getIntent().getExtras().get("quantity").toString();
        Price=getIntent().getExtras().get("Price").toString();
        pidp=getIntent().getExtras().get("pidp").toString();
        pname=getIntent().getExtras().get("pname").toString();

        totalPrice=(Float.parseFloat(Price)*Integer.valueOf(quantity));





        DatabaseReference sendOrderDB = FirebaseDatabase.getInstance().getReference().child("Orders");
        //This trigger allows access to a database for the same product and saves the variables so that it can overwrite the text currently written there by the model of the same product.
        //And the picasso function allows for a more beautiful display
        sendOrderDB.child(ordersPid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                buyerOrders=snapshot.getValue(BuyerOrders.class);
//                Toast.makeText(sendProductActivity.this,buyerOrders.getCity(),Toast.LENGTH_SHORT).show();


                buyerName.setText("Name: "+ buyerOrders.getName());
                PhoneNumber.setText("Phone: "+ buyerOrders.getPhone());
                DateTime.setText("Order at: "+ buyerOrders.getDate()+"  "+buyerOrders.getTime());
                orderAddress.setText("Shipping Address: "+ buyerOrders.getAddress());
                Country.setText("Shipping Country: "+ buyerOrders.getCountry());
                city.setText("Shipping City: "+ buyerOrders.getCity());
                TotalPrice.setText("Total Amount: "+totalPrice);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> OrdersMessageMap = new HashMap<>();
                OrdersMessageMap.put("type", 3);
                OrdersMessageMap.put("phone_id", buyerOrders.getBuyerID());
                OrdersMessageMap.put("title", "The order has been received and shipped to your home");
                OrdersMessageMap.put("newMessage", "Congratulations "+buyerOrders.getName()+",\n The seller has confirmed the "+ pname+" purchase and the product will be shipped and will arrive at your home in the coming days.\n" +
                                    "When the shipment arrives, please confirm it by clicking on the following link:");
                OrdersMessageMap.put("pid", pidp);
                OrdersMessageMap.put("from",phone_id);

                DatabaseReference  OrderListRF = FirebaseDatabase.getInstance().getReference().child("Message").child("Buyer");
                OrderListRF.child(pidp).updateChildren(OrdersMessageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            DatabaseReference RootRef;
                            RootRef = FirebaseDatabase.getInstance().getReference();
                            RootRef.child("Buyer").child(buyerOrders.getBuyerID()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {


                                    int mn= Integer.valueOf(snapshot.child("messageN").getValue().toString());
                                        mn++;

                                    snapshot.child("messageN").getRef().setValue(mn);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            DatabaseReference  ROrderListRF = FirebaseDatabase.getInstance().getReference();
                            ROrderListRF.child("Sellers order").child(pidp).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(sendProductActivity.this,sellersNewOrderActivity.class);
                                        intent.putExtra("phone_id",phone_id);
                                        startActivity(intent);




                                    }
                                }
                            });



//





                        }

                    }
                });






            }
        });




    }
}