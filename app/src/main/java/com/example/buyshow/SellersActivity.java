package com.example.buyshow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SellersActivity extends AppCompatActivity {
    private Button checkOrder ,addProduct,viewYourUploadedProducts;
    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellers);

        phone = getIntent().getExtras().get("phone").toString();

        checkOrder=(Button) findViewById(R.id.check_order);
        addProduct=(Button) findViewById(R.id.add_product);
        viewYourUploadedProducts=(Button) findViewById(R.id.view_your_uploaded_products);

        viewYourUploadedProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SellersActivity.this,SellerProductActivity.class);
                //A function that allows you to move arguments from one page to another
                intent.putExtra("phone_id",phone);
                startActivity(intent);
            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SellersActivity.this,SellersCategoryActivity.class);
                intent.putExtra("phone_id",phone);
                startActivity(intent);
            }
        });
//
//        viewYourUploadedProducts.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(SellersActivity.this,RgisterActivity.class);
//                startActivity(intent);
//            }
//        });


    }
}