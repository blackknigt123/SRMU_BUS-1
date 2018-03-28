package com.example.vaibhav.srmu_bus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class splash_screen extends AppCompatActivity {

    private int SLEEP_TIMER=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //  setContentView(R.layout.activity_splash_screen);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

    setContentView(R.layout.splash_screen);
     getSupportActionBar().hide();
      LogoLauncher logolauncher = new LogoLauncher();
      logolauncher.start();



    }


    private class LogoLauncher extends Thread
    {
        public void run()
        {
            try
            {
                sleep(1000* SLEEP_TIMER);

            }

            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            Intent intent = new Intent(splash_screen.this,stu_home.class);
            startActivity(intent);
            splash_screen.this.finish();

        }
    }
}
