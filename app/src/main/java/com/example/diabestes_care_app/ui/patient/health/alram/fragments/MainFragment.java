package com.example.diabestes_care_app.ui.patient.health.alram.fragments;

import static com.example.diabestes_care_app.ui.patient.health.alram.screens.AddEditAlarmActivity.ADD_ALARM;
import static com.example.diabestes_care_app.ui.patient.health.alram.screens.AddEditAlarmActivity.buildAddEditAlarmActivityIntent;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.diabestes_care_app.R;
import com.example.diabestes_care_app.ui.patient.components.adapters.AlarmsAdapter;
import com.example.diabestes_care_app.data.model.Alarm_Model;
import com.example.diabestes_care_app.receiver.LoadAlarmsReceiver;
import com.example.diabestes_care_app.services.LoadAlarmsService;
import com.example.diabestes_care_app.utils.AlarmUtils;
import com.example.diabestes_care_app.utils.DividerItemDecoration;
import com.example.diabestes_care_app.ui.patient.components.recycler_view.EmptyRecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public final class MainFragment extends Fragment
        implements LoadAlarmsReceiver.OnAlarmsLoadedListener {

    private LoadAlarmsReceiver mReceiver;
    private AlarmsAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReceiver = new LoadAlarmsReceiver(this);
    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.p_fragment_dose, container, false);
        final EmptyRecyclerView rv = v.findViewById(R.id.recycler);

        mAdapter = new AlarmsAdapter();
        rv.setEmptyView(v.findViewById(R.id.empty_view));
        rv.setAdapter(mAdapter);
        rv.addItemDecoration(new DividerItemDecoration(getContext()));
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setItemAnimator(new DefaultItemAnimator());

        final FloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            AlarmUtils.checkAlarmPermissions(getActivity());
            final Intent i = buildAddEditAlarmActivityIntent(getContext(), ADD_ALARM);
            startActivity(i);
        });
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        final IntentFilter filter = new IntentFilter(LoadAlarmsService.ACTION_COMPLETE);
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(mReceiver, filter);
        LoadAlarmsService.launchLoadAlarmsService(getContext());
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(mReceiver);
    }

    @Override
    public void onAlarmsLoaded(ArrayList<Alarm_Model> alarms) {
        mAdapter.setAlarms(alarms);
    }

}
