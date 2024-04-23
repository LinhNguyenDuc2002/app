package com.example.medication.activity;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.data.DTO.PrescriptionDto;
import com.example.medication.service.PrescriptionService;
import com.example.medication.service.ServiceGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorPrescriptionDetailActivity extends MainActivity {
    private LinearLayout buttonAdd;
    private View dataView;

    private TextView patientName,patientDob,patientPhone;
    private final PrescriptionService prescriptionService = ServiceGenerator.createService(PrescriptionService.class);
    private LinearLayout prescriptionListLayout;
    private Integer id;
    private String n,d,p;

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_prescription);
        Intent intent = getIntent();
        id = intent.getIntExtra("patient_id",-1);
        n = intent.getStringExtra("patient_name");
        d = intent.getStringExtra("patient_dob");
        p = intent.getStringExtra("patient_phone");
        patientName = findViewById(R.id.text_name);
        patientPhone = findViewById(R.id.text_phone);
        patientDob = findViewById(R.id.text_dob);


        patientName.setText(n);
        patientDob.setText(d);
        patientPhone.setText(p);

        prescriptionListLayout = findViewById(R.id.prescription_list);

        buttonAdd = findViewById(R.id.button_add_doctor);
        Button addButton = new Button(this);
        addButton.setText("Thêm đơn");
        addButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        buttonAdd.addView(addButton);
        constructor();
        loadAllPrescriptions(id);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện hành động khi nút Add được nhấn
                // Ví dụ: Chuyển đến một activity để thêm đơn thuốc mới
                Intent intent = new Intent(DoctorPrescriptionDetailActivity.this, AddPrescriptionActivity.class);
                intent.putExtra("patient_id",id);
                intent.putExtra("patient_name", n);
                intent.putExtra("patient_dob", d);
                intent.putExtra("patient_phone", p);
                startActivity(intent);
            }
        });
    }

    private void loadAllPrescriptions(Integer patientId) {
        prescriptionService.getAllPrescriptions(patientId).enqueue(
                new Callback<List<PrescriptionDto>>() {
                    @Override
                    public void onResponse(Call<List<PrescriptionDto>> call, Response<List<PrescriptionDto>> response) {
                        if (response.isSuccessful()) {
                            List<PrescriptionDto> data = response.body();
                            showListPrescription(data);
                        } else {
                            System.out.println("error list");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PrescriptionDto>> call, Throwable t) {
                        System.out.println("error");
                    }
                }
        );
    }

    private void showListPrescription(List<PrescriptionDto> data) {
        // Lặp qua danh sách đơn thuốc và hiển thị thông tin của từng đơn thuốc
        for (PrescriptionDto prescription : data) {
            addPrescriptionView(prescription);
        }

    }

    private void addPrescriptionView(PrescriptionDto prescription) {
        // Inflate layout for prescription item
        View prescriptionView = getLayoutInflater().inflate(R.layout.prescription_item, null);

        // Find TextViews in the inflated layout
        TextView prescriptionNameTextView = prescriptionView.findViewById(R.id.prescription_name);
        TextView prescriptionDateTextView = prescriptionView.findViewById(R.id.prescription_date);

        // Set data for TextViews
        prescriptionDateTextView.setText("Ngày: "+formatDate(prescription.getCreatedAt()));
        prescriptionNameTextView.setText("Bệnh: "+prescription.getDisease());

        prescriptionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform action when prescriptionView is clicked
                // For example, you can start a new activity
                Intent intent = new Intent(DoctorPrescriptionDetailActivity.this, PrescriptionDetailActivity.class);
                // You can also pass data to the next activity if needed
                intent.putExtra("prescription_id", prescription.getId());
                intent.putExtra("prescription_patient", prescription.getPatientName());
                intent.putExtra("prescription_doctor", prescription.getDoctorName());
                intent.putExtra("prescription_status", prescription.getStatus());
                startActivity(intent);
            }
        });

        if (prescription.getStatus().equals(0)) {
            prescriptionNameTextView.setTextColor(getResources().getColor(R.color.red_color));
        }

        // Set margin for prescriptionView
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, 16); // Đặt khoảng cách dưới là 16 pixels, bạn có thể điều chỉnh theo ý muốn

        prescriptionView.setLayoutParams(layoutParams);

        // Add prescriptionView to prescriptionListLayout
        prescriptionListLayout.addView(prescriptionView);
    }
}
