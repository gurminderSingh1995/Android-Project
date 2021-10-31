package com.example.bakingbella.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bakingbella.AddFood;
import com.example.bakingbella.LoginActivity;
import com.example.bakingbella.R;
import com.google.firebase.auth.FirebaseAuth;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        slideshowViewModel =
//                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        Intent i = new Intent(root.getContext(), LoginActivity.class);
        FirebaseAuth.getInstance().signOut();
        startActivity(i);

        return root;
    }
}