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
import android.widget.TextView;
import android.widget.Toast;

import com.example.buyshow.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    private EditText InputPhoneNumber, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    static TextView Op1Link,Op2Link;
    private String ParentDB="Buyer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton = (Button) findViewById(R.id.login_btn);
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        InputPhoneNumber = (EditText) findViewById(R.id.login_phone_number_input);
        loadingBar = new ProgressDialog(this);
        Op1Link = (TextView) findViewById(R.id.op1_panel_link);
        Op2Link = (TextView) findViewById(R.id.op2_panel_link);



        Op1Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Op1Link.getText().equals("for Buyer user")){
                    Op1Link.setText("for Seller user");
                    Op2Link.setText("for Admin user");
                    LoginButton.setText("Login Buyer");
                    ParentDB = "Buyer";
                }
                else if(Op1Link.getText().equals("for Seller user")){
                    Op1Link.setText("for Buyer user");
                    Op2Link.setText("for Admin user");
                    LoginButton.setText("Login Seller");
                    ParentDB = "Seller";
                }

            }
        });

        Op2Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Op2Link.getText().equals("for Admin user")){
                    Op1Link.setText("for Buyer user");
                    Op2Link.setText("for Seller user");
                    LoginButton.setText("Login Admin");
                    ParentDB = "Admin";
                }
                else if(Op2Link.getText().equals("for Seller user")){
                    Op1Link.setText("for Buyer user");
                    Op2Link.setText("for Admin user");
                    LoginButton.setText("Login Seller");
                    ParentDB = "Seller";
                }
            }
        });







        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });






    }

    private void LoginUser() {
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();
         if (TextUtils.isEmpty(phone)){
            Toast.makeText(this,"Please write your phone",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please write your password",Toast.LENGTH_SHORT).show();
        }
        else{
             loadingBar.setTitle("Login To Account");
             loadingBar.setMessage("Pleas wait, while we are checking the credentials.");
             loadingBar.setCanceledOnTouchOutside(false);
             loadingBar.show();

             AllowAccessToAccount(phone,password);
         }

    }

    private void AllowAccessToAccount(String phone, String password) {


        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(ParentDB).child(phone).exists()) {
                    User usersData =snapshot.child(ParentDB).child(phone).getValue(User.class);
//                    if(usersData.getPhone().equals(phone)){
                        if(usersData.getPassword().equals(password)){
                            if(ParentDB.equals("Buyer")){
                            Toast.makeText(LoginActivity.this,"your logged in Successfully."+ParentDB,Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent=new Intent(LoginActivity.this, BuyerActivity.class);
                            startActivity(intent);
                        }
                            else if(ParentDB.equals("Admin")){
                                Toast.makeText(LoginActivity.this,"your logged in Successfully.",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent=new Intent(LoginActivity.this, AdminActivity.class);
                                startActivity(intent);
                            }

                            else if(ParentDB.equals("Seller")){
                                Toast.makeText(LoginActivity.this,"your logged in Successfully.",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent=new Intent(LoginActivity.this, SellersActivity.class);
                                startActivity(intent);
                            }



                    }

                        else{
                            Toast.makeText(LoginActivity.this,"Incorrect password.",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent=new Intent(LoginActivity.this,LoginActivity.class);
                            startActivity(intent);

                        }
                }
                else {
                    Toast.makeText(LoginActivity.this,"Account with this "+phone+" not exists "+ParentDB,Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }



}