package com.example.bakingbella;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class UserFoodDetails extends AppCompatActivity  implements AdapterView.OnItemSelectedListener  {
    TextView txtFoodTitle, txtPrice, txtQuantity, txtDetails;
    ImageView foodDetailImage;
    Spinner spinner;
    Button btnAddToCart;
    String number;
    DatabaseReference mReference;
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    String foodName, foodPrice, foodWeight,foodCategory, foodDesc, foodImage, username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_food_details);
        txtFoodTitle = findViewById(R.id.txtFoodTitle);
        txtDetails = findViewById(R.id.txtDetails);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        txtPrice = findViewById(R.id.txtPrice);
        txtQuantity = findViewById(R.id.txtQuantity);
        spinner = findViewById(R.id.spinner);
        foodDetailImage = findViewById(R.id.foodDetailImage);

        mAuth = FirebaseAuth.getInstance();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String category = intent.getStringExtra("category");
        username = intent.getStringExtra("username");
        Intent checkoutIntent = new Intent(UserFoodDetails.this, Checkout.class);
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("usernameFoodDetails",username);

              //  startActivity(checkoutIntent);
             addingToCartList();
            }
        });
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("categories").child(category);
        Query checkFood = ref.orderByChild("foodName").equalTo(name);

        checkFood.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                foodName = snapshot.child(name).child("foodName").getValue(String.class);
                 foodPrice = snapshot.child(name).child("foodPrice").getValue(String.class);
                 foodWeight = snapshot.child(name).child("foodWeight").getValue(String.class);
                 foodCategory = snapshot.child(name).child("foodCategory").getValue(String.class);
                 foodDesc = snapshot.child(name).child("foodDesc").getValue(String.class);
                 foodImage = snapshot.child(name).child("foodImage").getValue(String.class);

                txtFoodTitle.setText(foodName);
                txtPrice.setText("Price: $"+foodPrice);
                txtQuantity.setText("Weight: "+foodWeight);
                txtDetails.setText(foodDesc);

                StorageReference storRef = FirebaseStorage.getInstance().getReferenceFromUrl(foodImage);
                Glide.with(UserFoodDetails.this).load(storRef).into(foodDetailImage);

            }
            


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
    @Override
    protected void onStart()
    {
        super.onStart();
        Toast.makeText(getApplicationContext(),"Now onStart() calls", Toast.LENGTH_LONG).show(); //onStart Called
    }

    private void addingToCartList() {
        try {
            String saveCurrentTime, saveCurrentDate;

            Calendar calForDate = Calendar.getInstance();
            SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, YYYY");
            saveCurrentDate = currentDate.format(calForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calForDate.getTime());




            final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("CartList");
          //  FirebaseUser firebaseUser = mAuth.getInstance().getCurrentUser();


            final HashMap<String, Object> cartMap = new HashMap<>();
            cartMap.put("foodName", foodName);
            cartMap.put("foodPrice", foodPrice);
            cartMap.put("foodQuantity", number);
            cartMap.put("date", saveCurrentDate);
            cartMap.put("time", saveCurrentTime);

        cartListRef.child("UserView").child(username)
                .child("Products").child(foodName)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                                    cartListRef.child("AdminView").child(username)
                                    .child("Products").child(foodName)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(UserFoodDetails.this, "Added to Cart List", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(UserFoodDetails.this,UserCart.class);
                                                intent.putExtra("username",username);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                        }
                    }
                });
        }
        catch (Exception e){}
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        number = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
