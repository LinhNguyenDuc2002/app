package com.example.medication.vinhquang.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.service.ServiceGenerator;
import com.example.medication.util.TransferActivity;
import com.example.medication.vinhquang.api.ApiService;
import com.example.medication.vinhquang.data.AppointmentRequest;
import com.example.medication.vinhquang.util.GlobalValues;

import java.text.ParseException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAppActivity extends MainActivity {
    private final ApiService api = ServiceGenerator.createService(ApiService.class);
    GlobalValues globalValues = GlobalValues.getInstance();
    AppointmentRequest request = new AppointmentRequest();
    Button save;
    Button searchDr;
    Button searchPt;
    Button searchDate;
    Button searchTime;
    EditText searchLocal;
    private String sDate;
    private String sTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_app_activity);

        constructor();
    }

    @Override
    public void constructor() {
        super.constructor();

        Button returnButton = findViewById(R.id.returnBtn);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        globalValues.setSearchType(-1);

        save = findViewById(R.id.save);
        searchDr = findViewById(R.id.searchDr);
        searchPt = findViewById(R.id.searchPt);
        searchDate = findViewById(R.id.searchDate);
        searchTime = findViewById(R.id.searchTime);
        searchLocal = findViewById(R.id.searchLocal);

        searchDr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalValues.setSearchType(0);
                TransferActivity.transferActivity(AddAppActivity.this, SearchPeopleActivity.class);
            }
        });

        searchPt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalValues.setSearchType(1);
                TransferActivity.transferActivity(AddAppActivity.this, SearchPeopleActivity.class);
            }
        });

        searchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate();
            }
        });

        searchTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTime();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    save();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(globalValues.getSearchType() == 0) {
            if(globalValues.getDocterSearch() != null)
                searchDr.setText(globalValues.getDocterSearch().getName());
        } else if(globalValues.getSearchType() == 1) {
            if(globalValues.getPatientSearch() != null)
                searchPt.setText(globalValues.getPatientSearch().getName());
        }

    }

    public void getDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddAppActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        monthOfYear += 1;
                        String month = String.valueOf(monthOfYear);
                        if(monthOfYear < 10) {
                            month = "0" + month;
                        }
                        sDate = year + "-" + month + "-" + dayOfMonth;

                        searchDate.setText(sDate);
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    public void getTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddAppActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        String hour = String.valueOf(hourOfDay);
                        String sMinute = String.valueOf(minute);
                        if(hourOfDay < 10) hour = "0" + hour;
                        if(minute < 10) sMinute = "0" + minute;
                        sTime = hour + ":" + sMinute;

                        searchTime.setText(sTime);
                    }
                }, hour, minute, true);

        timePickerDialog.show();
    }

    public void save() throws ParseException {
        request.setDateTime(sDate + " " + sTime);
        request.setLocation(searchLocal.getText().toString());
        request.setStatus(0);
        request.setPatientId(globalValues.getPatientSearch().getId());
        request.setDoctorId(globalValues.getDocterSearch().getId());

        api.addApp(request, 1).enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String data = response.body();
                    System.out.println("vinhquang .... " + data);
                    afterSave();
                } else {
                    System.out.println("error get old");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                System.err.println("Đã xảy ra lỗi: " + t.getMessage());
            }
        });
    }

    public void afterSave() {
        Toast.makeText(this, "Chờ đối phương xác nhận!", Toast.LENGTH_SHORT).show();
    }
}