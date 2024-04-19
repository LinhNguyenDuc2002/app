package com.example.medication.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.data.DTO.PatientDto;
import com.example.medication.data.DTO.PrescriptionDto;
import com.example.medication.service.PatientService;
import com.example.medication.service.PrescriptionService;
import com.example.medication.service.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrescriptionActivity extends MainActivity {

    private final PatientService patientService = ServiceGenerator.createService(PatientService.class);
    private final PrescriptionService prescriptionService = ServiceGenerator.createService(PrescriptionService.class);
    private TextView patientName, patientDob, patientPhone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_prescription);

        constructor();
        loadInforPatient(1);
        loadAllPrescriptions(1);
    }



    public void constructor() {
        super.constructor();
        patientName = findViewById(R.id.text_name);
        patientDob = findViewById(R.id.text_dob);
        patientPhone = findViewById(R.id.text_phone);

    }
    private void loadInforPatient(int patientId) {
        patientService.findById(patientId).enqueue(new Callback<PatientDto>() {
            @Override
            public void onResponse(Call<PatientDto> call, Response<PatientDto> response) {
                if (response.isSuccessful()) {
                    PatientDto data = response.body();
                    showInforPatient(data);
                } else {
                    System.out.println("error");
                }
            }

            @Override
            public void onFailure(Call<PatientDto> call, Throwable t) {
                t.printStackTrace();
                System.err.println("Đã xảy ra lỗi: " + t.getMessage());
            }
        });
    }

    private void showInforPatient(PatientDto data) {
        patientName.setText("Họ và tên: "+data.getFullName());
        patientDob.setText("Ngày sinh: "+data.getDateOfBirth());
        patientPhone.setText("SĐT: "+ data.getPhoneNumber());
    }
    private void loadAllPrescriptions(Integer patientId) {
        prescriptionService.getAllPrescriptions(patientId).enqueue(
                new Callback<List<PrescriptionDto>>() {
                    @Override
                    public void onResponse(Call<List<PrescriptionDto>> call, Response<List<PrescriptionDto>> response) {
                        if(response.isSuccessful()){
                            List<PrescriptionDto> data = response.body();
                            showListPrescription(data);
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
        Log.d("data size", String.valueOf(data.size()));
    }

}
