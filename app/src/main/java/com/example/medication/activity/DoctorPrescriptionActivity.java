package com.example.medication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.data.DTO.PatientDto;
import com.example.medication.service.PatientService;
import com.example.medication.service.ServiceGenerator;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorPrescriptionActivity extends MainActivity {
    private EditText searchNamePatientEditText;
    private LinearLayout data;
    private final PatientService patientService = ServiceGenerator.createService(PatientService.class);
    private View dataView;
    private ScrollView scrollView;
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(date);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_prescriptions);
        searchNamePatientEditText = findViewById(R.id.search_name_patient);
        scrollView = findViewById(R.id.scrollViewDoctor);
        searchNamePatientEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần thực hiện gì ở đây
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Không cần thực hiện gì ở đây
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Đây là nơi bạn có thể thực hiện các hành động sau khi người dùng nhập xong văn bản
                String searchText = s.toString();
                clickFind();
                // Thực hiện tìm kiếm hoặc các hành động khác dựa trên văn bản đã nhập

            }
        });

    }


    private void clickFind() {
        data = findViewById(R.id.data);
        data.removeAllViews();
        searchNamePatientEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Thực hiện các hành động khi người dùng nhấn biểu tượng tìm kiếm trên bàn phím
                    String searchText = searchNamePatientEditText.getText().toString();
                    System.out.println(searchText);
                    patientService.searchByName(searchText).enqueue(new Callback<List<PatientDto>>() {
                        @Override
                        public void onResponse(Call<List<PatientDto>> call, Response<List<PatientDto>> response) {
                            if (response.isSuccessful()){
                                List<PatientDto> patientDtos = new ArrayList<>();
                                patientDtos = response.body();
                                System.out.println(patientDtos.size());
                                for (PatientDto t : patientDtos){
                                    showPatient(t);
                                }
                            }
                            else{
                                System.out.println("data patient not ok");
                            }
                        }

                        @Override
                        public void onFailure(Call<List<PatientDto>> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                    // Thực hiện tìm kiếm hoặc các hành động khác dựa trên văn bản đã nhập
                    return true; // Đánh dấu sự kiện đã được xử lý
                }
                return false; // Không đánh dấu sự kiện đã được xử lý
            }
        });
    }

    private void showPatient(PatientDto t) {
            // Inflate layout for prescription item

            dataView = getLayoutInflater().inflate(R.layout.patient_item, null);
            TextView sId = dataView.findViewById(R.id.sId);
            TextView name = dataView.findViewById(R.id.text_name);
            TextView phone = dataView.findViewById(R.id.text_phone);

            sId.setText(t.getsId());
            name.setText(t.getFullName());
            phone.setText(t.getPhoneNumber());


            dataView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Perform action when prescriptionView is clicked
                    // For example, you can start a new activity
                    Intent intent = new Intent(DoctorPrescriptionActivity.this, DoctorPrescriptionDetailActivity.class);
                    intent.putExtra("patient_id",t.getId());
                    intent.putExtra("patient_name", t.getFullName());
                    intent.putExtra("patient_dob", formatDate(t.getDateOfBirth()));
                    intent.putExtra("patient_phone", t.getPhoneNumber());
                    startActivity(intent);
                }
            });

            // Set margin for prescriptionView
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(0, 0, 0, 16); // Đặt khoảng cách dưới là 16 pixels, bạn có thể điều chỉnh theo ý muốn

            dataView.setLayoutParams(layoutParams);

            // Add prescriptionView to prescriptionListLayout
            data.addView(dataView);

    }
}
