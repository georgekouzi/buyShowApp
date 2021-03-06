package com.example.buyshow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class SellerAddNewProductActivityActivity extends AppCompatActivity {
    private String phone_id ,categoryName ,description,price,name,saveCurrentDate,saveCurrentTime;
    private Button addNewProductButton;
    private ImageView productImage;
    private EditText productName,productDescription,productPrice;
    private Uri imageUri;
    private  static final int galleryPick=1;
    private String productRandomKey , downloadImageUrl;
    private StorageReference productImageRef;
    private DatabaseReference productRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_add_new_product_activity);
        categoryName =  getIntent().getExtras().get("category").toString();
        phone_id = getIntent().getExtras().get("phone_id").toString();
        addNewProductButton = (Button) findViewById(R.id.add_new_product);
        productImage = (ImageView) findViewById(R.id.select_product_image);
        productName = (EditText) findViewById(R.id.product_name);
        productDescription = (EditText) findViewById(R.id.product_description);
        productPrice = (EditText) findViewById(R.id.product_price);
        productImageRef = FirebaseStorage.getInstance().getReference().child("ProductHolder Images");
        productRef =FirebaseDatabase.getInstance().getReference().child("Products");
        loadingBar = new ProgressDialog(this);

        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        addNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateProductData();
            }
        });

    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        //when the seller select image
        startActivityForResult(galleryIntent,galleryPick);


    }

    @Override
    //This function receives the image and checks whether the request has arrived and whether the data taken is not blank
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK&&data!=null){

            imageUri = data.getData();
            productImage.setImageURI(imageUri);
        }
    }
    /*
    This function extracts the data entered by the user and checks end cases and sends messages accordingly.
     */

    private void validateProductData() {
        description= productDescription.getText().toString();
        price= productPrice.getText().toString();
        name= productName.getText().toString();
        //if imageUri == null then the seller dont select the product image
        if(imageUri==null){
            Toast.makeText(this,"product image is mandatory",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty((description))){
            Toast.makeText(this,"please write product description",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty((price))){
            Toast.makeText(this,"please write product price",Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty((name))){
            Toast.makeText(this,"please write product name",Toast.LENGTH_SHORT).show();
        }
        else{
            storProductInformation();
        }


    }

    private void storProductInformation() {
        loadingBar.setTitle("Add new product");
        loadingBar.setMessage("Pleas wait, while we are adding new product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentData =new SimpleDateFormat("MMM dd,yyyy");
        saveCurrentDate = currentData.format(calendar.getTime());

        SimpleDateFormat currentTime =new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        //when the seller add new product then Current Date + Current Time this will be a uniqe random key ,it will not be refutable key because it contain the seconds
        productRandomKey = phone_id+" "+saveCurrentDate+saveCurrentTime;

        StorageReference filePath = productImageRef.child(imageUri.getLastPathSegment()+productRandomKey+".jpg");
        //An controllable task that uploads and fires events for success, progress and failure. It also allows pause and resume to control the upload operation.
        final UploadTask uploadTask =filePath.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String massage = e.toString();
                Toast.makeText(SellerAddNewProductActivityActivity.this,"Error: " + massage,Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(SellerAddNewProductActivityActivity.this,"Image uploaded Successfully",Toast.LENGTH_SHORT).show();
                Task<Uri>urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw  task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            downloadImageUrl= task.getResult().toString();
                            Toast.makeText(SellerAddNewProductActivityActivity.this,"Getting product image url successfully",Toast.LENGTH_SHORT).show();
                            saveProductInfoToDatabase();

                        }

                    }
                });
            }
        });

    }

    private void saveProductInfoToDatabase() {
        HashMap<String,Object> productMap = new HashMap<>();
        productMap.put("phone_id",phone_id);
        productMap.put("pid",productRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",description);
        productMap.put("image",downloadImageUrl);
        productMap.put("category",categoryName);
        productMap.put("price","Price "+price+"$");
        productMap.put("name",name);


        productRef.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent intent=new Intent(SellerAddNewProductActivityActivity.this, SellersCategoryActivity.class);
                    startActivity(intent);

                    loadingBar.dismiss();
                    Toast.makeText(SellerAddNewProductActivityActivity.this,"product added successfully",Toast.LENGTH_SHORT).show();

                }
                else{
                    loadingBar.dismiss();

                    Toast.makeText(SellerAddNewProductActivityActivity.this,"Error: "+task.getException().toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


}