package com.example.bakingbella.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingbella.CategoriesAdapter;
import com.example.bakingbella.CategoriesData;
import com.example.bakingbella.MainActivity;
import com.example.bakingbella.ProductsAdapter;
import com.example.bakingbella.R;
import com.example.bakingbella.UserFoodListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    TextView txtCategoryName;
    ImageView imgCategory;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    LinearLayoutManager HorizontalLayout;
    private RecyclerView.LayoutManager layoutManager;
    public ArrayList<CategoriesData> productsList = new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Intent i = getActivity().getIntent();
        String userName = i.getStringExtra("username");
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
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference reference = database.getReference("categories");
        //   rViewAdminFoodCategory = root.findViewById(R.id.rViewAdminFoodCategory);
        //   query = database.getReference("categories").child("Desserts").getParent();


//        reference.addListenerForSingleValueEvent(new ValueEventListener() {
//                                                     @Override
//                                                     public void onDataChange(DataSnapshot dataSnapshot) {
//                                                         for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
//                                                             String parent = childSnapshot.getKey();
//                                                             Log.i("parenttttttttt", parent);
//                                                           //  mCategoriesData.add(parent);
//                                                          //   FirebaseRecyclerOptions<CategoriesData> options = new FirebaseRecyclerOptions.Builder<CategoriesData>().setQuery(query, CategoriesData.class).build();
//                                                         }
//                                                     }
//
//                                                     @Override
//                                                     public void onCancelled(@NonNull DatabaseError error) {
//
//                                                     }
//                                                 });

//        FirebaseRecyclerOptions<CategoriesData> options = new FirebaseRecyclerOptions.Builder<CategoriesData>()
//                .setQuery(query, new SnapshotParser<CategoriesData>() {
//                    @NonNull
//                    @Override
//                    public CategoriesData parseSnapshot(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
//                                                          //  String parent = ;
//                        return new CategoriesData(childSnapshot.getKey());
//                    }
//                }).build();

        recyclerView = root.findViewById(R.id.rViewAdminFoodCategory);
        layoutManager = new LinearLayoutManager(HomeFragment.super.getContext());
        recyclerView.setLayoutManager(layoutManager);
        HorizontalLayout
                = new LinearLayoutManager(
                HomeFragment.super.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerView.setLayoutManager(HorizontalLayout);
        mAdapter = new ProductsAdapter(productsList, false, userName);
        recyclerView.setAdapter(mAdapter);

//        imgCategory = root.findViewById(R.id.imgCategory);
//        txtCategoryName = root.findViewById(R.id.txtCategoryName);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;

//    public void onStart() {
//        super.onStart();
//        adapter.startListening();
    }
}