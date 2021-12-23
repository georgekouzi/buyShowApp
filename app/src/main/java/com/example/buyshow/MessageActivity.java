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

import com.example.buyshow.Model.User;
import com.example.buyshow.Model.message;
import com.example.buyshow.Model.products;
import com.example.buyshow.ViewHolder.MessageHolder;
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

public class MessageActivity extends AppCompatActivity {
    private DatabaseReference productsRef;
    private RecyclerView recyclerView;
    private String userType,phone_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
//        productsRef = FirebaseDatabase.getInstance().getReference();
        //RecycleView object - allows you to arrange list details for display
        recyclerView = (RecyclerView)findViewById(R.id.message_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        productsRef =FirebaseDatabase.getInstance().getReference().child("Message");
        phone_id =  getIntent().getExtras().get("phoneBuyer").toString();
        userType =  getIntent().getExtras().get("usertype").toString();


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<message> options = new FirebaseRecyclerOptions.Builder<message>().setQuery(productsRef.child(userType),message.class).build();

        FirebaseRecyclerAdapter<message, MessageHolder> adapter = new FirebaseRecyclerAdapter<message, MessageHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MessageHolder holder, int position, @NonNull message model) {
                if(model.getPhone_id().equals(phone_id)) {
                    holder.titleText.setText(model.getTitle());


                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (model.getType() == 0) {


                                Intent intent = new Intent(MessageActivity.this, RatingMeMessageActivity.class);
                                intent.putExtra("from", model.getFrom());
                                startActivity(intent);
                            }







                        }
                    });

                }
                else{
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                }

            }

            @NonNull
            @Override
            public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.masssege_design,parent,false);
                MessageHolder holder =new MessageHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}