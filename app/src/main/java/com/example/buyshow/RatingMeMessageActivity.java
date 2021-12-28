package com.example.buyshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RatingMeMessageActivity extends AppCompatActivity {
private RatingBar ratingBar;
    private Button button;
    private String pid,phoneBuyer,userType,from,type;

    private float rat=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_me_message);
        pid= getIntent().getExtras().get("pid").toString();
        phoneBuyer= getIntent().getExtras().get("phoneBuyer").toString();
        userType= getIntent().getExtras().get("usertype").toString();
        from= getIntent().getExtras().get("from").toString();
        type= getIntent().getExtras().get("Type").toString();

        ratingBar = (RatingBar)findViewById(R.id.rating_seller_bar);
        button = (Button) findViewById(R.id.rating_seller);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rat=ratingBar.getRating();
                DatabaseReference RootRef;

                RootRef = FirebaseDatabase.getInstance().getReference();
                RootRef.child("Seller").child(from).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        int mn= Integer.valueOf(snapshot.child("sellCounter").getValue().toString());
                        mn++;
                        snapshot.child("sellCounter").getRef().setValue(mn);

                        int rank= Integer.valueOf(snapshot.child("rank").getValue().toString());
                        rank+=rat;
                        snapshot.child("rank").getRef().setValue(rank);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent intent = new Intent(RatingMeMessageActivity.this, MessageActivity.class);
                intent.putExtra("phoneBuyer",phoneBuyer);
                intent.putExtra("usertype",userType);
                startActivity(intent);






            }
        });




    }
}