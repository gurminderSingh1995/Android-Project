package com.example.bakingbella;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FoodListAdapter extends FirebaseRecyclerAdapter<FoodModel, FoodListAdapter.foodsViewholder> {
    private String category;
    String[] name = new String[10];
    Integer i=0;
   // private foodsViewholder.ClickListener mClickListener;
    public FoodListAdapter(
            @NonNull FirebaseRecyclerOptions<FoodModel> options, String category)
    {

        super(options);
        this.category = category;
    }

    // Function to bind the view in Card view(here
    // "person.xml") iwth data in
    // model class(here "person.class")
    @Override
    protected void
    onBindViewHolder(@NonNull foodsViewholder holder,
                     int position, @NonNull FoodModel model)
    {
        StorageReference storRef = FirebaseStorage.getInstance().getReferenceFromUrl(model.getFoodImage());
        // Add firstname from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")

        holder.txtFoodname.setText(model.getFoodName());
        name[i] = (model.getFoodName());

        holder.txtFoodPrice.setText("$"+model.getFoodPrice());

        // Add age from model class (here
        // "person.class")to appropriate view in Card
        // view (here "person.xml")
        holder.txtFoodDesc.setText(model.getFoodDesc());
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
    public foodsViewholder
    onCreateViewHolder(@NonNull ViewGroup parent,
                       int viewType)
    {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.foods_layout, parent, false);
        return new FoodListAdapter.foodsViewholder(view);
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class foodsViewholder extends RecyclerView.ViewHolder {

        TextView txtFoodname, txtFoodPrice, txtFoodDesc, txtFoodWeight;
        TextView updateFood;
        ImageView imgFood;
        Integer pos;
        public foodsViewholder(@NonNull View itemView)
        {

            super(itemView);

            txtFoodname = itemView.findViewById(R.id.txtFoodname);
            txtFoodPrice = itemView.findViewById(R.id.txtFoodPrice);
            txtFoodDesc = itemView.findViewById(R.id.txtFoodDesc);
            txtFoodWeight = itemView.findViewById(R.id.txtFoodWeight);
            imgFood = itemView.findViewById(R.id.imgFood);
            updateFood = itemView.findViewById(R.id.updateFood);

            updateFood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                //    Toast.makeText(,"Food is updated successfully!",Toast.LENGTH_LONG).show();
                    Log.i("click","update clicked!");
                    Intent intent = new Intent(itemView.getContext(), UpdateFood.class);
                    intent.putExtra("category",category);

                    intent.putExtra("name",name[getAdapterPosition()]);

                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
