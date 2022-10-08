package com.example.diabestes_care_app.Ui.Patient_all.Sections.Reports;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.diabestes_care_app.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Graphs_Fragment extends Fragment {

    // button week and day
    Button week, day;
    GraphView graph;
    // String friends;
    int sugarInt, sugerindex0, sugerindex1, sugerindex2, sugerindex3, sugerindex4;
    // Firebase
    DatabaseReference databaseReference;
    String PatientUsername;
    public static final String MyPREFERENCES_P = "P_Username";
    private Dialog dialog;


    // رسوم بيانية
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graphs_, container, false);

        graph = view.findViewById(R.id.graphview);
        //============================Get Patient Username===========================================
        SharedPreferences prefs = this.requireActivity().getSharedPreferences(MyPREFERENCES_P, MODE_PRIVATE);
        PatientUsername = prefs.getString("TAG_NAME", null);

        //=============================Dialog=======================================================
        //============================Create + Configure the Dialog here============================
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dilog);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dilog_background));
        //Setting the animations to dialog
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        Button Okay = dialog.findViewById(R.id.btn_okay);

        Okay.setOnClickListener(v -> {

            Toast.makeText(getContext(), "Okay", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });

        //============================Configure Firebase============================================
        databaseReference = FirebaseDatabase.getInstance().getReference("patient").child(PatientUsername).child("Reports_info").child("فحص يومي");
        List<String> friends = new ArrayList<>();
        Query query = databaseReference.orderByKey();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    try {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String friend = Objects.requireNonNull(snapshot.child("نسبة السكر في الدم").getValue()).toString();

                            String firstElement0 = friends.get(0);
                            String firstElement1 = friends.get(1);
                            String firstElement2 = friends.get(2);
                            String firstElement3 = friends.get(3);
                            String firstElement4 = friends.get(4);

                            sugerindex0 = Integer.parseInt(firstElement0);
                            sugerindex1 = Integer.parseInt(firstElement1);
                            sugerindex2 = Integer.parseInt(firstElement2);
                            sugerindex3 = Integer.parseInt(firstElement3);
                            sugerindex4 = Integer.parseInt(firstElement4);
                            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

                            series.appendData(new DataPoint(1, sugerindex0), true, 5);
                            series.appendData(new DataPoint(2, sugerindex1), true, 5);
                            series.appendData(new DataPoint(3, sugerindex2), true, 5);
                            series.appendData(new DataPoint(4, sugerindex3), true, 5);
                            series.appendData(new DataPoint(5, sugerindex4), true, 5);

                            series.setColor(Color.BLUE);
                            series.setTitle("نسبة السكر");
                            series.setDrawDataPoints(true);
                            series.setDataPointsRadius(20);
                            series.setThickness(3);
                            graph.addSeries(series);
                            //تسمية المحاور

                            graph.getViewport().setYAxisBoundsManual(false);
                            graph.getViewport().setMinY(20);
                            graph.getViewport().setMaxY(500);

                            friends.add(friend);

                        }
                    } catch (Exception e) {
                        Log.e("TAG", e.getMessage());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TAG", error.getMessage());
            }
        });
        return view;
    }
}
//تفاصيل عن تسمية النقطة
// series.setColor(Color.rgb(0, 80, 100));
//                series.setColor(Color.RED);
//                series.setTitle("نسبة السكر");

//                series.setColor(Color.RED);
//                series.setTitle("نسبة السكر");
//                series.setDrawDataPoints(true);
//                series.setDataPointsRadius(15);
//                series.setThickness(2);
//                graph.addSeries(series);
//                //تسمية المحاور
//
////                graph.getViewport().setYAxisBoundsManual(true);
////                graph.getViewport().setMinY(20);
////                graph.getViewport().setMaxY(500);
//
//                // اضافة النقاط على ال graph
//                graph.addSeries(series);

//عنوان ال graph
//                graph.setTitle("مخطط السكر اليومي");
//                graph.setTitleTextSize(50);
//                graph.setTitleColor(Color.RED);
//اتجه مسمى النقطة على اي مكان
//                graph.getLegendRenderer().setVisible(true);
//                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);

//                //تسمية المحاور
//                GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
//                //   gridLabel.setHorizontalAxisTitle("X وقت الجرعات في اليوم  ");
//                gridLabel.setVerticalAxisTitle("Y نسبة السكر في كل وقت  ");

//
//                // activate horizontal zooming and scrolling
//                graph.getViewport().setScalable(false);
//                // activate horizontal scrolling
//                graph.getViewport().setScrollable(false);
//                // activate horizontal and vertical zooming and scrolling
//                graph.getViewport().setScalableY(false);
//                // activate vertical scrolling
//                graph.getViewport().setScrollableY(false);


//                // set manual X bounds
//                graph.getViewport().setXAxisBoundsManual(false);
//                graph.getViewport().setMinX(0);
//                graph.getViewport().setMaxX(5);
// set manual Y bounds
//                graph.getViewport().setYAxisBoundsManual(true);
//                graph.getViewport().setMinY(30);
//                graph.getViewport().setMaxY(500);
// Hallow this is Update