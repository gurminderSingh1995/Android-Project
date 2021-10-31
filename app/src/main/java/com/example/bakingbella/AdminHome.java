package com.example.bakingbella;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bakingbella.ui.home.HomeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class AdminHome extends AppCompatActivity {
    TextView txtCategoryName;
    ImageView imgCategory;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    LinearLayoutManager HorizontalLayout;
    private FirebaseAuth mAuth;
    private RecyclerView.LayoutManager layoutManager;
    public ArrayList<CategoriesData> productsList = new ArrayList<>();

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Intent addFoodIntent = new Intent(AdminHome.this, AddFood.class);

        FloatingActionButton fab = findViewById(R.id.fab);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(addFoodIntent);
            }
        });


        CategoriesData c1 = new CategoriesData();
        c1.setCategories("Breads");
        c1.setImage(R.drawable.category4);

        CategoriesData c2 = new CategoriesData();
        c2.setCategories("Desserts");
        c2.setImage(R.drawable.category1);

        CategoriesData c3 = new CategoriesData();
        c3.setCategories("Hot beverages");
        c3.setImage(R.drawable.category3);

        CategoriesData c4 = new CategoriesData();
        c4.setCategories("Cold beverages");
        c4.setImage(R.drawable.category2);

        CategoriesData c5 = new CategoriesData();
        c5.setCategories("Pizza");
        c5.setImage(R.drawable.category5);


        productsList.add(c1);
        productsList.add(c2);
        productsList.add(c3);
        productsList.add(c4);
        productsList.add(c5);
        recyclerView = findViewById(R.id.rViewAdminFoodCategory);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        HorizontalLayout
                = new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(HorizontalLayout);
        mAdapter = new ProductsAdapter(productsList, true, "admin");
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.user_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:
                Intent i = new Intent(AdminHome.this, LoginActivity.class);

                finish();
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}