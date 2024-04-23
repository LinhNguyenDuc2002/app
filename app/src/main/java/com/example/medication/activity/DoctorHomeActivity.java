package com.example.medication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;

public class DoctorHomeActivity extends MainActivity {

    private Button doctor_pre,doctor_new_prescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_home_activity);
        doctor_pre = findViewById(R.id.doctor_prescription);
        doctor_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorHomeActivity.this,DoctorPrescriptionActivity.class);
                startActivity(intent);
            }
        });
        doctor_new_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}