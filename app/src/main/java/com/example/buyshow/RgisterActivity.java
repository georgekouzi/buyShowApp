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

public class RgisterActivity extends AppCompatActivity {
private Button createAccountButton;
private EditText InputName, InputPhoneNumber,InputPassword;
private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rgister);
        createAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        loadingBar = new ProgressDialog(this);

createAccountButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        CreateAccount();


    }
});

    }

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
    else{
loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Pleas wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidaephoneNumber(name,phone,password);

        }
    }

    private void ValidaephoneNumber(String name, String phone, String password) {

final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
            if(!(snapshot.child("User").child(phone).exists())){

                HashMap<String,Object> userDataMap=new HashMap<>();
                userDataMap.put("phon",phone);
                userDataMap.put("password",password);
                userDataMap.put("name",name);

                RootRef.child("User").child(phone).updateChildren(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
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