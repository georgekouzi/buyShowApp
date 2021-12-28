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

import com.example.buyshow.Model.User;
import com.example.buyshow.ViewHolder.User_Holder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

import io.paperdb.Paper;

public class AdminsActivity extends AppCompatActivity {
    private Button Logout_admin ,buyer_user,seller_users;
    private String phone;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins);
        Logout_admin=(Button) findViewById(R.id.Logout_admin);
        buyer_user=(Button) findViewById(R.id.buyer_user);
        seller_users= (Button)findViewById(R.id.seller_users);
        phone = getIntent().getExtras().get("phone").toString();
        Logout_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                Intent intent=new Intent(AdminsActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        buyer_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminsActivity.this,UserActivity.class);
                //A function that allows you to move arguments from one page to another
                intent.putExtra("phone",phone);
                intent.putExtra("type","Buyer");
                startActivity(intent);
            }
        });

        seller_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminsActivity.this,UserActivity.class);
                intent.putExtra("phone",phone);
                intent.putExtra("type","Seller");
                startActivity(intent);
            }
        });






//
//        backTologin = (Button)findViewById(R.id.back_login);
//        backTologin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AdminActivity.this,LoginActivity.class);
//                startActivity(intent);
//            }
//        });

//        usersRef.child("Seller").child(phone_id).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User seller =  snapshot.getValue(User.class);
//                DecimalFormat df = new DecimalFormat("###.#");
//                double  rating =Double.parseDouble( df.format((double)(seller.getRank()) / (double)(seller.getSellCounter())));
//                if(seller.getSellCounter()!=0) {
////                    rank.setText(String.valueOf(rating));
//                    ratingBar.setRating((float) rating);
//                    ratingBar.setIsIndicator(true);
//                }
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });




//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//
//        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>().setQuery(usersRef.child("Seller"),User.class).build();
//        FirebaseRecyclerAdapter<User, User_Holder> adapter = new FirebaseRecyclerAdapter<User, User_Holder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull User_Holder holder, int position, @NonNull User model) {
//
//                    holder.txtUserName.setText(model.getName());
//                    holder.txtUserType.setText("Seller");
////                    holder.txtUserRank.setVisibility(View.INVISIBLE);
////                    holder.ratingBar.setVisibility(View.INVISIBLE);
//                    holder.txtUserPhone.setText(model.getPhone());
//
//                    holder.itemView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            Intent intent = new Intent(AdminsActivity.this,User.class);
//                            intent.putExtra("name",model.getName());
//                            intent.putExtra("phone_id",model.getPhone());
//
//                            startActivity(intent);
//                        }
//                    });
//
//
//
//            }
//
//            @NonNull
//            @Override
//            public User_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user,parent,false);
//                User_Holder holder =new User_Holder(view);
//                return holder;
//
//            }
//        };
//        recyclerView.setAdapter(adapter);
//        adapter.startListening();
//
////        FirebaseRecyclerOptions<User> optionsBuyer = new FirebaseRecyclerOptions.Builder<User>().setQuery(usersRef.child("Buyer"),User.class).build();
////        FirebaseRecyclerAdapter<User, User_Holder> adapterBuyer = new FirebaseRecyclerAdapter<User, User_Holder>(optionsBuyer) {
////            @Override
////            protected void onBindViewHolder(@NonNull User_Holder holder, int position, @NonNull User model) {
////
////                holder.txtUserName.setText(model.getName());
////                holder.txtUserType.setText("Buyer");
////                holder.txtUserPhone.setText(model.getPhone());
////
////                holder.itemView.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View view) {
////                        Intent intent = new Intent(AdminsActivity.this,User.class);
////                        intent.putExtra("name",model.getName());
////                        intent.putExtra("phone_id",model.getPhone());
////
////                        startActivity(intent);
////                    }
////                });
////
////
////            }
////
////            @NonNull
////            @Override
////            public User_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user,parent,false);
////                User_Holder holder =new User_Holder(view);
////                return holder;
////
////            }
////        };
////        recyclerViewBuyer.setAdapter(adapterBuyer);
////        adapterBuyer.startListening();
//
//
//


    }



}