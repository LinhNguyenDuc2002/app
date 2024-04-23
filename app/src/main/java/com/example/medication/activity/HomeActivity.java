package com.example.medication.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.data.DTO.DailyStatusDto;
import com.example.medication.data.DTO.PrescribedMedDto;
import com.example.medication.service.DailyStatusService;
import com.example.medication.service.PrescribedMedicationService;
import com.example.medication.service.ServiceGenerator;
import com.example.medication.util.TransferActivity;
import com.example.medication.vinhquang.activity.AppointmentActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import static com.example.medication.vinhquang.util.FirebaseUtil.*;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends MainActivity {
    private final PrescribedMedicationService prescribedMedicationService = ServiceGenerator.createService(PrescribedMedicationService.class);
    private final DailyStatusService dailyStatusService = ServiceGenerator.createService(DailyStatusService.class);
    private LinearLayout listMedicationLayout;

    private Button statisticButton;
    private Button healthButton;
    private Button prescriptionButton;
    private Button scheduleButton;
    private Button addNewMedicationButton;
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
        addNewMedicationButton = findViewById(R.id.add_new_medication);

        statisticButton.setOnClickListener(this);
        healthButton.setOnClickListener(this);
        scheduleButton.setOnClickListener(this);
        prescriptionButton.setOnClickListener(this);
        addMemberButton.setOnClickListener(this);
        addNewMedicationButton.setOnClickListener(this);

        getToken(1);
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
<<<<<<< HEAD
        else if (id == R.id.add_new_medication)
            TransferActivity.transferActivity(this, NewMedication.class);

=======
        else if (id == R.id.scheduleButton)
            TransferActivity.transferActivity(this, AppointmentActivity.class);
>>>>>>> master
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
        TableRow.LayoutParams linearLayout = new TableRow.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                150
        );
        linearLayout.setMargins(0, 0, 0, 10);

        TableRow.LayoutParams textViewLayout = new TableRow.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                2
        );

        for(PrescribedMedDto item : data) {
            TableRow tableRow = new TableRow(listMedicationLayout.getContext());
            tableRow.setLayoutParams(linearLayout);
            tableRow.setBackground(new ColorDrawable(getResources().getColor(R.color.white)));

            TextView textViewName = new TextView(tableRow.getContext());
            textViewName.setLayoutParams(new TableRow.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    5
            ));
            textViewName.setText(item.getName());
            textViewName.setTextSize(18);
            textViewName.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            textViewName.setPadding(5, 0, 0, 0);

            textViewName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Tạo một mảng chuỗi chứa các lựa chọn cho spinner
                    String[] options = {"Thuốc đã hết", "Chỉnh giờ uống"};

                    // Tạo một ArrayAdapter để hiển thị danh sách các lựa chọn trong AlertDialog
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(HomeActivity.this, android.R.layout.simple_list_item_1, options);

                    // Tạo AlertDialog và hiển thị danh sách lựa chọn
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                    builder.setTitle("Chọn một lựa chọn")
                            .setAdapter(adapter, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Xử lý khi một lựa chọn được chọn
                                    String selectedOption = options[which];
                                    // Kiểm tra nếu người dùng chọn "Thuốc đã hết"
                                    if (selectedOption.equals("Thuốc đã hết")) {
                                        prescribedMedicationService.updatePrescribedMedEndById(item.getId()).enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                System.out.println("Thuốc "+item.getName()+" đã hết");
                                                recreate();
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {

                                            }
                                        });
                                    } else {
                                        Intent intent = new Intent(HomeActivity.this,UpdateTimeMedication.class);
                                        intent.putExtra("medicationId",item.getId());
                                        intent.putExtra("medicationName",item.getName());
                                        intent.putExtra("medicationQuantity",item.getQuantity());
                                        intent.putExtra("medicationTime",item.getTime());
                                        startActivity(intent);
                                    }
                                    // Đóng AlertDialog
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Đóng AlertDialog nếu người dùng chọn hủy
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });


            TextView textViewQuantity = new TextView(tableRow.getContext());
            textViewQuantity.setLayoutParams(textViewLayout);
            textViewQuantity.setText(item.getQuantity() + " " + item.getUnit());
            textViewQuantity.setTextSize(18);
            textViewQuantity.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

            TextView textViewDate = new TextView(tableRow.getContext());
            textViewDate.setLayoutParams(textViewLayout);
            textViewDate.setText(item.getTime());
            textViewDate.setTextSize(14);
            textViewDate.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

            CheckBox checkBox = new CheckBox(tableRow.getContext());
            checkBox.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            ));

            dailyStatusService.getDailyStatus(item.getTime(),1,item.getId()).enqueue(new Callback<DailyStatusDto>() {
                @Override
                public void onResponse(Call<DailyStatusDto> call, Response<DailyStatusDto> response) {
                    DailyStatusDto dto = response.body();
                    if (dto != null){
                        checkBox.setChecked(true); // Không kiểm tra checkbox
                        textViewName.setTextColor(getResources().getColor(R.color.red_color));
                    }
                    else {
                        checkBox.setChecked(false);
                    }
                }

                @Override
                public void onFailure(Call<DailyStatusDto> call, Throwable t) {
                    t.printStackTrace();
                }
            });

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Xử lý sự kiện khi checkbox được chọn
                    if (isChecked) {
                        System.out.println(item.getId());
                        dailyStatusService.updateDailyStatus(item, 1, 1).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                System.out.println("ok");
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                System.out.println("not ok");
                            }
                        });
                        // Đổi màu của textViewName thành đỏ khi checkbox được chọn
                        textViewName.setTextColor(getResources().getColor(R.color.red_color));
                    } else {
                        dailyStatusService.updateDailyStatus(item, 1, 0);
                        // Đổi màu của textViewName về màu mặc định hoặc một màu khác khi checkbox không được chọn
                        textViewName.setTextColor(getResources().getColor(R.color.black));
                    }
                }
            });


            listMedicationLayout.addView(tableRow);
            tableRow.addView(textViewName);
            tableRow.addView(textViewQuantity);
            tableRow.addView(textViewDate);
            tableRow.addView(checkBox);
        }
    }


}
