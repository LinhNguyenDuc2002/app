package com.example.medication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.data.DrinkingNotification;
import com.example.medication.service.ServiceGenerator;
import com.example.medication.vinhquang.api.ApiService;
import com.example.medication.vinhquang.data.NotificationFirebase;
import com.example.medication.vinhquang.util.GlobalValues;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailNotificationActivity extends MainActivity {
    private final ApiService api = ServiceGenerator.createService(ApiService.class);
    GlobalValues globalValues = GlobalValues.getInstance();
    private final ApiService apiService = ServiceGenerator.createService(ApiService.class);

    private Button backNoti;
    private Button helpButton;

    private TextView messageTextView;
    private TextView currentTime;
    private TextView medicineName;
    private TextView prescriptionTime;
    private TextView medicineQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_notification_activity);

        constructor();
        loadData();
    }

    @Override
    public void constructor() {
        super.constructor();

        backNoti = findViewById(R.id.backNoti);
        helpButton = findViewById(R.id.helpButton);

        messageTextView = findViewById(R.id.messageTextView);
        currentTime = findViewById(R.id.currentTime);
        medicineName = findViewById(R.id.medicineName);
        prescriptionTime = findViewById(R.id.prescriptionTime);
        medicineQuantity = findViewById(R.id.medicineQuantity);

        backNoti.setOnClickListener(this);
        helpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        int id = v.getId();

        if (id == R.id.backNoti)
            finish();
    }

    private void loadData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Integer id = extras.getInt("id");
            seen(id);
            apiService.getDrinkingNoti(id).enqueue(new Callback<DrinkingNotification>() {
                @Override
                public void onResponse(Call<DrinkingNotification> call, Response<DrinkingNotification> response) {
                    if (response.isSuccessful()) {
                        DrinkingNotification data = response.body();
                        showData(data);
                    } else {
                        System.out.println("error");
                    }
                }

                @Override
                public void onFailure(Call<DrinkingNotification> call, Throwable t) {
                    t.printStackTrace();
                    System.err.println("Đã xảy ra lỗi: " + t.getMessage());
                }
            });

        }
    }

    private void showData(DrinkingNotification data) {
        messageTextView.setText("Bệnh nhân " + data.getPatient().getFullName() + " có lịch thuốc thuốc lúc " + data.getPrescribedMed().getTime());
        currentTime.setText(data.getTime() + ", " + data.getDate());
        medicineName.setText(data.getPrescribedMed().getName());
        prescriptionTime.setText(data.getPrescribedMed().getTime() + ", " + data.getDate());
        medicineQuantity.setText(data.getPrescribedMed().getQuantity() + " " + data.getPrescribedMed().getUnit());
    }

    public void seen(Integer id) {
        api.update(id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String data = response.body();
                    System.out.println("vinhquang .... " + data);
                    List<NotificationFirebase> list = globalValues.getNotificationList();
                    list.forEach(n -> {
                        if(n.getId() == id) {
                            n.setStatus(1);
                        }
                    });
                    globalValues.setNotificationList(list);
                } else {
                    System.out.println("error get old");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                System.err.println("Đã xảy ra lỗi: " + t.getMessage());
            }
        });
    }
}
