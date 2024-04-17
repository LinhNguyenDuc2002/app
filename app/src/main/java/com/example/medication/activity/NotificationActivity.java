package com.example.medication.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;

public class NotificationActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);

        constructor();
    }
}
