package com.example.bakingbella;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class UpdateFood extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText txtFoodName,txtFoodPrice, txtWeight, txtDescription;
    Spinner spinner;
    ImageView imgUpdateFood;
    String category, mFoodName;
    private Uri imageUri;
    Button updateFood, deleteFood;
    String randomKey="";
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);

        mStorageRef = FirebaseStorage.getInstance().getReference("categories");
        txtFoodName = findViewById(R.id.txtFoodName);
        //  txtFoodCategory = findViewById(R.id.txtFoodCategory);
        txtFoodPrice = findViewById(R.id.txtFoodPrice);
        txtWeight = findViewById(R.id.txtWeight);
        txtDescription = findViewById(R.id.txtDescription);
        imgUpdateFood = findViewById(R.id.imgUpdateFood);
        spinner = findViewById(R.id.spinner);
        updateFood = findViewById(R.id.updateFood);
        deleteFood = findViewById(R.id.deleteFood);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.categories, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        mFoodName = intent.getStringExtra("name");
        Toast.makeText(this,mFoodName,Toast.LENGTH_LONG).show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories").child(category);
        Query checkFood = ref.orderByChild("foodName").equalTo(mFoodName);


        checkFood.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    String foodName = snapshot.child(mFoodName).child("foodName").getValue(String.class);
                                    String foodPrice = snapshot.child(mFoodName).child("foodPrice").getValue(String.class);
                                    String foodWeight = snapshot.child(mFoodName).child("foodWeight").getValue(String.class);
                                    String foodCategory = snapshot.child(mFoodName).child("foodCategory").getValue(String.class);
                                    String foodDesc = snapshot.child(mFoodName).child("foodDesc").getValue(String.class);
                                    String foodImage = snapshot.child(mFoodName).child("foodImage").getValue(String.class);
                                    Log.i("name", foodName);
                                    if(!foodName.isEmpty() || !foodPrice.isEmpty() || !foodWeight.isEmpty() || !foodCategory.isEmpty() || !foodDesc.isEmpty()){
                                        txtFoodName.setText(foodName);
                                        txtFoodPrice.setText(foodPrice);
                                        txtWeight.setText(foodWeight);
                                        txtDescription.setText(foodDesc);
                                        StorageReference storRef = FirebaseStorage.getInstance().getReferenceFromUrl(foodImage);
                                        Glide.with(UpdateFood.this).load(storRef).into(imgUpdateFood);
//                                        Glide.with(this)
//                                                .load(foodImage)
//                                                .into(imgUpdateFood);
                                        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(UpdateFood.this, R.array.categories, android.R.layout.simple_spinner_item);
                                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        spinner.setAdapter(adapter);
                                        if (category != null) {
                                            int spinnerPosition = adapter.getPosition(category);
                                            spinner.setSelection(spinnerPosition);
                                        }

                 //       startActivity(homeIntent);
                    }else{
                        Toast.makeText(UpdateFood.this,"Incorrect Password",Toast.LENGTH_SHORT).show();
                    }
//                }
//                else {
//                    Toast.makeText(UpdateFood.this,"No such user exists",Toast.LENGTH_SHORT).show();
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        try {
            imgUpdateFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    choosePicture();
                }
            });
            updateFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String foodName = txtFoodName.getText().toString().trim();
                    String foodPrice = txtFoodPrice.getText().toString().trim();
                    String foodWeight = txtWeight.getText().toString().trim();
                    String foodDesc = txtDescription.getText().toString().trim();
                    String foodImage = "gs://baking-bella.appspot.com/categories/images/"+randomKey;
                    if (!foodName.isEmpty() && !foodPrice.isEmpty() && !foodWeight.isEmpty() && !foodDesc.isEmpty()) {
                        updateFoodDetails(foodName, foodPrice, foodWeight, foodDesc, category, foodImage );


                    }
                }
            });


            deleteFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories").child(category);
//                Query query = ref.child("foodName").equalTo(foodName);

                    checkFood.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().removeValue();
                                Intent listIntent = new Intent(UpdateFood.this, FoodList.class);
                                startActivity(listIntent);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
//                Log.e("onCancelled", databaseError.toException());
                        }
                    });
                }
            });
        }
        catch (Exception e) {}

    }

    private void updateFoodDetails(String foodName, String foodPrice, String foodWeight, String foodDesc, String foodCategory, String foodImage) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("categories").child(category);
        FoodModel foodModel = new FoodModel(foodName, foodPrice, foodWeight, foodDesc, foodCategory, foodImage);
        Log.i("foodmodel",foodModel.getFoodPrice());
        reference.child(foodName).setValue(foodModel);
        Intent listIntent = new Intent(UpdateFood.this, FoodList.class);
        listIntent.putExtra("category", category);
        startActivity(listIntent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        category = "empty";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            imgUpdateFood.setImageURI(imageUri);
            uploadPicture();
        }
    }
    public void choosePicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

        private void uploadPicture () {
        try

        {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading image...");
        pd.show();
        // Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        randomKey = UUID.randomUUID().toString();
        StorageReference ref = mStorageRef.child("images/" + randomKey);

        ref.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Toast.makeText(UpdateFood.this, "Added", Toast.LENGTH_LONG).show();
                        Task<Uri> downloadUri = taskSnapshot.getStorage().getDownloadUrl();
//                        String imgRes = taskSnapshot.toString();
                        if (downloadUri.isSuccessful()) {
                            String generatedFilePath = downloadUri.getResult().toString();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(UpdateFood.this, "not added", Toast.LENGTH_LONG).show();
                        // Handle unsuccessful uploads
                        // ...
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double percent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Percentage: " + (int) percent + "%");
                    }
                });
        }
        catch(Exception e) {}
    }

}