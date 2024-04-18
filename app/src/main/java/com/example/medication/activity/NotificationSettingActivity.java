package com.example.medication.activity;

import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.data.NotificationSetting;
import com.example.medication.service.NotificationSettingService;
import com.example.medication.service.ServiceGenerator;
import com.example.medication.util.DateUtil;

import java.time.LocalTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationSettingActivity extends MainActivity {
    private final NotificationSettingService notificationSettingService = ServiceGenerator.createService(NotificationSettingService.class);

    private Button saveButton;
    private Button defaultButton;
    private Switch turnChip;
    private EditText editTextTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadStatus();
        setContentView(R.layout.notification_setting_activity);

        constructor();
    }

    @Override
    public void constructor() {
        super.constructor();

        turnChip.findViewById(R.id.turnChip);
        editTextTime.findViewById(R.id.editTextTime);

        saveButton.findViewById(R.id.saveButton);
        defaultButton.findViewById(R.id.defaultButton);

        saveButton.setOnClickListener(this);
        defaultButton.setOnClickListener(this);
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
            turnChip.getCompoundPaddingRight();
            editTextTime.setText(DateUtil.transferToString(notificationSetting.getPreNoti()));
        }
        else {
            turnChip.getCompoundPaddingLeft();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                editTextTime.setText(DateUtil.transferToString(LocalTime.of(0, 0)));
            }
            editTextTime.setEnabled(false);
        }
    }
}
