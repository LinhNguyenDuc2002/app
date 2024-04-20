package com.example.medication.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.data.AssessmentResult;
import com.example.medication.service.AssessmentService;
import com.example.medication.service.ServiceGenerator;
import com.example.medication.util.DateUtil;
import com.example.medication.util.DialogUtil;
import com.example.medication.util.StringUtil;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthStatisticActivity extends MainActivity  {
    private final AssessmentService assessmentService = ServiceGenerator.createService(AssessmentService.class);

    private LineChart lineChart;
    private LinearLayout listHistory;

    private EditText fromDate;
    private EditText toDate;
    private Button healthOkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_statistic_activity);

        constructor();
        loadData();
    }

    @Override
    public void constructor() {
        super.constructor();

        lineChart = findViewById(R.id.lineChart);
        listHistory = findViewById(R.id.listHistory);

        fromDate = findViewById(R.id.fromDate);
        toDate = findViewById(R.id.toDate);
        healthOkButton = findViewById(R.id.healthOkButton);

        fromDate.setOnClickListener(this);
        toDate.setOnClickListener(this);
        healthOkButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        int id = v.getId();

        if (id == R.id.fromDate)
            DialogUtil.showDatePickerDialog(this, fromDate);
        else if(id == R.id.toDate) {
            DialogUtil.showDatePickerDialog(this, toDate);
        }
        else if(id == R.id.healthOkButton) {
            loadHistory();
        }
    }

    private void loadData() {
        assessmentService.getHealthStatistic(1).enqueue(new Callback<Map<Integer, Integer>>() {
            @Override
            public void onResponse(Call<Map<Integer, Integer>> call, Response<Map<Integer, Integer>> response) {
                if (response.isSuccessful()) {
                    Map<Integer, Integer> data = response.body();
                    showData(data);
                } else {
                    System.out.println("error");
                }
            }

            @Override
            public void onFailure(Call<Map<Integer, Integer>> call, Throwable t) {
                t.printStackTrace();
                System.err.println("Đã xảy ra lỗi: " + t.getMessage());
            }
        });
    }

    private void loadHistory() {
        if(!TextUtils.isEmpty(fromDate.getText()) && !TextUtils.isEmpty(toDate.getText())) {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("start", fromDate.getText().toString());
            parameters.put("end", toDate.getText().toString());

            assessmentService.getHealthHistory(1, parameters).enqueue(new Callback<List<AssessmentResult>>() {
                @Override
                public void onResponse(Call<List<AssessmentResult>> call, Response<List<AssessmentResult>> response) {
                    if (response.isSuccessful()) {
                        List<AssessmentResult> data = response.body();
                        showHistory(data);
                    } else {
                        System.out.println("error");
                    }
                }

                @Override
                public void onFailure(Call<List<AssessmentResult>> call, Throwable t) {
                    t.printStackTrace();
                    System.err.println("Đã xảy ra lỗi: " + t.getMessage());
                }
            });
        }
    }

    private void showData(Map<Integer, Integer> data) {
        int i = 0;

        List<Integer> value = new ArrayList<>(data.values());
        List<Entry> entries = new ArrayList<>();
        for(int index = value.size()>10?10:value.size(); index >=0; index--) {
            entries.add(new Entry(i++, value.get(index)));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Assessment result");

        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.BLACK);

        LineData lineData = new LineData(dataSet);

        lineChart.setHorizontalScrollBarEnabled(true);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(10f);

        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private void showHistory(List<AssessmentResult> data) {
        TableRow.LayoutParams linearLayout = new TableRow.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                150
        );
        linearLayout.setMargins(0, 0, 0, 10);

        TableRow.LayoutParams textViewLayout = new TableRow.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                3
        );

        for(int i = data.size()-1; i>=0; i--) {
            AssessmentResult item = data.get(i);
            TableRow tableRow = new TableRow(listHistory.getContext());
            tableRow.setLayoutParams(linearLayout);
            if(item.getHealthStatus().equals("GOOD_STATE")) {
                tableRow.setBackground(new ColorDrawable(getResources().getColor(R.color.cadmium_green_color)));
            }
            else if(item.getHealthStatus().equals("MEDIUM_STATE")) {
                tableRow.setBackground(new ColorDrawable(getResources().getColor(R.color.orange_color)));
            }
            else {
                tableRow.setBackground(new ColorDrawable(getResources().getColor(R.color.dark_red_color)));
            }

            TextView textViewDate = new TextView(tableRow.getContext());
            textViewDate.setLayoutParams(textViewLayout);
            textViewDate.setText(DateUtil.utilDateToString(item.getDate()));
            textViewDate.setTextSize(18);
            textViewDate.setTextColor(Color.WHITE);
            textViewDate.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            textViewDate.setPadding(5, 0, 0, 0);

            TextView textViewScore = new TextView(tableRow.getContext());
            textViewScore.setLayoutParams(new TableRow.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    2
            ));
            textViewScore.setText(item.getScore().toString() + "/100");
            textViewScore.setTextSize(18);
            textViewScore.setTextColor(Color.WHITE);
            textViewScore.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

            TextView textViewResult = new TextView(tableRow.getContext());
            textViewResult.setLayoutParams(textViewLayout);
            textViewResult.setText(StringUtil.transferToMessage(item.getHealthStatus()));
            textViewResult.setTextSize(18);
            textViewResult.setTextColor(Color.WHITE);
            textViewResult.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

            listHistory.addView(tableRow);
            tableRow.addView(textViewDate);
            tableRow.addView(textViewScore);
            tableRow.addView(textViewResult);
        }
    }
}
