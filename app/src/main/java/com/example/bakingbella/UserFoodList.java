package com.example.bakingbella;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserFoodList extends AppCompatActivity {
    private RecyclerView recyclerFoodList;
    UserFoodListAdapter adapter; // Create Object of the Adapter class
    DatabaseReference mReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_food_list);
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        String username = intent.getStringExtra("username");

        mReference = FirebaseDatabase.getInstance().getReference("categories").child(category);

        recyclerFoodList = findViewById(R.id.recyclerUserFoodList);

        // To display the Recycler view linearly
        recyclerFoodList.setLayoutManager(
                new LinearLayoutManager(this));
        FirebaseRecyclerOptions<FoodModel> options
                = new FirebaseRecyclerOptions.Builder<FoodModel>()
                .setQuery(mReference, FoodModel.class)
                .build();
        // Connecting object of required Adapter class to
        // the Adapter class itself
        adapter = new UserFoodListAdapter(options, category, username);
        // Connecting Adapter class with the Recycler view*/
        recyclerFoodList.setAdapter(adapter);

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