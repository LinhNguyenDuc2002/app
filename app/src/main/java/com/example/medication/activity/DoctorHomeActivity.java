package com.example.medication.activity;

import android.os.Bundle;
import android.widget.Button;


import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;

public class DoctorHomeActivity extends MainActivity {

    private Button doctor_pre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_home_activity);
        doctor_pre = findViewById(R.id.doctor_prescription);

    }
}