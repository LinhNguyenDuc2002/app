package com.example.medication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.data.NotificationSetting;
import com.example.medication.service.NotificationSettingService;
import com.example.medication.service.ServiceGenerator;
import com.example.medication.util.DialogUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationSettingActivity extends MainActivity implements CompoundButton.OnCheckedChangeListener {
    private final NotificationSettingService notificationSettingService = ServiceGenerator.createService(NotificationSettingService.class);

    private Button saveButton;
    private Button defaultButton;
    private Switch chip;
    private EditText editTextTime;

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
        editTextTime.setOnClickListener(this);

        saveButton = findViewById(R.id.saveButton);
        defaultButton = findViewById(R.id.defaultButton);

        saveButton.setOnClickListener(this);
        defaultButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        int id = v.getId();

        if (id == R.id.saveButton)
            updateSetting(packData());
        else if(id == R.id.defaultButton)
            updateSetting(new HashMap<>());
        else if (id == R.id.editTextTime)
            DialogUtil.showTimePickerDialog(this, editTextTime);
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

    private void updateSetting(Map<String, Object> parameters) {
        notificationSettingService.updateSetting(2, parameters).enqueue(new Callback<NotificationSetting>() {
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

    private Map<String, Object> packData() {
        Map<String, Object> map = new HashMap<>();

        boolean check = chip.isChecked();
        map.put("status", check);

        if(check) {
            map.put("previous", editTextTime.getText());
        }

        return map;
    }

    private void showData(NotificationSetting notificationSetting) {
        if(notificationSetting.getStatus()) {
            chip.setChecked(true);
            editTextTime.setText(notificationSetting.getPreNoti());
        }
        else {
            chip.setChecked(false);
            editTextTime.setText("00:00");
        }
    }
}
