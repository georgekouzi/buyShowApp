package com.example.buyshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.buyshow.Model.User;
import com.example.buyshow.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
private Button joinNowButton ,loginButton;
private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingBar = new ProgressDialog(this);
        Paper.init(this);

        String userPhoneKey = Paper.book().read(Prevalent.UserPhoneKey);
        String userPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);
        String ParentDB = Paper.book().read(Prevalent.UserTypeKey);




        if(userPhoneKey!="" && userPasswordKey!="" && ParentDB!=""){
            if(!TextUtils.isEmpty(userPhoneKey) && !TextUtils.isEmpty(userPasswordKey) && !TextUtils.isEmpty(ParentDB)) {
                loginCheckBox(userPhoneKey,userPasswordKey,ParentDB);
            }
        }

            setContentView(R.layout.activity_main);



        joinNowButton=(Button) findViewById(R.id.main_join_now__btn);
        loginButton=(Button) findViewById(R.id.main_login_btn);





        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,RgisterActivity.class);
                startActivity(intent);
            }
        });





    }
    private void loginCheckBox(String userPhoneKey, String userPasswordKey, String ParentDB){
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(ParentDB).child(userPhoneKey).exists()) {
                    User usersData =snapshot.child(ParentDB).child(userPhoneKey).getValue(User.class);
//                    if(usersData.getPhone().equals(phone)){
                    if(usersData.getPassword().equals(userPasswordKey)){

                        if(ParentDB.equals("Buyer")){
                            Toast.makeText(MainActivity.this,"your logged in Successfully."+ParentDB,Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent=new Intent(MainActivity.this, BuyerActivity.class);
                            intent.putExtra("phone",userPhoneKey);
                            startActivity(intent);
                        }
                        else if(ParentDB.equals("Admin")){
                            Toast.makeText(MainActivity.this,"your logged in Successfully.",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent=new Intent(MainActivity.this, AdminActivity.class);
                            startActivity(intent);
                        }

                        else if(ParentDB.equals("Seller")){
                            Toast.makeText(MainActivity.this,"your logged in Successfully.",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent=new Intent(MainActivity.this, SellersActivity.class);
                            intent.putExtra("phone",userPhoneKey);
                            startActivity(intent);
                        }


                    }

                    else{
                        Toast.makeText(MainActivity.this,"Incorrect password.",Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);

                    }
                }
                else {
                    Toast.makeText(MainActivity.this,"Account with this "+userPhoneKey+" not exists "+ParentDB,Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}