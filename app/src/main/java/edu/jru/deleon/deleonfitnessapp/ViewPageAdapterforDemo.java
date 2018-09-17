package edu.jru.deleon.deleonfitnessapp;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by Dell pc on 8/5/2017.
 */

public class ViewPageAdapterforDemo extends PagerAdapter{
    private Context context;
    private LayoutInflater layoutInflater;
    private int []images = {R.drawable.warmupplan,R.drawable.workoutplan,R.drawable.workoutplan1,
            R.drawable.workoutplan2};

    public ViewPageAdapterforDemo(Context context) {
        this.context = context;
    }

    @Override
    public int getCount(){
        return images.length;
    }
    @Override
    public boolean isViewFromObject(View view, Object object){
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.customfordemo,null);

        ImageView imageView = (ImageView)view.findViewById(R.id.imgPlan);
        imageView.setImageResource(images[position]);

        ViewPager vp = (ViewPager) container;
        vp.addView(view,0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {


        ViewPager vp=(ViewPager)container;
        View view = (View)object;
        vp.removeView(view);


    }
}

