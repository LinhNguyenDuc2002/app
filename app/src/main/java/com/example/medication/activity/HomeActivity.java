package com.example.medication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.data.DTO.PrescribedMedDto;
import com.example.medication.service.PrescribedMedicationService;
import com.example.medication.service.ServiceGenerator;
import com.example.medication.util.TransferActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends MainActivity {
    private final PrescribedMedicationService prescribedMedicationService = ServiceGenerator.createService(PrescribedMedicationService.class);

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
        LinearLayout.LayoutParams linearLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                50
        );
        linearLayout.setMargins(0, 0, 0, 3);

        LinearLayout.LayoutParams textViewLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        textViewLayout.weight = 1.0f;

        for(PrescribedMedDto item : data) {
            TableRow tableRow = new TableRow(listMedicationLayout.getContext());
            tableRow.setLayoutParams(linearLayout);

            TextView textViewName = new TextView(tableRow.getContext());
            textViewName.setBackgroundColor(getResources().getColor(R.color.white));
            textViewName.setLayoutParams(textViewLayout);
            textViewName.setText(item.getName());

            TextView textViewQuantity = new TextView(tableRow.getContext());
            textViewQuantity.setBackgroundColor(getResources().getColor(R.color.white));
            textViewQuantity.setLayoutParams(textViewLayout);
            textViewQuantity.setText(item.getQuantity() + " " + item.getUnit());

            TextView textViewDate = new TextView(tableRow.getContext());
            textViewDate.setBackgroundColor(getResources().getColor(R.color.white));
            textViewDate.setLayoutParams(textViewLayout);
            textViewDate.setText(item.getTime());

            tableRow.addView(textViewName);
            tableRow.addView(textViewQuantity);
            tableRow.addView(textViewDate);
            listMedicationLayout.addView(tableRow);
        }
    }
}
