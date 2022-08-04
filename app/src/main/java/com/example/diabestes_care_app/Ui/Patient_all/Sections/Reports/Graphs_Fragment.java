package com.example.diabestes_care_app.Ui.Patient_all.Sections.Reports;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diabestes_care_app.Adapters.Reports_Adapter;
import com.example.diabestes_care_app.Models.Reports_Model;
import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;
import static com.example.diabestes_care_app.Ui.Sing_In.Fragment.LogIn_Patient_Fragment.MyPREFERENCES_P;


public class Graphs_Fragment extends Fragment {
    TextView mohammed; // example
    // button week and day
    Button week, day;
    GraphView graph;
    String suger;
    int   suger_string ;
    // Firebase
    DatabaseReference databaseReference;
    String PatientUsername;
    public static final String MyPREFERENCES_P = "P_Username";
    public int dataa1, dataa2;

    // رسوم بيانية
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graphs_, container, false);

        graph = view.findViewById(R.id.graphview);
        //============================Get Patient Username===========================================
        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);
        //============================Configure Firebase============================================
        databaseReference = FirebaseDatabase.getInstance().getReference("patient").child(PatientUsername).child("Reports_info").child("فحص يومي");

        Query query = databaseReference.orderByKey();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                           suger = snapshot.child("نسبة السكر في الدم").getValue().toString();

                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), ".00.", Toast.LENGTH_SHORT).show();

                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", error.getMessage());
            }
        });

//        dataa1 = Integer.parseInt(Bold);
//        dataa2 = Integer.parseInt(Time);

/*
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                blood = snapshot.child("patient").child(P_Username).child("Reports_info").child("فحص يومي").child("نسبة السكر في الدم").getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        int[] Data = {dataa2, dataa2};

        //
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
//        for (int i=0;i<90;i++) {
//
//            series.appendData(new DataPoint(i, 20), false, 90);
//        }
        series.appendData(new DataPoint(1, 70), false, 5 );
        series.appendData(new DataPoint(2, 50), false, 5);
        series.appendData(new DataPoint(3, 80), false, 5);
        series.appendData(new DataPoint(4, 120), false, 5);
        series.appendData(new DataPoint(5, 200), false, 5);
        series.appendData(new DataPoint(6, 10), false, 5);

//        series.appendData(new DataPoint(20, 60), true, 20);
//        series.appendData(new DataPoint(40, 40), true, 20);
//        series.appendData(new DataPoint(44, 55), true, 20);
//        series.appendData(new DataPoint(2, 5), true, 20);

        //  new DataPoint(50  , 60);


        //تفاصيل عن تسمية النقطة
       // series.setColor(Color.rgb(0, 80, 100));
        series.setColor(Color.RED);
        series.setTitle("نسبة السكر");
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(50);
        series.setThickness(8);

        // اضافة النقاط على ال graph
        graph.addSeries(series);

        //عنوان ال graph
        graph.setTitle("مخطط السكر اليومي");
        graph.setTitleTextSize(50);
        graph.setTitleColor(Color.RED);
        //اتجه مسمى النقطة على اي مكان
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);

        //تسمية المحاور
        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
     //   gridLabel.setHorizontalAxisTitle("X وقت الجرعات في اليوم  ");
        gridLabel.setVerticalAxisTitle("Y نسبة السكر في كل وقت  ");


        // activate horizontal zooming and scrolling
        graph.getViewport().setScalable(false);
        // activate horizontal scrolling
        graph.getViewport().setScrollable(false);
        // activate horizontal and vertical zooming and scrolling
        graph.getViewport().setScalableY(false);
        // activate vertical scrolling
        graph.getViewport().setScrollableY(false);


        // set manual X bounds
//        graph.getViewport().setXAxisBoundsManual(false);
//        graph.getViewport().setMinX(0);
//        graph.getViewport().setMaxX(5);
        // set manual Y bounds
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(250);


 */


       // Date currentTime = Calendar.getInstance().getTime();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        series.appendData(new DataPoint(0, 50), true, 5);
        series.appendData(new DataPoint(1, 150), true, 5);
        series.appendData(new DataPoint(2, 120), true, 5);
        series.appendData(new DataPoint(3, 200), true, 5);
        series.appendData(new DataPoint(4, 100), true, 5);
//
//           if ((suger_string = Integer.parseInt(suger)) != 0) {
//            for (int i = 0; i < 5; i++) {
//
//            }
//         }else {
//            Toast.makeText(getActivity(), "لا توجد نتيجة", Toast.LENGTH_SHORT).show();
//
//        }




         //تفاصيل عن تسمية النقطة
        // series.setColor(Color.rgb(0, 80, 100));
        series.setColor(Color.RED);
        series.setTitle("نسبة السكر");
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(15);
        series.setThickness(2);
        graph.addSeries(series);
        //تسمية المحاور

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(50);
        graph.getViewport().setMaxY(250);
        return view;
    }


}