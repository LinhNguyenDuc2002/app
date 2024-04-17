package com.example.medication.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.util.TransferActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class StatisticActivity extends MainActivity implements OnChartValueSelectedListener {
    private Button showDetailButton;

    private PieChart chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic_activity);

        constructor();
    }

    @Override
    public void constructor() {
        super.constructor();

        chart = (PieChart) findViewById(R.id.chart);
        chart.setRotationEnabled(true);
        chart.setHoleRadius(30f);
        chart.setTransparentCircleAlpha(0);
        chart.setCenterTextSize(20);
        chart.setDrawEntryLabels(false);

        setDataChart();

        chart.setOnChartValueSelectedListener(this);

        showDetailButton = findViewById(R.id.showDetailButton);
        showDetailButton.setOnClickListener(this);
    }

    public void setDataChart() {
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(25f));
        entries.add(new PieEntry(35f));
        entries.add(new PieEntry(40f));

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

        if (id == R.id.showDetailButton)
            TransferActivity.transferActivity(this, DetailStatisticActivity.class);
    }
}
