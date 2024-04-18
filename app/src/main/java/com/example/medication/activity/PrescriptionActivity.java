package com.example.medication.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.data.DTO.PatientDto;
import com.example.medication.data.PatientEntity;
import com.example.medication.service.AssessmentService;
import com.example.medication.service.PatientService;
import com.example.medication.service.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrescriptionActivity extends MainActivity {
    private final PatientService patientService = ServiceGenerator.createService(PatientService.class);
    private TextView textName, textDob,textPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_prescription);

        constructor();
        FindInforPatient(1);
    }

    @Override
    public void constructor() {
        super.constructor();
        textName = findViewById(R.id.text_name);
        textDob = findViewById(R.id.text_dob);
        textPhone = findViewById(R.id.text_phone);
    }
    private void FindInforPatient(int patientId) {
        patientService.findById(patientId).enqueue(new Callback<PatientDto>() {
            @Override
            public void onResponse(Call<PatientDto> call, Response<PatientDto> response) {
                if(response.isSuccessful()){
                    PatientDto patientDto = response.body();
                    showDataPatient(patientDto);
//                    Log.d("đã lấy dược Patient");
                } else {
                    Log.e("PrescriptionActivity", "Đã xảy ra lỗi khi tìm kiếm thông tin bệnh nhân: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<PatientDto> call, Throwable t) {
                t.printStackTrace();
                System.err.println("Đã xảy ra lỗi: " + t.getMessage());
            }
        });

    }

    private void showDataPatient(PatientDto patientDto) {
        textName.setText(patientDto.getFullName());
        textDob.setText(patientDto.getDateOfBirth().toString());
        textPhone.setText(patientDto.getPhoneNumber());

        Log.d("PrescriptionActivity", "Họ và tên của bệnh nhân là: " + patientDto.getFullName());
    }


}
