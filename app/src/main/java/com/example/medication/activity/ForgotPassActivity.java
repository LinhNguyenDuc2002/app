package com.example.medication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medication.R;
import com.example.medication.util.TransferActivity;


public class ForgotPassActivity extends AppCompatActivity implements View.OnClickListener {

    protected TextView signUp;

    protected TextView signIn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_activity);

        constructor();
    }

    protected void constructor() {

        signIn = findViewById(R.id.SignIn);

        signUp = findViewById(R.id.SignUp);

        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.SignIn)
            TransferActivity.transferActivity(this, SignInActivity.class);
        else if (id == R.id.SignUp)
            TransferActivity.transferActivity(this, SignUpActivity.class);
    }
}



