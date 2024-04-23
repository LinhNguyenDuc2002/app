package com.example.medication.quan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medication.R;
import com.example.medication.activity.DoctorHomeActivity;
import com.example.medication.activity.HomeActivity;
import com.example.medication.quan.request.UserLoginRequest;
import com.example.medication.quan.respone.UserLoginRespone;
import com.example.medication.quan.service.UserService;
import com.example.medication.service.ServiceGenerator;
import com.example.medication.util.TransferActivity;
import com.example.medication.vinhquang.util.GlobalValues;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private final UserService userService = ServiceGenerator.createService(UserService.class);
    protected Button SigninButton;
    protected Button ForgotpassButton;
    protected Button SignupButton;
    protected TextInputEditText username;
    protected TextInputEditText password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);

        constructor();
    }

    protected void constructor() {

        SigninButton = findViewById(R.id.SigninButton);
        ForgotpassButton = findViewById(R.id.ForgotpassButton);
        SignupButton = findViewById(R.id.SignupButton);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        SigninButton.setOnClickListener(this);
        ForgotpassButton.setOnClickListener(this);
        SignupButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.SigninButton)
            postLogin();
//            TransferActivity.transferActivity(this, HomeActivity.class);
        else if (id == R.id.ForgotpassButton)
            TransferActivity.transferActivity(this, ForgotPassActivity.class);
        else if (id == R.id.SignupButton)
            TransferActivity.transferActivity(this, SignUpActivity.class);
    }

    private UserLoginRequest packData(){
        String Username = username.getText().toString();
        String Password = password.getText().toString();

        // Tạo đối tượng UserLoginRequest và đặt giá trị cho các trường
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUsername(Username);
        userLoginRequest.setPassword(Password);

        System.out.println(Username);
        System.out.println(Password);
        return userLoginRequest;
    }

    private void postLogin() {
        System.out.println("login...");
        userService.postLoginForm(packData()).enqueue(new Callback<UserLoginRespone>() {
            @Override
            public void onResponse(Call<UserLoginRespone> call, Response<UserLoginRespone> response) {
                if (response.isSuccessful()) {
                    UserLoginRespone data = response.body();
                    saveCurrentUser(data);
                    returnToHome(data.getRole());
                    System.out.println(data.getId());
                    System.out.println(data.getRole());
                    System.out.println("xin chao " + data.getFullName());

                } else {
                    System.out.println("error");
                }
            }

            @Override
            public void onFailure(Call<UserLoginRespone> call, Throwable t) {
                t.printStackTrace();
                System.err.println("Đã xảy ra lỗi: " + t.getMessage());
                System.out.println("dang nhap that bai...");
            }
        });
    }

    private void saveCurrentUser(UserLoginRespone userLoginRespone) {
        GlobalValues globalValues = GlobalValues.getInstance();

        globalValues.setUserId(userLoginRespone.getId());
        globalValues.setRole(userLoginRespone.getRole());
        globalValues.setFullName(userLoginRespone.getFullName());
    }
    public void returnToHome(Integer role){
        if(role == 1) {
            TransferActivity.transferActivity(this, HomeActivity.class);
        }
        else {
            TransferActivity.transferActivity(this, DoctorHomeActivity.class);
        }
    }
}



