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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

import io.paperdb.Paper;

public class UserActivity extends AppCompatActivity {
    private DatabaseReference usersRef;
    private RecyclerView recyclerView, recyclerViewBuyer;
    private String phone_id,type;
    private Button delete;
    private RatingBar ratingBar;
    private TextView rank, user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user2);

        usersRef = FirebaseDatabase.getInstance().getReference();
        recyclerView = (RecyclerView) findViewById(R.id.user_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        phone_id = getIntent().getExtras().get("phone").toString();
        type = getIntent().getExtras().get("type").toString();

        ratingBar = (RatingBar) findViewById(R.id.user_rating_u);
        rank = (TextView) findViewById(R.id.user_rank_u);
        user_type = (TextView) findViewById(R.id.user_type_new);
        delete = (Button) findViewById(R.id.delete_user);

    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<User> options = new FirebaseRecyclerOptions.Builder<User>().setQuery(usersRef.child(type), User.class).build();
        FirebaseRecyclerAdapter<User, User_Holder> adapter = new FirebaseRecyclerAdapter<User, User_Holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull User_Holder holder, int position, @NonNull User model) {

                holder.txtUserName.setText(model.getName());
                holder.txtUserType.setText(type);
//                    holder.txtUserRank.setVisibility(View.INVISIBLE);
//                    holder.ratingBar.setVisibility(View.INVISIBLE);
                holder.txtUserPhone.setText(model.getPhone());
                holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
                     @Override
                public void onClick(View view) {
                         DatabaseReference  ROrderListRF = FirebaseDatabase.getInstance().getReference();
                         ROrderListRF.child(type).child(model.getPhone()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                 if(task.isSuccessful()){


                                 }
                             }
                         });


                         }
                    });


            }

            @NonNull
            @Override
            public User_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user, parent, false);
                User_Holder holder = new User_Holder(view);
                return holder;

            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }
}