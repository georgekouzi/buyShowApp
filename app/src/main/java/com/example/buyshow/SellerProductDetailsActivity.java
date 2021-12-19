package com.example.buyshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buyshow.Model.products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class SellerProductDetailsActivity extends AppCompatActivity {
private String pid,phoneId,image_uri;
    private TextView productPrice,productName,productDetails;
    private ImageView productImage;
    private Button buttonBack,buttonDeleteProduct;
    private DatabaseReference getProductInfoDB;
    private StorageReference productImageRef;
    private Uri imageUri;


    public SellerProductDetailsActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_product_details);
        phoneId=getIntent().getExtras().get("phone_id").toString();
        pid=getIntent().getExtras().get("pid").toString();
        image_uri=getIntent().getExtras().get("Image_uri").toString();

        productName = (TextView) findViewById(R.id.seller_product_name_d);
        productDetails = (TextView) findViewById(R.id.seller_product_details_d);
        productPrice = (TextView) findViewById(R.id.seller_product_price_d);
        productImage = (ImageView) findViewById(R.id.seller_product_image_d);

        buttonDeleteProduct= (Button) findViewById(R.id.seller_button_delete_product_d);
        buttonBack= (Button) findViewById(R.id.seller_button_back);






        getProductInfoDB = FirebaseDatabase.getInstance().getReference().child("Products");
        getProductInfoDB.child(pid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(SellerProductDetailsActivity.this,pid,Toast.LENGTH_SHORT).show();

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

        buttonDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StorageReference photoRef = FirebaseStorage.getInstance().getReferenceFromUrl(image_uri);
                photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(SellerProductDetailsActivity.this,"Product delete url successful.",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SellerProductDetailsActivity.this,"error",Toast.LENGTH_SHORT).show();

                    }
                });


                getProductInfoDB.child(pid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(SellerProductDetailsActivity.this,"Product delete successful.",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SellerProductDetailsActivity.this,SellerProductActivity.class);
                            startActivity(intent);
                        }
                    }
                });


            }
        });
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SellerProductDetailsActivity.this,SellerProductActivity.class);
                startActivity(intent);
            }
        });






    }





}