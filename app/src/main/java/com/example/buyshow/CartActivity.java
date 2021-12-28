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
import android.widget.Toast;

import com.example.buyshow.Model.Cart;
import com.example.buyshow.Prevalent.Prevalent;
import com.example.buyshow.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button nextbtn;
    private TextView totalamounttxt;
    private String buyer_Phone,phone_id;
    private float totalPrice=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        buyer_Phone= getIntent().getExtras().get("phoneBuyer").toString();
        recyclerView = findViewById(R.id.cart_list);
//        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        nextbtn =(Button) findViewById(R.id.next_proces_btn);
        totalamounttxt = (TextView) findViewById(R.id.total_price);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {







                Intent intent = new Intent(CartActivity.this,OrderActivity.class);
                intent.putExtra("phoneBuyer",buyer_Phone);
                intent.putExtra("totalPrice",String.valueOf(totalPrice));
                startActivity(intent);

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartListRef
        .child(buyer_Phone).child("Products"),Cart.class).build();
        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options){
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model) {
                phone_id = model.getSellerID();
                holder.txtProductQuantity.setText("Quantity = "+model.getQuantity());
                holder.txtProductPrice.setText("Price "+model.getPrice() + "$");
                totalPrice+=(Float.parseFloat(model.getPrice())*Integer.valueOf(model.getQuantity()));
                totalamounttxt.setText(String.valueOf(totalPrice));
                holder.txtProductName.setText(model.getPname());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence option[]= new CharSequence[]{
                          "Edit", "Remove"

                        };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Option");
                        builder.setItems(option, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i == 0){
                                    Intent intent = new Intent(CartActivity.this,ProductDetailsActivity.class);
                                    intent.putExtra("phoneBuyer",model.getBuyerID());
                                    intent.putExtra("phone_id",model.getSellerID());
                                    intent.putExtra("pid",model.getPid());
                                    startActivity(intent);
                                }
                                if(i == 1){
                                    cartListRef.child(model.getBuyerID()).child("Products").child(model.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                DatabaseReference  ROrderListRF = FirebaseDatabase.getInstance().getReference();
                                                ROrderListRF.child("Sellers order").child(model.getPidOrder()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(CartActivity.this,"Product remove",Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                });


                                            }

                                        }
                                    });
                                }

                            }
                        });
                        builder.show();

                    }
                });

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout,parent,false);
                        CartViewHolder holder = new CartViewHolder(view);
                        return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

}