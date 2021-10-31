package com.example.bakingbella;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.Objects;

public class ViewpagerAdapter extends PagerAdapter {
    Context context;
    int[] imagesLogin;
    LayoutInflater mLayoutInflater;

    public ViewpagerAdapter(Context context, int[]images){
        this.context = context;
        this.imagesLogin = images;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return imagesLogin.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

            View itemView = mLayoutInflater.inflate(R.layout.viewpager, container, false);
            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageViewDisplay);
            imageView.setImageResource(imagesLogin[position]);
            Objects.requireNonNull(container).addView(itemView);
            return itemView;

    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
