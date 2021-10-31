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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {
    private ArrayList<CategoriesData> mProductsList;
    private boolean flag;
    private String username;

    // constructor for initializing array list
    public ProductsAdapter(ArrayList<CategoriesData> productsList, boolean flag, String username) {
        mProductsList = productsList;
        this.flag = flag;
        this.username = username;
    }

    @NonNull
    @Override
    public ProductsAdapter.ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new ProductsViewHolder(layoutInflater, parent);
    }

    // Binding data according to position
    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.ProductsViewHolder holder, int position) {
        CategoriesData p = mProductsList.get(position);

        Context productContext = holder.itemView.getContext();
        int resId = productContext.getResources().getIdentifier(String.valueOf(p.getImage()), "drawable",productContext.getPackageName());
        holder.txtCategoryName.setText(p.getCategories());
        holder.imgCategory.setImageResource(resId);
     //   holder.txtPrice.setText("CDN$"+p.getPrice());

    }

    @Override
    public int getItemCount() {
        return mProductsList.size();
    }

    // on click event on every item of recycler view
    class ProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtCategoryName, txtPrice;
        ImageView imgCategory;

        public ProductsViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.categories, parent, false));
            itemView.setOnClickListener(this);
            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
//            txtPrice = itemView.findViewById(R.id.txtPrice);
            imgCategory = itemView.findViewById(R.id.imgCategory);
        }
        public void onClick(View v){
            int position = getLayoutPosition();
            CategoriesData selected = mProductsList.get(position);
//
            if(flag == true) {
                Intent intent = new Intent(this.itemView.getContext(), FoodList.class);
                intent.putExtra("category", selected.getCategories());
                this.itemView.getContext().startActivity(intent);
            }
            else{
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                Log.i("adapter userrrrr", user+"");
                Intent intent = new Intent(this.itemView.getContext(), UserFoodList.class);
                intent.putExtra("username", username);
                intent.putExtra("category", selected.getCategories());
                this.itemView.getContext().startActivity(intent);
            }

//            intent.putExtra("image",selected.getImage());
//
//            intent.putExtra("star", selected.getStar());
//            intent.putExtra("price", selected.getPrice());
//
//            intent.putExtra("details",selected.getDetails());

        }
    }
}
