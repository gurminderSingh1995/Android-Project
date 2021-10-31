package com.example.bakingbella;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class OrderDetails extends AppCompatActivity {
    TextView txtOrderTitle, txtOrderQuantity, txtOrderPrice;
    ImageView imgOrder;
    private RecyclerView recyclerView;
 //   private OrdersAdapter mAdapter;
    //  private ArrayList<OrdersData> ordersList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

//        txtOrderTitle = findViewById(R.id.txtOrderTitle);
//        txtOrderQuantity = findViewById(R.id.txtOrderQuantity);
//        txtOrderPrice = findViewById(R.id.txtOrderPrice);
//        imgOrder = findViewById(R.id.imgOrder);
//        recyclerView = findViewById(R.id.orderRview);
//
//        Intent intent = getIntent();
//        String title = intent.getStringExtra("title");
//        String quantity = intent.getStringExtra("quantity");
//        Float totalAmount = intent.getFloatExtra("price", -1);
//        Integer image = intent.getIntExtra("image", -1);
//
//        loadData();
//        buildRecyclerView();
//        ordersList.add(new OrdersData(title,Integer.parseInt(quantity),image,totalAmount));
//        mAdapter.notifyItemInserted(ordersList.size());
//        saveData();
    }
//
//    // method for saving new data in array list
//    private void saveData() {
//        SharedPreferences sharedPreferences = getSharedPreferences("orders shared pref", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(ordersList);
//        editor.putString("orders",json);
//        editor.apply();
//    }
//
//    private void buildRecyclerView() {
//        mAdapter = new OrdersAdapter(ordersList, com.example.dashop.OrderDetails.this);
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(manager);
//        recyclerView.setAdapter(mAdapter);
//    }
//
//    // Loading previously stored data from array list through shared preferences
//    private void loadData() {
//        SharedPreferences sharedPreferences = getSharedPreferences("orders shared pref", MODE_PRIVATE);
//        Gson gson = new Gson();
//
//        String json = sharedPreferences.getString("orders",null);
//        Type type = new TypeToken<ArrayList<OrdersData>>() {}.getType();
//        ordersList = gson.fromJson(json, type);
//        if(ordersList == null){
//            ordersList = new ArrayList<>();
//        }
//    }
}