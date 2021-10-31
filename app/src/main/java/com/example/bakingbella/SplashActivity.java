package com.example.bakingbella;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    Animation mTopAnim;
    Animation mBottomAnim;
    ImageView imageViewSplash;
    TextView textTitleSplash, textTagSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        // Animation
        mTopAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        mBottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        imageViewSplash = (ImageView) findViewById(R.id.imageViewSplash);
        textTitleSplash = (TextView) findViewById(R.id.textTitleSplash);
        textTagSplash= (TextView) findViewById(R.id.textTagSplash);

        imageViewSplash.setAnimation(mTopAnim);
        textTitleSplash.setAnimation(mBottomAnim);
        textTagSplash.setAnimation(mBottomAnim);


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent homeIntent = new Intent(SplashActivity.this, MainActivity.class);
//                startActivity(homeIntent);
//                finish();
//            }
//        }, 5000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);

    }
}