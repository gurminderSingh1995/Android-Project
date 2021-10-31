package com.example.bakingbella;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.UUID;

public class AddFood extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText txtFoodName, txtFoodCategory,txtFoodPrice, txtWeight, txtDescription;
    ImageView imgFood;
    Button addFood;
    private Uri imageUri;
    Spinner spinner;
    String category;
    public String randomKey="";
    private StorageReference mStorageRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        // mStorageRef = FirebaseStorage.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference("categories");

        txtFoodName = findViewById(R.id.txtFoodName);
      //  txtFoodCategory = findViewById(R.id.txtFoodCategory);
        txtFoodPrice = findViewById(R.id.txtFoodPrice);
        txtWeight = findViewById(R.id.txtWeight);
        txtDescription = findViewById(R.id.txtDescription);
        addFood = findViewById(R.id.addFood);
        imgFood = findViewById(R.id.imgFood);
        spinner = findViewById(R.id.spinner);
        Intent mainIntent = new Intent(AddFood.this, AdminHome.class);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.categories, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        imgFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });

        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference reference = database.getReference("categories").child(category);
                String foodName = txtFoodName.getText().toString().trim();
                String foodPrice = txtFoodPrice.getText().toString().trim();
                String foodWeight = txtWeight.getText().toString().trim();
                String foodDesc = txtDescription.getText().toString().trim();
                String foodImage = "gs://baking-bella.appspot.com/categories/images/"+randomKey;
                String foodCategory = category;

                if( validateFoodname() & validateFoodPrice() & validateFoodWeight() & validateFoodDesc() & validateCategory()) {
                        Log.i("insideif",category+foodCategory);
                        FoodModel foodModel = new FoodModel(foodName, foodPrice, foodWeight, foodDesc, foodCategory, foodImage);
                        Log.i("foodmodel",foodModel.toString());
                        reference.child(foodName).setValue(foodModel);
                        Toast.makeText(AddFood.this, "Food added successfully!", Toast.LENGTH_LONG).show();
                        startActivity(mainIntent);

                }

            }
        });
    }
    public void choosePicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            imgFood.setImageURI(imageUri);
            uploadPicture();
        }
    }
    public void uploadPicture(){

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading image...");
        pd.show();
       // Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
         randomKey = UUID.randomUUID().toString();
        StorageReference ref = mStorageRef.child("images/"+ randomKey);

        ref.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Toast.makeText(AddFood.this, "Added", Toast.LENGTH_LONG).show();
                        Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();
//                        String imgRes = taskSnapshot.toString();
                        if(downloadUri.isSuccessful()) {
                            String generatedFilePath = downloadUri.getResult().toString();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(AddFood.this, "not added", Toast.LENGTH_LONG).show();
                        // Handle unsuccessful uploads
                        // ...
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double percent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Percentage: "+(int) percent + "%");
                    }
                });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        category = "empty";
    }
    private Boolean validateFoodname() {
        String val = txtFoodName.getText().toString().trim();

        if (val.isEmpty()) {
            txtFoodName.setError("Field cannot be empty");
            return false;
        }else {
            txtFoodName.setError(null);
            return true;
        }
    }
    private Boolean validateFoodPrice() {
        String val = txtFoodPrice.getText().toString().trim();

        if (val.isEmpty()) {
            txtFoodPrice.setError("Field cannot be empty");
            return false;
        }else {
            txtFoodPrice.setError(null);
            return true;
        }
    }
    private Boolean validateFoodWeight() {
        String val = txtWeight.getText().toString().trim();

        if (val.isEmpty()) {
            txtWeight.setError("Field cannot be empty");
            return false;
        }else {
            txtWeight.setError(null);
            return true;
        }
    }
    private Boolean validateFoodDesc() {
        String val = txtDescription.getText().toString().trim();

        if (val.isEmpty()) {
            txtDescription.setError("Field cannot be empty");
            return false;
        }else {
            txtDescription.setError(null);
            return true;
        }
    }
    private Boolean validateCategory(){
        Log.i("cat",category);
        if(category.equals("Select category:")){
            Toast.makeText(AddFood.this,"Select a category!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }
}