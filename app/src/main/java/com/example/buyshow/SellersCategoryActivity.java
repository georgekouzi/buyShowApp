package com.example.buyshow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SellersCategoryActivity extends AppCompatActivity {
    private ImageView tshirts , tsports,femaledresses,sweather,glasses,pursesbags,hats,shoess,headphoness,laptops,watches,mobiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellers_category);
        //reference to all image view
        tshirts= (ImageView) findViewById(R.id.tshirts);
        tsports= (ImageView) findViewById(R.id.tsports);
        femaledresses= (ImageView) findViewById(R.id.femaledresses);
        sweather= (ImageView) findViewById(R.id.sweather);
        glasses= (ImageView) findViewById(R.id.glasses);
        pursesbags= (ImageView) findViewById(R.id.pursesbags);
        hats= (ImageView) findViewById(R.id.hats);
        shoess= (ImageView) findViewById(R.id.shoess);
        headphoness= (ImageView) findViewById(R.id.headphoness);
        laptops= (ImageView) findViewById(R.id.laptops);
        watches= (ImageView) findViewById(R.id.watches);
        mobiles= (ImageView) findViewById(R.id.mobiles);


        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellersCategoryActivity.this,SellerAddNewProductActivityActivity.class);
                intent.putExtra("category","tshirts");
                startActivity(intent);

            }
        });
        tsports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellersCategoryActivity.this,SellerAddNewProductActivityActivity.class);
                intent.putExtra("category","tsports");
                startActivity(intent);
            }
        });
        femaledresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellersCategoryActivity.this,SellerAddNewProductActivityActivity.class);
                intent.putExtra("category","femaledresses");
                startActivity(intent);
            }
        });
        sweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellersCategoryActivity.this,SellerAddNewProductActivityActivity.class);
                intent.putExtra("category","sweather");
                startActivity(intent);
            }
        });
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellersCategoryActivity.this,SellerAddNewProductActivityActivity.class);
                intent.putExtra("category","glasses");
                startActivity(intent);
            }
        });
        pursesbags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellersCategoryActivity.this,SellerAddNewProductActivityActivity.class);
                intent.putExtra("category","pursesbags");
                startActivity(intent);
            }
        });
        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellersCategoryActivity.this,SellerAddNewProductActivityActivity.class);
                intent.putExtra("category","hats");
                startActivity(intent);
            }
        });
        shoess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellersCategoryActivity.this,SellerAddNewProductActivityActivity.class);
                intent.putExtra("category","shoess");
                startActivity(intent);
            }
        });
        headphoness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellersCategoryActivity.this,SellerAddNewProductActivityActivity.class);
                intent.putExtra("category","headphoness");
                startActivity(intent);
            }
        });
        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellersCategoryActivity.this,SellerAddNewProductActivityActivity.class);
                intent.putExtra("category","laptops");
                startActivity(intent);
            }
        });
        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellersCategoryActivity.this,SellerAddNewProductActivityActivity.class);
                intent.putExtra("category","watches");
                startActivity(intent);
            }
        });
        mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellersCategoryActivity.this,SellerAddNewProductActivityActivity.class);
                intent.putExtra("category","mobiles");
                startActivity(intent);
            }
        });





    }
}