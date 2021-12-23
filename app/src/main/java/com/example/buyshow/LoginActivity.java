package com.example.buyshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buyshow.Model.User;
import com.example.buyshow.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;


public class LoginActivity extends AppCompatActivity {

    private EditText InputPhoneNumber, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    //switch user
    static TextView Op1Link,Op2Link;
    private CheckBox RememberUser;

    private String ParentDB="Buyer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
/*
Initialize the buttons on the login screen, and save the information that the user enters as variables
 */
        LoginButton = (Button) findViewById(R.id.login_btn);
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        InputPhoneNumber = (EditText) findViewById(R.id.login_phone_number_input);
        loadingBar = new ProgressDialog(this);
        RememberUser =  (CheckBox) findViewById(R.id.remember_me_chkb);
        Paper.init(this);
        Op1Link = (TextView) findViewById(R.id.op1_panel_link);
        Op2Link = (TextView) findViewById(R.id.op2_panel_link);

/*
This function is associated with the login screen when there are several login buttons.
And to select the type of user logging in to the app.
The default type displayed on the main button is the buyer.
And the 2 side buttons are the manager's or seller's.
If the user is a manager or seller when he presses the button, the main button of the login will change accordingly and so will the 2 side buttons.
 */

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
    /*
       In this function, we check the information entered by the user, and check if it is correct.
   If they are not then an error message is sent for the same field.
   And if all goes well, move to the loading screen.
        */
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
      /*

This function asks for a reference to our database in order to access the details of the users and search for the same user who entered his details in order to log into his account.
If the user does not exist then an error message is sent.
And if the user exists according to his phone number then we will extract all the details that are updated under that same number. Then check if all the information the user entered is equal to the information we saved in order to give the user permission to log in to this account.
If the details are equal then we will check what type of user he is saving - buyer, seller or manager and then from there he enters a screen that matches the type of user.
And if the password is not the same then an error message is sent and the login screen is initialized from the beginning.

     */

    private void AllowAccessToAccount(String phone, String password) {
        if(RememberUser.isChecked()){
        Paper.book().write(Prevalent.UserPasswordKey,password);
        Paper.book().write(Prevalent.UserPhoneKey,phone);
        Paper.book().write(Prevalent.UserTypeKey,ParentDB);


        }


        if(ParentDB.equals("Buyer")){

        }

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
                            intent.putExtra("phoneBuyer",phone);
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
                                intent.putExtra("phone",phone);
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