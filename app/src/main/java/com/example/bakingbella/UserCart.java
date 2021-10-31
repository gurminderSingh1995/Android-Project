package com.example.bakingbella;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserCart extends AppCompatActivity {
    private RecyclerView rViewCart;
    UserCartAdapter adapter; // Create Object of the Adapter class
    DatabaseReference mReference;
    Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart);
        rViewCart = findViewById(R.id.rViewCart);
        Intent checkoutintent = new Intent(this, Checkout.class);
        btnCheckout  = findViewById(R.id.btnCheckout);


        Intent cartIntent = getIntent();
        String username = cartIntent.getStringExtra("username");
        try {
            mReference = FirebaseDatabase.getInstance().getReference("CartList").child("UserView").child(username).child("Products");
            Log.i("mReference", mReference + "");
            // To display the Recycler view linearly
            rViewCart.setLayoutManager(
                    new LinearLayoutManager(this));
            FirebaseRecyclerOptions<CartModel> options
                    = new FirebaseRecyclerOptions.Builder<CartModel>()
                    .setQuery(mReference, CartModel.class)
                    .build();
            // Connecting object of required Adapter class to
            // the Adapter class itself
            adapter = new UserCartAdapter(options, username);
            // Connecting Adapter class with the Recycler view*/
            rViewCart.setAdapter(adapter);



            btnCheckout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Integer total= adapter.totalPrice;
                    checkoutintent.putExtra("total",total);
                    Log.i("total in userCart", total+"");
                    checkoutintent.putExtra("username",username);
                    startActivity(checkoutintent);
                }
            });

        }
        catch (Exception e) {}

    }
    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    // Function to tell the app to stop getting
    // data from database on stoping of the activity
    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
    }

}