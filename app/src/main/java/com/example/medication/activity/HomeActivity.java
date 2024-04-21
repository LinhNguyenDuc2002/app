package com.example.medication.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.data.DTO.PrescribedMedDto;
import com.example.medication.service.DailyStatusService;
import com.example.medication.service.PrescribedMedicationService;
import com.example.medication.service.ServiceGenerator;
import com.example.medication.util.TransferActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import static com.example.medication.vinhquang.util.FirebaseUtil.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends MainActivity {
    private final PrescribedMedicationService prescribedMedicationService = ServiceGenerator.createService(PrescribedMedicationService.class);
    private final DailyStatusService dailyStatusService = ServiceGenerator.createService(DailyStatusService.class);
    private LinearLayout listMedicationLayout;

    private Button statisticButton;
    private Button healthButton;
    private Button prescriptionButton;
    private Button scheduleButton;
    private FloatingActionButton addMemberButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        constructor();
        loadMedication();
        getToken();
        setTokenToUser();
        getListOldNoti();
    }

    @Override
    public void constructor() {
        super.constructor();

        listMedicationLayout = findViewById(R.id.listMedicationLayout);

        statisticButton = findViewById(R.id.statisticButton);
        healthButton = findViewById(R.id.healthButton);
        prescriptionButton = findViewById(R.id.prescriptionButton);
        scheduleButton = findViewById(R.id.scheduleButton);
        addMemberButton = findViewById(R.id.addMemberButton);

        statisticButton.setOnClickListener(this);
        healthButton.setOnClickListener(this);
        scheduleButton.setOnClickListener(this);
        prescriptionButton.setOnClickListener(this);
        addMemberButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        int id = v.getId();

        if (id == R.id.statisticButton)
            TransferActivity.transferActivity(this, StatisticActivity.class);
        else if (id == R.id.healthButton)
            TransferActivity.transferActivity(this, HealthAssessmentActivity.class);
        else if (id == R.id.addMemberButton)
            TransferActivity.transferActivity(this, AddMemberActivity.class);
        else if (id == R.id.prescriptionButton)
            TransferActivity.transferActivity(this, PrescriptionActivity.class);
    }

    private void loadMedication() {
        prescribedMedicationService.getDailyMedication(1).enqueue(new Callback<List<PrescribedMedDto>>() {
            @Override
            public void onResponse(Call<List<PrescribedMedDto>> call, Response<List<PrescribedMedDto>> response) {
                if (response.isSuccessful()) {
                    List<PrescribedMedDto> data = response.body();
                    showMedication(data);
                } else {
                    System.out.println("error");
                }
            }

            @Override
            public void onFailure(Call<List<PrescribedMedDto>> call, Throwable t) {
                t.printStackTrace();
                System.err.println("Đã xảy ra lỗi: " + t.getMessage());
            }
        });
    }

    private void showMedication(List<PrescribedMedDto> data) {
        TableRow.LayoutParams linearLayout = new TableRow.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                150
        );
        linearLayout.setMargins(0, 0, 0, 10);

        TableRow.LayoutParams textViewLayout = new TableRow.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                2
        );

        for(PrescribedMedDto item : data) {
            TableRow tableRow = new TableRow(listMedicationLayout.getContext());
            tableRow.setLayoutParams(linearLayout);
            tableRow.setBackground(new ColorDrawable(getResources().getColor(R.color.white)));

            TextView textViewName = new TextView(tableRow.getContext());
            textViewName.setLayoutParams(new TableRow.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    5
            ));
            textViewName.setText(item.getName());
            textViewName.setTextSize(18);
            textViewName.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            textViewName.setPadding(5, 0, 0, 0);

            TextView textViewQuantity = new TextView(tableRow.getContext());
            textViewQuantity.setLayoutParams(textViewLayout);
            textViewQuantity.setText(item.getQuantity() + " " + item.getUnit());
            textViewQuantity.setTextSize(18);
            textViewQuantity.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

            TextView textViewDate = new TextView(tableRow.getContext());
            textViewDate.setLayoutParams(textViewLayout);
            textViewDate.setText(item.getTime());
            textViewDate.setTextSize(14);
            textViewDate.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

            CheckBox checkBox = new CheckBox(tableRow.getContext());
            checkBox.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            ));
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Xử lý sự kiện khi checkbox được chọn
                    if (isChecked) {
                        System.out.println(item.getId());
                        dailyStatusService.updateDailyStatus(item, 1, 1).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                System.out.println("ok");
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.out.println("not ok");
                            }
                        });
                        // Đổi màu của textViewName thành đỏ khi checkbox được chọn
                        textViewName.setTextColor(getResources().getColor(R.color.red_color));
                    } else {
                        dailyStatusService.updateDailyStatus(item, 1, 0);
                        // Đổi màu của textViewName về màu mặc định hoặc một màu khác khi checkbox không được chọn
                        textViewName.setTextColor(getResources().getColor(R.color.black));
                    }
                }
            });


            listMedicationLayout.addView(tableRow);
            tableRow.addView(textViewName);
            tableRow.addView(textViewQuantity);
            tableRow.addView(textViewDate);
            tableRow.addView(checkBox);
        }
    }


}
