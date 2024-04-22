package com.example.medication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.service.DailyStatusService;
import com.example.medication.service.MedicationService;
import com.example.medication.service.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateTimeMedication extends MainActivity {
    private final MedicationService medicationService = ServiceGenerator.createService(MedicationService.class);
    private EditText nameMedication,numberPills,timeToDrink;
    private Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_medication);
        constructor();
        Intent intent = getIntent();
        Integer medicationId = intent.getIntExtra("medicationId", -1); // Lấy medicationId
        String medicationName = intent.getStringExtra("medicationName"); // Lấy medicationName
        Integer medicationQuantity = intent.getIntExtra("medicationQuantity", -1); // Lấy medicationQuantity
        String medicationTime = intent.getStringExtra("medicationTime"); // Lấy medicationTime
        nameMedication.setText(medicationName);
        numberPills.setText(medicationQuantity.toString());
        timeToDrink.setText(medicationTime);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = String.valueOf(timeToDrink.getText());
                medicationService.updateTime(medicationId, time).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        System.out.println("update thành công "+ medicationId);
                        Intent intent1 = new Intent(UpdateTimeMedication.this, HomeActivity.class);
                        startActivity(intent1);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });


    }
    public void constructor(){
        super.constructor();
        nameMedication = findViewById(R.id.searchEditText);
        numberPills = findViewById(R.id.number_pills);
        timeToDrink = findViewById(R.id.time_to_drink);
        save = findViewById(R.id.save_new_medication);
    }

}
