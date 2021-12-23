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
import com.example.buyshow.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {
private ElegantNumberButton counterProduct;
private TextView productPrice,productName,productDetails;
private ImageView productImage;
private Button buttonBack,buttonAddProductToCart;
private int sumOfProduct=0;
private String productId,phone_id ,BuyerPhone;
private DatabaseReference cartListRF;
//private DatabaseReference getProductInfoDB;

    public ProductDetailsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productId=getIntent().getExtras().get("pid").toString();
        phone_id=getIntent().getExtras().get("phone_id").toString();
        BuyerPhone=getIntent().getExtras().get("phoneBuyer").toString();

        counterProduct = (ElegantNumberButton) findViewById(R.id.counter_button);
        productName = (TextView) findViewById(R.id.product_name_d);
        productDetails = (TextView) findViewById(R.id.product_details_d);
        productPrice = (TextView) findViewById(R.id.product_price_d);
        productImage = (ImageView) findViewById(R.id.product_image_d);
        buttonBack =(Button) findViewById(R.id.button_back);
        buttonAddProductToCart =(Button) findViewById(R.id.button_add_product_to_cart_d);

        DatabaseReference getProductInfoDB = FirebaseDatabase.getInstance().getReference().child("Products");
        //This trigger allows access to a database for the same product and saves the variables so that it can overwrite the text currently written there by the model of the same product.
        //And the picasso function allows for a more beautiful display
        getProductInfoDB.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

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
                addingToCart();
//                intent.putExtra("phoneBuyer",BuyerPhone);
//                intent.putExtra("phone_id",phone_id);
//                intent.putExtra("pid",productId);
//                startActivity(intent);




            }
        });

    }

    private void addingToCart() {
        String saveCurrentDate,saveCurrentTime;
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentData =new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentData.format(calendar.getTime());

        SimpleDateFormat currentTime =new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        cartListRF=FirebaseDatabase.getInstance().getReference().child("Cart List");

        HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("pid",productId);
        cartMap.put("Seller ID",phone_id);
        cartMap.put("pname",productName.getText());
        cartMap.put("price",productPrice.getText());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",counterProduct.getNumber());
        cartMap.put("discount","");
        cartListRF.child("Buyer View").child(BuyerPhone).child("Products").child(productId).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    cartListRF.child("Seller View").child(BuyerPhone).child("Products").child(productId).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ProductDetailsActivity.this,"Added To Cart",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ProductDetailsActivity.this,BuyerActivity.class);
                                intent.putExtra("phoneBuyer",BuyerPhone);
                                intent.putExtra("phone_id",phone_id);
                                intent.putExtra("pid",productId);
                                startActivity(intent);

                            }
                        }
                    });


                    }
            }
        });





    }


}