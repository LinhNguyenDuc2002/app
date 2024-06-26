package com.example.medication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.data.DTO.PrescribedMedDto;
import com.example.medication.service.PrescribedMedicationService;
import com.example.medication.service.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrescriptionDetailActivity extends MainActivity {
    private Integer prescriptionId,prescriptionStatus;
    private String prescriptionPatient,prescriptionDoctor;
    private TextView patientName, doctorName;
    private final PrescribedMedicationService prescribedMedicationService = ServiceGenerator.createService(PrescribedMedicationService.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prescription_detail);
        Intent intent = getIntent();

        // Get data from the Intent
        prescriptionId = intent.getIntExtra("prescription_id", -1);
        prescriptionPatient = intent.getStringExtra("prescription_patient");
        prescriptionDoctor = intent.getStringExtra("prescription_doctor");
        prescriptionStatus = intent.getIntExtra("prescription_status",-1);
        constructor();
        loadInforDoctorAndPatient();
        loadInforDetailPrescription();
        loadButtonSync();
    }

    private void loadButtonSync() {
        LinearLayout syncButtonLayout = findViewById(R.id.sync_button);
        Button syncButton = new Button(this);
        syncButton.setText("Sync Data");

// Kiểm tra điều kiện
        if (prescriptionStatus == 0) {
            // Thêm button vào LinearLayout
            syncButtonLayout.addView(syncButton);

            // Thiết lập bộ lắng nghe sự kiện cho button nếu cần
            syncButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent intent = new Intent(PrescriptionDetailActivity.this, SyncPrescriptionActivity.class);
                    intent.putExtra("prescription_id", prescriptionId);
                    intent.putExtra("prescription_patient", prescriptionPatient);
                    intent.putExtra("prescription_doctor", prescriptionDoctor);
                    intent.putExtra("prescription_status", prescriptionStatus);
                   startActivity(intent);
                }
            });
        }
    }


    private void loadInforDetailPrescription() {
        prescribedMedicationService.findAllPrescribedMedByPrecriptionId(prescriptionId).enqueue(
                new Callback<List<PrescribedMedDto>>() {
                    @Override
                    public void onResponse(Call<List<PrescribedMedDto>> call, Response<List<PrescribedMedDto>> response) {
                        if(response.isSuccessful()){
                            List<PrescribedMedDto> data = response.body();
                            updateTable(data);
                        }
                        else{
                            System.out.println("error data");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PrescribedMedDto>> call, Throwable t) {
                        System.out.println("error");
                    }
                }
        );
    }

    private void updateTable(List<PrescribedMedDto> data) {
        TableLayout tableLayout = findViewById(R.id.table_detail_prescription);

        for (PrescribedMedDto prescribedMed : data) {
            // Tạo một TableRow mới cho mỗi dòng dữ liệu
            TableRow row = new TableRow(this);

            // Thiết lập layout params cho TableRow
            TableRow.LayoutParams rowLayoutParams = new TableRow.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );

            // Thiết lập margin cho TableRow (nếu cần)
            rowLayoutParams.setMargins(0, 0, 0, 10); // Ví dụ: Đặt margin dưới là 10 pixels

            // Đặt layout params cho TableRow
            row.setLayoutParams(rowLayoutParams);

            // Tạo layoutParams cho các TextView
            TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.weight = 1;

            // Create TextView for each column
            TextView nameTextView = new TextView(this);
            nameTextView.setLayoutParams(layoutParams);
            nameTextView.setText(prescribedMed.getName());
            nameTextView.setGravity(Gravity.CENTER);
            nameTextView.setPadding(8, 8, 8, 8);
            nameTextView.setBackgroundResource(R.drawable.table_border);

            TextView quantityTextView = new TextView(this);
            quantityTextView.setLayoutParams(layoutParams);
            quantityTextView.setText(String.valueOf(prescribedMed.getQuantity()));
            quantityTextView.setGravity(Gravity.CENTER);
            quantityTextView.setPadding(8, 8, 8, 8);
            quantityTextView.setBackgroundResource(R.drawable.table_border);


            TextView noteTextView = new TextView(this);
            noteTextView.setLayoutParams(layoutParams);
            noteTextView.setText(prescribedMed.getNote());
            noteTextView.setGravity(Gravity.CENTER);
            noteTextView.setPadding(8, 8, 8, 8);
            noteTextView.setBackgroundResource(R.drawable.table_border);

            // Add TextViews to the TableRow
            row.addView(nameTextView);
            row.addView(quantityTextView);
            row.addView(noteTextView);

            // Add the TableRow to the TableLayout
            tableLayout.addView(row);
        }
    }




    private void loadInforDoctorAndPatient() {
        patientName.setText(prescriptionPatient);
        doctorName.setText(prescriptionDoctor);
    }

    public void constructor() {
            super.constructor();
        patientName =  findViewById(R.id.patient_name);
        doctorName = findViewById(R.id.doctor_name);
    }
}
