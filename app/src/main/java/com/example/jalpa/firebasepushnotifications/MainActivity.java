package com.example.jalpa.firebasepushnotifications;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView mProfileLabel;
    private TextView mUsersLabel;
    private TextView mNotificationLabel;

    private ViewPager mMainPager;

    private PagerViewAdapter mPagerViewAdapter;

    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("20DEC","ON START METHOD");
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            sendToLogin();
        }
    }
    private void sendToLogin(){
        Intent loginIntent = new Intent(MainActivity.this , LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth = FirebaseAuth.getInstance();

        mProfileLabel =(TextView) findViewById(R.id.profileLabel);
        mUsersLabel =(TextView) findViewById(R.id.usersLabel);
        mNotificationLabel =(TextView) findViewById(R.id.notificationsLabel);

        mMainPager = (ViewPager) findViewById(R.id.mainPager);
        mMainPager.setOffscreenPageLimit(2);

        mPagerViewAdapter = new PagerViewAdapter(getSupportFragmentManager());
        mMainPager.setAdapter(mPagerViewAdapter);

        mProfileLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(0);
            }

        });

        mUsersLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(1);
            }

        });

        mNotificationLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainPager.setCurrentItem(2);
            }

        });

        mMainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @TargetApi(Build.VERSION_CODES.M)
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPageSelected(int position) {
                changeTabs(position);
                
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeTabs(int position) {

        if(position==0){
            mProfileLabel.setTextColor(getColor(R.color.textBarBright));
            mProfileLabel.setTextSize(22);

            mUsersLabel.setTextColor(getColor(R.color.textTabLight));
            mUsersLabel.setTextSize(16);

            mNotificationLabel.setTextColor(getColor(R.color.textTabLight));
            mNotificationLabel.setTextSize(16);

        }

        if(position==1){
            mProfileLabel.setTextColor(getColor(R.color.textTabLight));
            mProfileLabel.setTextSize(16);

            mUsersLabel.setTextColor(getColor(R.color.textBarBright));
            mUsersLabel.setTextSize(22);

            mNotificationLabel.setTextColor(getColor(R.color.textTabLight));
            mNotificationLabel.setTextSize(16);

        }

        if(position==2){
            mProfileLabel.setTextColor(getColor(R.color.textTabLight));
            mProfileLabel.setTextSize(16);

            mUsersLabel.setTextColor(getColor(R.color.textTabLight));
            mUsersLabel.setTextSize(16);

            mNotificationLabel.setTextColor(getColor(R.color.textBarBright));
            mNotificationLabel.setTextSize(22);

        }
    }


}
