package com.example.medication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medication.R;
import com.example.medication.util.TransferActivity;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    protected Button signUpButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        constructor();
    }

    protected void constructor() {

        signUpButton = findViewById(R.id.SignupButton);

        signUpButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.SignupButton)
            TransferActivity.transferActivity(this, SignInActivity.class);

    }

}



