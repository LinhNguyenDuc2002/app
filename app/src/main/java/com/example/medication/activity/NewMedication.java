package com.example.medication.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.data.DTO.PatientDto;
import com.example.medication.data.MedicationEntity;
import com.example.medication.service.MedicationService;
import com.example.medication.service.PatientService;
import com.example.medication.service.ServiceGenerator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewMedication extends MainActivity {
    private final PatientService patientService = ServiceGenerator.createService(PatientService.class);
    private final MedicationService medicationService = ServiceGenerator.createService(MedicationService.class);
    private TextView patientName, patientDob, patientPhone;
    private static List<String> allNames = new ArrayList<>();
    private List<String> filteredNames = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private EditText nameMedication,numberPills,timeToDrink;
    private ListView listView;
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_medication);
        constructor();
//        allNames.add(setupSearch().toString());

    }

//    public List<String> setupSearch() {
//        List<String> names = new ArrayList<>();
//
//        medicationService.findAll().enqueue(new Callback<List<MedicationEntity>>() {
//            @Override
//            public void onResponse(Call<List<MedicationEntity>> call, Response<List<MedicationEntity>> response) {
//                if (response.isSuccessful()) {
//                    List<MedicationEntity> medicationList = response.body();
//                    for (MedicationEntity medicationEntity : medicationList) {
//                        names.add(medicationEntity.getName());
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<MedicationEntity>> call, Throwable t) {
//                // Xử lý khi gặp lỗi
//            }
//        });
//        return names;
//    }



    public void constructor() {
        super.constructor();
        nameMedication = findViewById(R.id.searchEditText);
        listView = findViewById(R.id.resultsListView);
        numberPills = findViewById(R.id.number_pills);
        timeToDrink = findViewById(R.id.time_to_drink);
    }

}
