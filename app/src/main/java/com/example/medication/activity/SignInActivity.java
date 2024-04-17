package com.example.medication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medication.R;
import com.example.medication.util.TransferActivity;


public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    protected Button signInButton;
    protected TextView forgotPass;
    protected TextView signUp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);

        constructor();
    }

    protected void constructor() {

        signInButton = findViewById(R.id.SignupButton);
        forgotPass = findViewById(R.id.SignIn);
        signUp = findViewById(R.id.SignUp);

        signInButton.setOnClickListener(this);
        forgotPass.setOnClickListener(this);
        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.SignupButton)
            TransferActivity.transferActivity(this, HomeActivity.class);
        else if (id == R.id.SignIn)
            TransferActivity.transferActivity(this, ForgotPassActivity.class);
        else if (id == R.id.SignUp)
            TransferActivity.transferActivity(this, SignUpActivity.class);
    }
}



