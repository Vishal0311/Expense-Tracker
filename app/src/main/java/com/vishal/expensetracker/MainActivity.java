package com.vishal.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
 //   SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

//        pref=getSharedPreferences("demo",0);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putInt("value",0);
//        editor.apply();


        new Handler().postDelayed(new Runnable() {
           @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(MainActivity.this, homeActivity.class);
                startActivity(i);
                finish();
            }
        }, 2500);


    }
}