package com.example.diabestes_care_app.Sing_up_pages;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import com.example.diabestes_care_app.R;

public class Sing_Up_1 extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    // create array of Strings
    String[] types = {"أنثى", "ذكر"};
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up1);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //====================================Define variables===============================
        autoCompleteTextView = findViewById(R.id.autoComplete);
        adapterItems = new ArrayAdapter<>(this, R.layout.type_list_female_male, types);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            String item = parent.getItemAtPosition(position).toString();
            Toast.makeText(getApplicationContext(), "Item: " + item, Toast.LENGTH_SHORT).show();
        });
    }
    //====================================Spinner Method=====================================
//        dotsIndicator = (DotsIndicator) findViewById(R.id.dots_indicator);
//        viewPager = (ViewPager) findViewById(R.id.view_pager);
//        adapter = new ViewPagerAdapter();
//        viewPager.setAdapter(adapter);
//        dotsIndicator.setViewPager(viewPager);

    //====================================Spinner Method=====================================
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), types[position], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}