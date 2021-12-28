package com.example.buyshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.buyshow.Model.message;
import com.example.buyshow.Model.products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class GetMessageActivity extends AppCompatActivity {
    private TextView messageView,title,Confirmation;
    private String pid,phoneBuyer,userType,from,type;
    private DatabaseReference getMessageRef;
    private Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_message);
        pid= getIntent().getExtras().get("pid").toString();
        phoneBuyer= getIntent().getExtras().get("phoneBuyer").toString();
        userType= getIntent().getExtras().get("usertype").toString();
        from= getIntent().getExtras().get("from").toString();
        type= getIntent().getExtras().get("Type").toString();







        Confirmation = (TextView) findViewById(R.id.Confirmation);

        messageView = (TextView) findViewById(R.id.text_m);
        title = (TextView) findViewById(R.id.title_m);
        if(type.equals("3")){
            Confirmation.setVisibility(View.VISIBLE);
        }


        getMessageRef = FirebaseDatabase.getInstance().getReference().child("Message");
        getMessageRef.child(userType).child(pid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    message mess =snapshot.getValue(message.class);
                    messageView.setText(mess.getNewMessage().toString());
                    title.setText(mess.getTitle().toString());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                HashMap<String, Object> OrdersMessageMap = new HashMap<>();
                OrdersMessageMap.put("type", 0);
                OrdersMessageMap.put("phone_id", phoneBuyer);
                OrdersMessageMap.put("title","Rate the seller");
                OrdersMessageMap.put("newMessage"," ");
                OrdersMessageMap.put("pid", pid+from);
                OrdersMessageMap.put("from",from);
                DatabaseReference  OrderListRF = FirebaseDatabase.getInstance().getReference().child("Message").child("Buyer");
                OrderListRF.child(pid+from).updateChildren(OrdersMessageMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(GetMessageActivity.this, MessageActivity.class);
                            intent.putExtra("phoneBuyer",phoneBuyer);
                            intent.putExtra("usertype",userType);
                            startActivity(intent);

                        }

                    }
                });




            }
        });


                back = (Button) findViewById(R.id.button_back_m);


                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(GetMessageActivity.this, MessageActivity.class);

                        intent.putExtra("phoneBuyer",phoneBuyer);
                        intent.putExtra("usertype",userType);
                        startActivity(intent);

                    }
                });







    }
}