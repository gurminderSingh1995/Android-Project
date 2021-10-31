package com.example.bakingbella;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bakingbella.ui.home.HomeFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends FirebaseRecyclerAdapter<CategoriesData, CategoriesAdapter.CategoryHolder> {
    public CategoriesAdapter(FirebaseRecyclerOptions<CategoriesData> options){
        super(options);
    }

    @NonNull
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new CategoryHolder(layoutInflater,parent);
    }

    //Displaying UI components in a recycler view using adapter
    @Override
    protected void onBindViewHolder(@NonNull CategoryHolder holder, int position, @NonNull CategoriesData model) {


    }
    class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtCategoryName;
//        ImageView imgBook;
//        TextView txtProductPrice;
//        RatingBar productRating;
        public CategoryHolder(LayoutInflater inflater,ViewGroup parent){
            super(inflater.inflate(R.layout.categories,parent,false));
            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
//            imgBook = itemView.findViewById(R.id.imgBook);
//            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
//            productRating = itemView.findViewById(R.id.productRating);
           // itemView.setOnClickListener(this);
        }
        //passing data to display activity using Intent
        @Override
        public void onClick(View v) {

            CategoriesData categoriesData = getItem(getLayoutPosition());
            String categories = categoriesData.getCategories();
            String foodName = categoriesData.getFoodName();
            String foodCategory = categoriesData.getFoodCategory();
            String foodPrice = categoriesData.getFoodPrice();
            String foodWeight = categoriesData.getFoodWeight();
            String foodDesc = categoriesData.getFoodDesc();

        }
    }
}

