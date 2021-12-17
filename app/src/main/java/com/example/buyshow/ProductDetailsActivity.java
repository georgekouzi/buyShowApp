package com.example.buyshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.buyshow.Model.products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity {
private ElegantNumberButton counterProduct;
private TextView productPrice,productName,productDetails;
private ImageView productImage;
private Button buttonBack,buttonAddProductToCart;
private int sumOfProduct=0;
private String productId="";
//private DatabaseReference getProductInfoDB;

    public ProductDetailsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        productId=getIntent().getExtras().get("pid").toString();
//        Toast.makeText(ProductDetailsActivity.this,productId,Toast.LENGTH_SHORT).show();

        counterProduct = (ElegantNumberButton) findViewById(R.id.counter_button);
        productName = (TextView) findViewById(R.id.product_name_d);
        productDetails = (TextView) findViewById(R.id.product_details_d);
        productPrice = (TextView) findViewById(R.id.product_price_d);

        productImage = (ImageView) findViewById(R.id.product_image_d);


        buttonBack =(Button) findViewById(R.id.button_back);
        buttonAddProductToCart =(Button) findViewById(R.id.button_add_product_to_cart_d);

        DatabaseReference getProductInfoDB = FirebaseDatabase.getInstance().getReference().child("Products");
        getProductInfoDB.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(ProductDetailsActivity.this,productId,Toast.LENGTH_SHORT).show();

                if(snapshot.exists()){
                    products productsD=snapshot.getValue(products.class);
                    productName.setText(productsD.getName());
                    productDetails.setText(productsD.getDescription());
                    productPrice.setText(productsD.getPrice());
                    Picasso.get().load(productsD.getImage()).into(productImage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailsActivity.this,BuyerActivity.class);
                startActivity(intent);
            }
        });

        buttonAddProductToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailsActivity.this,BuyerActivity.class);
                startActivity(intent);
            }
        });

    }
}