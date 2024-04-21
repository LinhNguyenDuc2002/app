package com.example.medication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.data.DTO.PrescriptionDto;
import com.example.medication.data.Statistic;
import com.example.medication.service.PrescriptionService;
import com.example.medication.service.ServiceGenerator;
import com.example.medication.service.StatisticService;
import com.example.medication.util.DialogUtil;
import com.example.medication.util.TransferActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticActivity extends MainActivity implements OnChartValueSelectedListener, AdapterView.OnItemSelectedListener {
    private final PrescriptionService prescriptionService = ServiceGenerator.createService(PrescriptionService.class);
    private final StatisticService statisticService = ServiceGenerator.createService(StatisticService.class);

    private Button showDetailButton;
    private Button okButton;
    private EditText startDate;
    private EditText endDate;
    private Spinner prescriptionSpinner;
    private TextView emptyChartText;

    private PieChart chart;

    private Map<String, Integer> prescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic_activity);

        constructor();
    }

    @Override
    public void constructor() {
        super.constructor();

        prescription = new HashMap<>();

        chart = (PieChart) findViewById(R.id.chart);
        chart.setVisibility(View.GONE);
        chart.setRotationEnabled(true);
        chart.setHoleRadius(30f);
        chart.setTransparentCircleAlpha(0);
        chart.setCenterTextSize(20);
        chart.setDrawEntryLabels(false);

        chart.setOnChartValueSelectedListener(this);

        showDetailButton = findViewById(R.id.showDetailButton);
        okButton = findViewById(R.id.okButton);
        startDate = findViewById(R.id.startDate);
        endDate = findViewById(R.id.endDate);
        prescriptionSpinner = findViewById(R.id.prescriptionSpinner);

        emptyChartText = findViewById(R.id.emptyChartText);
        emptyChartText.setVisibility(View.VISIBLE);

        showDetailButton.setOnClickListener(this);
        okButton.setOnClickListener(this);
        startDate.setOnClickListener(this);
        endDate.setOnClickListener(this);
        prescriptionSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        int id = v.getId();

        if (id == R.id.showDetailButton) {
            Intent intent = new Intent(this, DetailStatisticActivity.class);
            intent.putExtra("start", startDate.getText().toString());
            intent.putExtra("end", endDate.getText().toString());
            intent.putExtra("prescription", prescription.get(prescriptionSpinner.getSelectedItem().toString()));
            startActivity(intent);
        }
        else if(id == R.id.okButton) {
            try {
                loadPrescription();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        else if(id == R.id.startDate) {
            DialogUtil.showDatePickerDialog(this, startDate);
        }
        else if(id == R.id.endDate) {
            DialogUtil.showDatePickerDialog(this, endDate);
        }
    }

    private void loadPrescription() throws ParseException {
        if(!TextUtils.isEmpty(startDate.getText()) && !TextUtils.isEmpty(endDate.getText())) {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("start", startDate.getText().toString());
            parameters.put("end", endDate.getText().toString());
            prescriptionService.getPrescriptionByDate(1, parameters).enqueue(new Callback<List<PrescriptionDto>>() {
                @Override
                public void onResponse(Call<List<PrescriptionDto>> call, Response<List<PrescriptionDto>> response) {
                    if (response.isSuccessful()) {
                        List<PrescriptionDto> data = response.body();
                        showPrescriptionData(data);
                        loadStatistic(null);
                    } else {
                        System.out.println("error");
                    }
                }

                @Override
                public void onFailure(Call<List<PrescriptionDto>> call, Throwable t) {
                    t.printStackTrace();
                    System.err.println("Đã xảy ra lỗi: " + t.getMessage());
                }
            });
        }
    }

    private void loadStatistic(Integer prescriptionId) {
        if(!TextUtils.isEmpty(startDate.getText()) && !TextUtils.isEmpty(endDate.getText())) {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("start", startDate.getText().toString());
            parameters.put("end", endDate.getText().toString());
            if(prescriptionId != null && prescriptionId != 0) {
                parameters.put("prescription", String.valueOf(prescriptionId));
            }
            statisticService.getStatistic(1, parameters).enqueue(new Callback<Statistic>() {
                @Override
                public void onResponse(Call<Statistic> call, Response<Statistic> response) {
                    if (response.isSuccessful()) {
                        Statistic data = response.body();
                        showStatistic(data);
                    } else {
                        System.out.println("error");
                    }
                }

                @Override
                public void onFailure(Call<Statistic> call, Throwable t) {
                    t.printStackTrace();
                    System.err.println("Đã xảy ra lỗi: " + t.getMessage());
                }
            });
        }
    }

    private void showStatistic(Statistic data) {
        if(data == null || (data.getForget() == 00 && data.getLate() == 0 && data.getOnTime() == 0)) {
            chart.setVisibility(View.GONE);
            emptyChartText.setVisibility(View.VISIBLE);
        }
        else {
            Integer sum = data.getForget() + data.getLate() + data.getOnTime();
            float late = (data.getLate()*100)/sum;
            float forget = (data.getForget()*100)/sum;
            float onTime = (data.getOnTime()*100)/sum;

            List<PieEntry> entries = new ArrayList<>();
            entries.add(new PieEntry(forget));
            entries.add(new PieEntry(onTime));
            entries.add(new PieEntry(late));

            // Create dataset and add data in dataset
            PieDataSet dataSet = new PieDataSet(entries, null);

            ArrayList<Integer> colors=new ArrayList<>();
            colors.add(Color.RED);
            colors.add(Color.GREEN);
            colors.add(Color.YELLOW);

            dataSet.setColors(colors);
            dataSet.setValueTextSize(18);

            // Init data for chart
            PieData pieData = new PieData(dataSet);
            chart.setData(pieData);
            chart.invalidate(); // Update

            emptyChartText.setVisibility(View.GONE);
            chart.setVisibility(View.VISIBLE);
        }
    }

    private void showPrescriptionData(List<PrescriptionDto> data) {
        prescription.put("All", 0);
        for(PrescriptionDto item : data) {
            prescription.put(item.getDisease() + "-" + item.getId(), item.getId());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(prescriptionSpinner.getContext(), android.R.layout.simple_spinner_item, new ArrayList<>(prescription.keySet()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prescriptionSpinner.setAdapter(adapter);
        prescriptionSpinner.setSelection(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();
        loadStatistic(prescription.get(selectedItem));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
