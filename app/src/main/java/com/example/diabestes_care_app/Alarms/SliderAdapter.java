package com.example.diabestes_care_app.Alarms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.diabestes_care_app.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {

        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int resId;
        //View view = layoutInflater.inflate(R.layout.slide_layout, container, false);
        //additional layouts must be (RelativeLayout)
        switch (position) {
            //view = layoutInflater.inflate(R.layout.slide_layout, container, false);
            case 1:
                resId = R.layout.alarm_layout;
                //view = layoutInflater.inflate(R.layout.clock_layout, container, false);
                break;
            default:
                resId = R.layout.clock_layout;
                //view = layoutInflater.inflate(R.layout.alarm_layout, container, false);
                break;



        }
        View view = layoutInflater.inflate(resId, null);
        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((RelativeLayout) object);
    }
}
