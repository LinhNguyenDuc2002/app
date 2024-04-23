package com.example.medication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import static com.example.medication.util.TransferActivity.transferActivityWithId;
import static com.example.medication.vinhquang.util.DateTimeUtil.dateTimeToString;
import static com.example.medication.vinhquang.util.FirebaseUtil.getListOldNoti;
import static com.example.medication.vinhquang.util.FirebaseUtil.getToken;
import static com.example.medication.vinhquang.util.FirebaseUtil.setTokenToUser;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.service.ServiceGenerator;
import com.example.medication.util.TransferActivity;
import com.example.medication.vinhquang.activity.AppointmentActivity;
import com.example.medication.vinhquang.activity.DetailAppActivity;
import com.example.medication.vinhquang.api.ApiService;
import com.example.medication.vinhquang.data.AppointmentResponse;
import com.example.medication.vinhquang.util.GlobalValues;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorHomeActivity extends MainActivity {
    GlobalValues globalValues = GlobalValues.getInstance();
    private final ApiService api = ServiceGenerator.createService(ApiService.class);
    List<AppointmentResponse> data;
    LinearLayout rootLayout;
    private Button preBtn;
    private Button appBtn;

    private Button doctor_pre,doctor_new_prescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_home_activity);

        doctor_pre = findViewById(R.id.preBtn);
        doctor_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorHomeActivity.this,DoctorPrescriptionActivity.class);
                startActivity(intent);
            }
        });

        constructor();
    }

    @Override
    protected void constructor() {
        super.constructor();

        appBtn = findViewById(R.id.appBtn);

        appBtn.setOnClickListener(this);

        rootLayout = findViewById(R.id.noti);
//        getToken();
        getAllApps();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        int id = v.getId();

        if (id == R.id.appBtn)
            TransferActivity.transferActivity(this, AppointmentActivity.class);
    }

    public void getAllApps() {
        api.getAllAppToday(globalValues.getUserId()).enqueue(new Callback<List<AppointmentResponse>>() {

            @Override
            public void onResponse(Call<List<AppointmentResponse>> call, Response<List<AppointmentResponse>> response) {
                if (response.isSuccessful()) {
                    data = response.body();
                    System.out.println("vinhquang .... " + data.size());

                    data.forEach(a -> {
                        render(a);
                    });
                } else {
                    System.out.println("error get old");
                }
            }

            @Override
            public void onFailure(Call<List<AppointmentResponse>> call, Throwable t) {
                t.printStackTrace();
                System.err.println("Đã xảy ra lỗi: " + t.getMessage());
            }

        });
    }

    public void render(AppointmentResponse a) {
        LinearLayout parentLayout = new LinearLayout(this);
        parentLayout.setTag(R.id.id, a.getId());
        parentLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        parentLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout userLayout = new LinearLayout(this);
        userLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        userLayout.setOrientation(LinearLayout.HORIZONTAL);
        userLayout.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout imageLayout = new LinearLayout(this);
        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(
                200,
                200);
        imageLayoutParams.setMargins(30, 30, 30, 30);
        imageLayout.setLayoutParams(imageLayoutParams);
        imageLayout.setOrientation(LinearLayout.HORIZONTAL);

        Button userButton = new Button(this);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        userButton.setLayoutParams(buttonParams);
        userButton.setBackgroundResource(R.color.transparent);
        userButton.setForeground(getDrawable(R.drawable.user));

        imageLayout.addView(userButton);

        LinearLayout infoLayout = new LinearLayout(this);
        infoLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        infoLayout.setOrientation(LinearLayout.VERTICAL);

        TextView nameTextView = new TextView(this);
        nameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        nameTextView.setTypeface(null, Typeface.BOLD);
        String fullName = a.getPatient().getFullName();
        nameTextView.setText(fullName);
        nameTextView.setTextColor(Color.parseColor("#000000"));

        TextView timeTextView = new TextView(this);
        timeTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        timeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        timeTextView.setTypeface(null, Typeface.ITALIC);
        timeTextView.setText(dateTimeToString(a.getDateTime()));
        timeTextView.setTextColor(Color.parseColor("#000000"));

        infoLayout.addView(nameTextView);
        infoLayout.addView(timeTextView);

        userLayout.addView(imageLayout);
        userLayout.addView(infoLayout);

        parentLayout.addView(userLayout);

        View lineView = new View(this);
        lineView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,10));
        lineView.setBackgroundColor(Color.parseColor("#00b38f"));

        parentLayout.addView(lineView);

        rootLayout.addView(parentLayout);


        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idValue = (int) v.getTag(R.id.id);
                transferActivityWithId(DoctorHomeActivity.this, DetailAppActivity.class, idValue);
            }
        });
    }


}


















