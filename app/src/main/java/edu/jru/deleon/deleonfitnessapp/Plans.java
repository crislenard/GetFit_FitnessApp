package edu.jru.deleon.deleonfitnessapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Plans extends AppCompatActivity {
ViewPager viewPager;
    private int dotscount;
    private ImageView[]dots;
    LinearLayout linearLayout;
    FloatingActionButton btnHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);


        viewPager = (ViewPager)findViewById(R.id.vpDemo);
        linearLayout = (LinearLayout)findViewById(R.id.SliderDots2) ;
        ViewPageAdapterforDemo viewPagerAdapter = new ViewPageAdapterforDemo(this);
        viewPager.setAdapter(viewPagerAdapter);
        btnHome = (FloatingActionButton)findViewById(R.id.btnHome);


        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Plans.this,MainActivity.class);
                startActivity(intent);
            }
        });


        for(int i = 0; i<dotscount; i++){
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),
                    R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);
            linearLayout.addView(dots[i],params);

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
    }
}
