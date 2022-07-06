package com.example.diabestes_care_app.Alarms;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.diabestes_care_app.R;

public class GridAdapter extends BaseAdapter {
// This Adapter show the tone and Image for each tone for alarm
    private final Context mContext;
    private final String[] web;
    private final int[] Imageid;

    public GridAdapter(Context c, String[] web, int[] Imageid) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
    }


    @Override
    public int getCount() {
        return Imageid.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = inflater.inflate(R.layout.item_layout, null);
            TextView textView = grid.findViewById(R.id.grid_text);
            ImageView imageView = grid.findViewById(R.id.grid_image);
            textView.setText(web[position]);
            imageView.setImageResource(Imageid[position]);
        } else {
            grid = convertView;
        }

        return grid;
    }
}
