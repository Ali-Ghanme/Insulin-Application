package com.example.diabestes_care_app.Ui.Patient_all.Community;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.diabestes_care_app.R;

import java.util.ArrayList;

public class Food_System_Patient_P extends AppCompatActivity {
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_system_patient_p);
        ArrayList Item = new ArrayList<>();
        Item.add("الفطور");
        Item.add("الغذاء");
        Item.add("العشاء");
        Item.add("الفطور في رمضان ");
        Item.add("السحور في رمضان");
        ArrayAdapter items = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1 ,Item );
        ListView list = (ListView) findViewById(R.id.lv);
        list.setAdapter(items);

    }
}