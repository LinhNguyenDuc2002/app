package com.example.medication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.util.TransferActivity;

public class UserActivity extends MainActivity {
    private TextView repasswordButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateinfor_activity);

        constructor();
    }

    public void constructor() {
        super.constructor();

        repasswordButton = findViewById(R.id.repasswordButton);
        logoutButton = findViewById(R.id.LogoutButton);

        repasswordButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
    }
    public void onClick(View v) {
        super.onClick(v);

        int id = v.getId();

        if (id == R.id.repasswordButton)
            TransferActivity.transferActivity(this, RepasswordActivity.class);
        else if (id == R.id.LogoutButton)
            TransferActivity.transferActivity(this, SignInActivity.class);
    }
}


