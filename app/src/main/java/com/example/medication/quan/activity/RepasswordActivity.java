package com.example.medication.quan.activity;

import android.os.Bundle;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;

public class RepasswordActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repassword_activity);

        constructor();
    }
}
