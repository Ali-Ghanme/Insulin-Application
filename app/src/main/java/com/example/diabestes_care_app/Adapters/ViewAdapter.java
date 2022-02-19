package com.example.diabestes_care_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.diabestes_care_app.Models.ScreenItem;
import com.example.diabestes_care_app.R;

import java.util.List;

public class ViewAdapter extends PagerAdapter {
    Context context;
    List<ScreenItem> mListScreen;

    public ViewAdapter(Context mContext, List<ScreenItem> mListScreen) {
        this.context = mContext;
        this.mListScreen = mListScreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item, null);

        ImageView imageView = view.findViewById(R.id.image_view);
        imageView.setImageResource(mListScreen.get(position).getScreenImg());

        TextView title_S = view.findViewById(R.id.title_splash_2);
        title_S.setText(mListScreen.get(position).getTitle());

        TextView description = view.findViewById(R.id.description_splash_2);
        description.setText(mListScreen.get(position).getDescription());

        container.addView(view);
        return view;

    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
