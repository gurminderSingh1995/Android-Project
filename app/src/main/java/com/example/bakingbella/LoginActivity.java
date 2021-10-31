package com.example.bakingbella;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends AppCompatActivity {
    ViewPager mViewPager;
    Button btnAdmin, btnUser;
    int[] imagesLogin = {R.drawable.vpager1, R.drawable.vpager2,R.drawable.vpager3, R.drawable.vpager4};
    ViewpagerAdapter mViewPagerAdapter;
    TextView txtDescription;
 //   public ArrayList<OrdersData> ordersList = new ArrayList<>();

    String about = "Baking bella is newly launched Italian bakery and is your best friend in baking that will share you recipes, solutions and technology that will help you boost your baking business!" +
            "Register now and make your baking lives easier, faster and better.";

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mViewPager = (ViewPager) findViewById(R.id.viewPagerLogin);
        mViewPagerAdapter = new ViewpagerAdapter(LoginActivity.this, imagesLogin);
        mViewPager.setAdapter(mViewPagerAdapter);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setIcon(R.drawable.tagg);

        // setting up auto scroll view pager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == 4) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };
        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        txtDescription = findViewById(R.id.txtDescription);
        txtDescription.setText(about);
        Intent intent = new Intent(this, AdminLogin.class);
        Intent userIntent = new Intent(this, UserLogin.class);
        btnAdmin = (Button) findViewById(R.id.btnAdmin);
        btnUser = findViewById(R.id.btnUser);
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(userIntent);
            }
        });
    }
}