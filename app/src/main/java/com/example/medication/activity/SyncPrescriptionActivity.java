package com.example.medication.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.data.DTO.PrescribedMedDto;
import com.example.medication.service.PrescribedMedicationService;
import com.example.medication.service.ServiceGenerator;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncPrescriptionActivity extends MainActivity {
    private String enteredTime;
    private Integer prescriptionId, prescriptionStatus;
    private String prescriptionPatient, prescriptionDoctor;
    private TextView patientName, doctorName;
    private Button addSync;
    private final PrescribedMedicationService prescribedMedicationService = ServiceGenerator.createService(PrescribedMedicationService.class);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sync_prescription);
        Intent intent = getIntent();

        // Get data from the Intent
        prescriptionId = intent.getIntExtra("prescription_id", -1);
        prescriptionPatient = intent.getStringExtra("prescription_patient");
        prescriptionDoctor = intent.getStringExtra("prescription_doctor");
        prescriptionStatus = intent.getIntExtra("prescription_status", -1);
        constructor();
        loadInforDoctorAndPatient();
        loadInforDetailPrescription();
    }
    private void loadButtonSync() {
        LinearLayout syncButtonLayout = findViewById(R.id.sync_button);
        Button syncButton = new Button(this);
        syncButton.setText("Sync Data");
        syncButtonLayout.addView(syncButton);
    }
    private void loadInforDetailPrescription() {
        prescribedMedicationService.findAllPrescribedMedByPrecriptionId(prescriptionId).enqueue(
                new Callback<List<PrescribedMedDto>>() {
                    @Override
                    public void onResponse(Call<List<PrescribedMedDto>> call, Response<List<PrescribedMedDto>> response) {
                        if (response.isSuccessful()) {
                            List<PrescribedMedDto> data = response.body();
                            updateTable(data);
                        } else {
                            Log.e("Error", "Error fetching data");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PrescribedMedDto>> call, Throwable t) {
                        Log.e("Error", "API Call failed");
                    }
                }
        );
    }

    private void updateTable(List<PrescribedMedDto> data) {
        TableLayout tableLayout = findViewById(R.id.table_detail_prescription);

        for (int i = 0; i < data.size(); i++) {
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
            nameTextView.setText(data.get(i).getName());
            nameTextView.setGravity(Gravity.CENTER);
            nameTextView.setPadding(8, 8, 8, 8);
            nameTextView.setBackgroundResource(R.drawable.table_border);

            TextView quantityTextView = new TextView(this);
            quantityTextView.setLayoutParams(layoutParams);
            quantityTextView.setText(String.valueOf(data.get(i).getQuantity()));
            quantityTextView.setGravity(Gravity.CENTER);
            quantityTextView.setPadding(8, 8, 8, 8);
            quantityTextView.setBackgroundResource(R.drawable.table_border);

            EditText timeText = new EditText(this);
            timeText.setLayoutParams(layoutParams);
            timeText.setHint("HH:MM");
            timeText.setGravity(Gravity.CENTER);
            timeText.setPadding(8, 8, 8, 8);
            timeText.setBackgroundResource(R.drawable.table_border);
            // set đồng hoạt động thời gian
            int f = i;
            timeText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // Không cần xử lý trước sự thay đổi văn bản
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // Không cần xử lý khi văn bản đang được thay đổi
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    // Cập nhật giờ khi người dùng nhập xong
                    enteredTime = editable.toString()+":00";
                    data.get(f).setTime(enteredTime);
                }
            });

            TextView noteTextView = new TextView(this);
            noteTextView.setLayoutParams(layoutParams);
            noteTextView.setText(data.get(i).getNote());
            noteTextView.setGravity(Gravity.CENTER);
            noteTextView.setPadding(8, 8, 8, 8);
            noteTextView.setBackgroundResource(R.drawable.table_border);

            // Add TextViews to the TableRow
            row.addView(nameTextView);
            row.addView(quantityTextView);
            row.addView(timeText);
            row.addView(noteTextView);

            // Add the TableRow to the TableLayout
            tableLayout.addView(row);
        }
        addSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prescribedMedicationService.SyncMedicationToDailyByPrescription(prescriptionId,data).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(getApplicationContext(), "Đã thêm thuốc ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SyncPrescriptionActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }


    private void loadInforDoctorAndPatient() {
        patientName.setText(prescriptionPatient);
        doctorName.setText(prescriptionDoctor);
    }

    public void constructor() {
        super.constructor();
        patientName = findViewById(R.id.patient_name);
        doctorName = findViewById(R.id.doctor_name);
        addSync = findViewById(R.id.addSync_button);
    }

}
