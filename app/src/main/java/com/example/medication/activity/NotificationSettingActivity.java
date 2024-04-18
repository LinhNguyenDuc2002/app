package com.example.medication.activity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.data.NotificationSetting;
import com.example.medication.service.NotificationSettingService;
import com.example.medication.service.ServiceGenerator;

import java.time.LocalTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationSettingActivity extends MainActivity implements CompoundButton.OnCheckedChangeListener {
    private final NotificationSettingService notificationSettingService = ServiceGenerator.createService(NotificationSettingService.class);

    private Button saveButton;
    private Button defaultButton;
    private Switch chip;
    private EditText editTextTime;

    public NotificationSettingActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_setting_activity);

        constructor();
        loadStatus();
    }

    @Override
    public void constructor() {
        super.constructor();

        chip = findViewById(R.id.chip);
        editTextTime = findViewById(R.id.editTextTime);

        saveButton = findViewById(R.id.saveButton);
        defaultButton = findViewById(R.id.defaultButton);

        saveButton.setOnClickListener(this);
        defaultButton.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            editTextTime.setEnabled(true);
        } else {
            editTextTime.setText("00:00");
            editTextTime.setEnabled(true);
        }
    }

    private void loadStatus() {
        notificationSettingService.getSettingStatus(2).enqueue(new Callback<NotificationSetting>() {
            @Override
            public void onResponse(Call<NotificationSetting> call, Response<NotificationSetting> response) {
                if (response.isSuccessful()) {
                    NotificationSetting data = response.body();
                    showData(data);
                } else {
                    System.out.println("error");
                }
            }

            @Override
            public void onFailure(Call<NotificationSetting> call, Throwable t) {
                t.printStackTrace();
                System.err.println("Đã xảy ra lỗi: " + t.getMessage());
            }
        });
    }

    private void showData(NotificationSetting notificationSetting) {
        if(notificationSetting.getStatus()) {
            chip.setChecked(true);
            editTextTime.setText(notificationSetting.getPreNoti());
        }
        else {
            chip.setChecked(false);
            editTextTime.setText("00:00");
            editTextTime.setEnabled(false);
        }
    }
}
