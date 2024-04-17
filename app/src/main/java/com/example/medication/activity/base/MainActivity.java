package com.example.medication.activity.base;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medication.R;
import com.example.medication.activity.HomeActivity;
import com.example.medication.activity.UserActivity;
import com.example.medication.activity.NotificationActivity;
import com.example.medication.activity.NotificationSettingActivity;
import com.example.medication.util.TransferActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    protected Button bellButton;

    protected Button setupButton;

    protected Button prescriptionButton;

    protected Button homeButton;

    protected Button chatButton;

    protected Button userButton;

    protected void constructor() {
        bellButton = findViewById(R.id.bellButton);
        setupButton = findViewById(R.id.setupButton);
        prescriptionButton = findViewById(R.id.prescriptionButton);
        homeButton = findViewById(R.id.homeButton);
        chatButton = findViewById(R.id.chatButton);
        userButton = findViewById(R.id.userButton);

        bellButton.setOnClickListener(this);
        setupButton.setOnClickListener(this);
        prescriptionButton.setOnClickListener(this);
        homeButton.setOnClickListener(this);
        chatButton.setOnClickListener(this);
        userButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.bellButton)
            TransferActivity.transferActivity(this, NotificationActivity.class);
        else if(id == R.id.setupButton)
            TransferActivity.transferActivity(this, NotificationSettingActivity.class);
        else if(id == R.id.homeButton)
            TransferActivity.transferActivity(this, HomeActivity.class);
        else if(id == R.id.userButton)
            TransferActivity.transferActivity(this, UserActivity.class);
    }
}
