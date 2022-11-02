package com.example.diabestes_care_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.diabestes_care_app.Models.SelfCare_Model;
import com.example.diabestes_care_app.R;

import java.util.List;

public class ViewAdapter_Content extends PagerAdapter {

    Context context;
    List<SelfCare_Model> mListScreen;

    public ViewAdapter_Content(Context mContext, List<SelfCare_Model> mListScreen) {
        this.context = mContext;
        this.mListScreen = mListScreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.content_item_layout, null);

//        ImageView imageView = view.findViewById(R.id.Image_Content);
//        imageView.setImageResource(mListScreen.get(position).getCont_Image());

        TextView description = view.findViewById(R.id.Text_Content);
        description.setText(mListScreen.get(position).getContent());

        TextView title = view.findViewById(R.id.Content_title);
        title.setText(mListScreen.get(position).getTitle());
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
