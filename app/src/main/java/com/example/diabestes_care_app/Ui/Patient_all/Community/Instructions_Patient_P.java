package com.example.diabestes_care_app.Ui.Patient_all.Community;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.diabestes_care_app.R;

import java.util.ArrayList;

public class Instructions_Patient_P extends AppCompatActivity {

ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions_patient_p);

        ArrayList Item = new ArrayList<>();
        Item.add("عن مرض السكري");
        Item.add("أنواع مرض السكري ");
        Item.add("مضاعفات مرض السكري");
        Item.add("ما هي غيبوبة السكر");
        Item.add("التعامل مع حالات اغماء السكري");
        Item.add("جرعات الانسولين وكيفية اخدها");
        Item.add("اعراض مبكرة لمرض السكري");
        ArrayAdapter items = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1 ,Item );
        ListView list = (ListView) findViewById(R.id.lv);
        list.setAdapter(items);




    }
}