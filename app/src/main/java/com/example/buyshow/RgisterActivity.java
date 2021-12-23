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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.DatabaseMetaData;
import java.util.HashMap;
/*

We have created 2 buttons that are connected to our xml and each has an id that when you click on a certain button he knows which button it is.
Below line 23 you can see the function SetOnClickListener-listener.
We listen to our buttons that we have already initialized.
So if a certain button is pressed - it will move us to a certain screen.
Intent This is a function that accepts the current screen we are on and the screen we want to switch to.

And in lines 26-27 we are in the MainActivity screen and want to switch to LoginActivity and then the StartActivity function activates Intent and causes us to move to another screen.
And the same thing when we want to switch to RegisterActivity - which is the registration screen.


 */
public class RgisterActivity extends AppCompatActivity {
private Button createAccountBuyerButton;
private Button createAccountSellerButton;
private EditText InputName, InputPhoneNumber,InputPassword;
private ProgressDialog loadingBar;
private String ParentDB="Buyer";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgister);
        createAccountBuyerButton = (Button) findViewById(R.id.buyer_account);
        createAccountSellerButton =  (Button) findViewById(R.id.seller_account);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        loadingBar = new ProgressDialog(this);

        createAccountBuyerButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        ParentDB="Buyer";
        CreateAccount();


    }
});

        createAccountSellerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParentDB="Seller";
                CreateAccount();
            }
        });

    }
/*Create three strings for the user's name, phone and password.
- We did tests and end cases for entering the details: each case separately.
(If one of the fields is empty and makes an error)
- When the user has filled in the fields and the registration was successful,
Define the loading screen, and the message displayed on the screen.

 */

private void CreateAccount(){
        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();
        if (TextUtils.isEmpty(name)){
            Toast.makeText(this,"Please write your name",Toast.LENGTH_SHORT).show();
        }
    else if (TextUtils.isEmpty(phone)){
        Toast.makeText(this,"Please write your phone",Toast.LENGTH_SHORT).show();
    }
    else if (TextUtils.isEmpty(password)){
        Toast.makeText(this,"Please write your password",Toast.LENGTH_SHORT).show();
    }
    else
        {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Pleas wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidaephoneNumber(name,phone,password);

        }
    }
    /*
    This function creates a user, and before that checks whether the user already exists - his cell phone number is already updated in the system. And if it is updated then it sends the user an error message and returns it to the login screen.
    And if not, it creates a new user with its details and saves it in the database.
    Then an oncomplete function that checks if the registration was successful - sends a message and forwards it to the login screen.
    If failed, sends error.
     */
    private void ValidaephoneNumber(String name, String phone, String password) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
            if(!(snapshot.child(ParentDB).child(phone).exists())){

                HashMap<String,Object> userDataMap=new HashMap<>();
                userDataMap.put("phone",phone);
                userDataMap.put("password",password);
                userDataMap.put("name",name);
                if(ParentDB.equals("Seller")){
                    userDataMap.put("sellCounter",0);
                    userDataMap.put("rank",0);
                }
                else if(ParentDB.equals("Buyer")){
                    userDataMap.put("messageN",0);

                }



                // RootRef is reference to the firebase
                RootRef.child(ParentDB).child(phone).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RgisterActivity.this,"Excellent, account created.",Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent=new Intent(RgisterActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                        else{
                            loadingBar.dismiss();
                            Toast.makeText(RgisterActivity.this,"Network Error: please try again.",Toast.LENGTH_SHORT).show();

                        }
                    }
                });



            }
            else{
                Toast.makeText(RgisterActivity.this,"This " + phone+ "already exists.",Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
                Toast.makeText(RgisterActivity.this,"please try again using another phone number.",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(RgisterActivity.this,MainActivity.class);
                startActivity(intent);
            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}