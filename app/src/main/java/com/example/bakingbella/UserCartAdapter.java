package com.example.bakingbella;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

import java.util.ArrayList;
import java.util.Arrays;

public class UserCartAdapter extends FirebaseRecyclerAdapter<CartModel, UserCartAdapter.cartViewholder> {

    String[] name = new String[10];
    Integer i=0;
    String username, foodName, foodPrice, foodQuantity, foodTime, foodDate;
    Integer flag = 0, temp =0, totalPrice=0;


    // private foodsViewholder.ClickListener mClickListener;
    public UserCartAdapter(
            @NonNull FirebaseRecyclerOptions<CartModel> options, String username)
    {
        super(options);
        this.username = username;


    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull UserCartAdapter.cartViewholder holder,
                     int position, @NonNull CartModel model) {
        try {
        totalPrice=0;
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("CartList").child("UserView").child(username).child("Products");

//        Query checkFood = ref.orderByChild("foodName");
//
//        checkFood.addListenerForSingleValueEvent(new ValueEventListener() {


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("length", snapshot.getChildrenCount() + "");
                flag = 0;


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    if (flag <= temp) {
                        foodName = dataSnapshot.child("foodName").getValue(String.class);
                        foodPrice = dataSnapshot.child("foodPrice").getValue(String.class);
                        foodQuantity = dataSnapshot.child("foodQuantity").getValue(String.class);
                        foodDate = dataSnapshot.child("date").getValue(String.class);
                        foodTime = dataSnapshot.child("time").getValue(String.class);

                        holder.cartFoodName.setText(foodName);
                        //    name[i] = (model.getFoodName());
                        holder.cartFoodQuantity.setText("Quantity: " + foodQuantity);
                        holder.cartFoodDate.setText(foodDate);
                        holder.cartFoodTime.setText(foodTime);
                        holder.cartFoodPrice.setText("$" + foodPrice);
                      //  name[i] = (foodName);
                        if (flag == temp) {
                     //       i++;
                            temp++;
                            break;
                        } else {
                       //     i--;
                            temp--;
                            flag++;
                        }
                      //  i++;
                         temp++;
                    }

                }

                name[temp]=foodName;
                Log.i("temp", name[temp]+"");
                totalPrice += Integer.parseInt(foodPrice)*Integer.parseInt(foodQuantity);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
    catch(Exception e){}
    }

    @NonNull
    @Override
    public UserCartAdapter.cartViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_cart_layout, parent, false);
        return new UserCartAdapter.cartViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class cartViewholder extends RecyclerView.ViewHolder {

        TextView cartFoodName, cartFoodQuantity, cartFoodPrice, cartFoodDate, cartFoodTime, removeCartItem;
        // ImageView imgFood;
        Integer pos  ;
        public cartViewholder(@NonNull View itemView)
        {
            super(itemView);
            cartFoodName = itemView.findViewById(R.id.cartFoodName);
            cartFoodQuantity = itemView.findViewById(R.id.cartFoodQuantity);
            cartFoodPrice = itemView.findViewById(R.id.cartFoodPrice);
            cartFoodDate = itemView.findViewById(R.id.cartFoodDate);
            cartFoodTime = itemView.findViewById(R.id.cartFoodTime);

            removeCartItem = itemView.findViewById(R.id.removeCartItem);
;
            removeCartItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        String food = name[getAdapterPosition()+1];
                        Log.i("foodname_cart", name[getAdapterPosition()+1]+"");
                        RemoveCartItem(food);
                        Intent i = ((Activity)itemView.getContext()).getIntent();
                        ((Activity)itemView.getContext()).finish();
                        ((Activity)itemView.getContext()).startActivity(i);
                    }
                    catch (Exception e){};
                }
            });


        }}

    private void RemoveCartItem(String food) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("CartList").child("UserView").child(username).child("Products");
        Query checkFood = ref.orderByChild("foodName").equalTo(food);
        checkFood.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot foodSnapshot : dataSnapshot.getChildren()) {
                    foodSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
//                Log.e("onCancelled", databaseError.toException());
            }
        });
    }
}

