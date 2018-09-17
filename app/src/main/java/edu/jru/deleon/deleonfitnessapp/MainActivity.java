package edu.jru.deleon.deleonfitnessapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity{

    ViewPager viewPager;
    ImageView btnPedometer,btnPlans,btnWorkout,btnWarmup;
    LinearLayout sliderDots;
    private int dotscount;
    private ImageView[]dots;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPedometer = (ImageView) findViewById(R.id.btnPedometer);
        btnPlans = (ImageView) findViewById(R.id.btnPlan);
        btnWarmup = (ImageView) findViewById(R.id.btnWarmUp);
        btnWorkout = (ImageView) findViewById(R.id.btnWorkout);
        sliderDots = (LinearLayout)findViewById(R.id.SliderDots);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i<dotscount; i++){
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                    R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            sliderDots.addView(dots[i],params);

        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i =0 ; i<dotscount;i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                            R.drawable.nonactive_dot));
                    dots[position].setImageDrawable(ContextCompat.getDrawable(getApplication(),
                            R.drawable.active_dot));

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),4000,8000);


        Animation anim = new ScaleAnimation(
                0.90f, 1f, // Start and end values for the X axis scaling
                0.90f, 1f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(2000);
        anim.setRepeatMode(Animation.INFINITE);
        anim.setRepeatCount(Animation.INFINITE);
        btnWorkout.startAnimation(anim);
        btnWarmup.startAnimation(anim);
        btnPlans.startAnimation(anim);
        btnPedometer.startAnimation(anim);




        btnWorkout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,Workout.class);
                startActivity(intent);
            }
        });

        btnPedometer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,Pedometer.class);
                startActivity(intent);
            }
        });


        btnPlans.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,Plans.class);
                startActivity(intent);
            }
        });


        btnWarmup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,Warmup.class);
                startActivity(intent);
            }
        });



    }
    public class MyTimerTask extends TimerTask {
        @Override
        public void run(){
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem()==1) {
                        viewPager.setCurrentItem(2);
                    } else if(viewPager.getCurrentItem()==2){
                        viewPager.setCurrentItem(3);
                    }else{
                        viewPager.setCurrentItem(1);
                    }
                }
            });
        }
    }
}
