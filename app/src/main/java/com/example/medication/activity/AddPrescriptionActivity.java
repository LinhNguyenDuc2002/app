package com.example.medication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.data.DTO.PrescribedMedDto;
import com.example.medication.data.PrescriptionEntity;
import com.example.medication.service.PrescriptionService;
import com.example.medication.service.ServiceGenerator;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPrescriptionActivity extends MainActivity {
    private TextView patientName,patientDob,patientPhone;
    private Button add, save;
    private TableLayout tableLayout;
    private EditText disease;
    private Integer patientId;
    private final PrescriptionService prescriptionService  = ServiceGenerator.createService(PrescriptionService.class);

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_prescription);
        Intent intent = getIntent();
        patientId = intent.getIntExtra("patient_id",-1);
        System.out.println(patientId);
        patientName = findViewById(R.id.text_name);
        patientDob = findViewById(R.id.text_dob);
        patientDob.setText(intent.getStringExtra("patient_dob"));
        patientPhone = findViewById(R.id.text_phone);
        patientPhone.setText(intent.getStringExtra("patient_phone"));
        patientName.setText(intent.getStringExtra("patient_name"));
        add = findViewById(R.id.button_add_medication_prescription);
        save = findViewById(R.id.button_save_prescription_doctor);
        tableLayout = findViewById(R.id.new_prescription);
        disease = findViewById(R.id.disease);

        loadData();
        constructor();
    }

    @Override
    public void constructor() {
        super.constructor();
    }
    private void loadData() {
        List<TableRow> addedRows = new ArrayList<>();

// Khi người dùng nhấn vào nút "Add":
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TableRow newRow = new TableRow(AddPrescriptionActivity.this);

                // Tạo các EditTexts để người dùng nhập liệu
                EditText nameEditText = new EditText(AddPrescriptionActivity.this);
                nameEditText.setGravity(Gravity.CENTER);
                nameEditText.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2));
                nameEditText.setBackgroundResource(R.drawable.border);

                EditText quantityEditText = new EditText(AddPrescriptionActivity.this);
                quantityEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                quantityEditText.setGravity(Gravity.CENTER);
                quantityEditText.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2));
                quantityEditText.setBackgroundResource(R.drawable.border);

                EditText noteEditText = new EditText(AddPrescriptionActivity.this);
                noteEditText.setGravity(Gravity.CENTER);
                noteEditText.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2));
                noteEditText.setBackgroundResource(R.drawable.border);

                // Thêm EditTexts vào TableRow
                newRow.addView(nameEditText);
                newRow.addView(quantityEditText);
                newRow.addView(noteEditText);

                // Thêm TableRow vào bảng
                tableLayout.addView(newRow);

                // Thêm hàng mới vào danh sách đã khai báo
                addedRows.add(newRow);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String benh = disease.getText().toString();
                List<PrescribedMedDto> prescribedMedDtoList = new ArrayList<>();
                for (TableRow row : addedRows) {
                    // Đọc dữ liệu từ mỗi hàng và thực hiện các thao tác cần thiết
                    EditText nameEditText = (EditText) row.getChildAt(0);
                    EditText quantityEditText = (EditText) row.getChildAt(1);
                    EditText noteEditText = (EditText) row.getChildAt(2);

                    String name = nameEditText.getText().toString();
                    String quantity = quantityEditText.getText().toString();
                    String note = noteEditText.getText().toString();

                    PrescribedMedDto prescribedMedDto = new PrescribedMedDto(name,Integer.parseInt(quantity),note);
                    prescribedMedDtoList.add(prescribedMedDto);
                }
                System.out.println(prescribedMedDtoList.get(0).getName());
                System.out.println(prescribedMedDtoList.get(0).getQuantity());
                System.out.println(prescribedMedDtoList.get(0).getNote());
                prescriptionService.addNewPrescription(6,patientId,benh,prescribedMedDtoList).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(AddPrescriptionActivity.this, "Đã tạo đơn thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddPrescriptionActivity.this,DoctorPrescriptionActivity.class);
                            startActivity(intent);
                        }
                        else{
                            System.out.println("don fail");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
                // Lặp qua danh sách các hàng đã thêm vào

            }
        });
    }

}
