package com.example.medication.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.data.DTO.PrescribedMedDto;
import com.example.medication.service.AssessmentService;
import com.example.medication.service.ServiceGenerator;
import com.example.medication.service.StatisticService;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthStatisticActivity extends MainActivity {
    private final AssessmentService assessmentService = ServiceGenerator.createService(AssessmentService.class);

    private LineChart lineChart;
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

    private void showData(Map<Integer, Integer> data) {
        int i = 0;

        List<Entry> entries = new ArrayList<>();
        for(Integer item : data.values()) {
            entries.add(new Entry(i++, item));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Assessment Result");

        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.BLACK);

        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData);
        lineChart.invalidate();
    }
}
