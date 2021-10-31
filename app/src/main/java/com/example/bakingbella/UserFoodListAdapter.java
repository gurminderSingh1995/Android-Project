package com.example.bakingbella;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class UserFoodListAdapter extends FirebaseRecyclerAdapter<FoodModel, UserFoodListAdapter.foodsViewholder> {
    private String category;
    String[] name = new String[10];
    Integer i=0;
    String username;
    // private foodsViewholder.ClickListener mClickListener;
    public UserFoodListAdapter(
            @NonNull FirebaseRecyclerOptions<FoodModel> options, String category, String username)
    {
        super(options);
        this.username = username;
        this.category = category;
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull UserFoodListAdapter.foodsViewholder holder,
                     int position, @NonNull FoodModel model)
    {
        StorageReference storRef = FirebaseStorage.getInstance().getReferenceFromUrl(model.getFoodImage());
        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")

        holder.txtFoodname.setText(model.getFoodName());
        name[i] = (model.getFoodName());

        // Add lastname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.txtFoodPrice.setText("$"+model.getFoodPrice());

        // Add age from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
      // holder.txtFoodDesc.setText(model.getFoodDesc());
        holder.txtFoodWeight.setText(model.getFoodWeight());
//        Uri myUri = Uri.parse(model.getFoodImage());
//        holder.imgFood.setImageURI(myUri);
        Glide.with(holder.imgFood.getContext()).load(storRef).into(holder.imgFood);
        i++;
    }

    // Function to tell the class about the Card view (here
    // "person.xml")in
    // which the data will be shown
    @NonNull
    @Override
    public UserFoodListAdapter.foodsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_food_list_item, parent, false);
        return new UserFoodListAdapter.foodsViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class foodsViewholder extends RecyclerView.ViewHolder {

        TextView txtFoodname, txtFoodPrice, txtFoodWeight;
        ImageView imgFood;
        Integer pos  ;
        public foodsViewholder(@NonNull View itemView)
        {
            super(itemView);
            txtFoodname = itemView.findViewById(R.id.txtFoodname);
            txtFoodPrice = itemView.findViewById(R.id.txtFoodPrice);
        //    txtFoodDesc = itemView.findViewById(R.id.txtFoodDesc);
            txtFoodWeight = itemView.findViewById(R.id.txtFoodWeight);
            imgFood = itemView.findViewById(R.id.imgFood);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(itemView.getContext(), UserFoodDetails.class);
                    intent.putExtra("category",category);
                    intent.putExtra("username",username);
                    intent.putExtra("name",name[getAdapterPosition()]);

                    itemView.getContext().startActivity(intent);
                }
            });

    }}
}
