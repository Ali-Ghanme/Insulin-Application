package com.example.diabestes_care_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.diabestes_care_app.Models.Splash_Screen_Modal;
import com.example.diabestes_care_app.R;

import java.util.List;

public class ViewAdapter_Splash_Screen extends PagerAdapter {
    Context context;
    List<Splash_Screen_Modal> mListScreen;

    public ViewAdapter_Splash_Screen(Context mContext, List<Splash_Screen_Modal> mListScreen) {
        this.context = mContext;
        this.mListScreen = mListScreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_splash_screen_item, null);

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
