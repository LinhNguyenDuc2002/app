package com.example.medication.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.data.DailyResponse;
import com.example.medication.data.DetailStatistic;
import com.example.medication.service.ServiceGenerator;
import com.example.medication.service.StatisticService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailStatisticActivity extends MainActivity {
    private final StatisticService statisticService = ServiceGenerator.createService(StatisticService.class);

    private LinearLayout listDetailStatistic;

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_statistic_activity);

        constructor();
        loadData();
    }

    @Override
    public void constructor() {
        super.constructor();

        listDetailStatistic = findViewById(R.id.listDetailStatistic);

        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        int id = v.getId();

        if (id == R.id.backButton) {
            finish();
        }
    }

    private void loadData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("start", extras.getString("start"));
            parameters.put("end", extras.getString("end"));
            if(extras.getInt("prescription") != 0) {
                parameters.put("prescription", String.valueOf(extras.getInt("prescription")));
            }

            statisticService.getDetailStatistic(1, parameters).enqueue(new Callback<List<DetailStatistic>>() {
                @Override
                public void onResponse(Call<List<DetailStatistic>> call, Response<List<DetailStatistic>> response) {
                    if (response.isSuccessful()) {
                        List<DetailStatistic> data = response.body();
                        showData(data);
                    } else {
                        System.out.println("error");
                    }
                }

                @Override
                public void onFailure(Call<List<DetailStatistic>> call, Throwable t) {
                    t.printStackTrace();
                    System.err.println("Đã xảy ra lỗi: " + t.getMessage());
                }
            });
        }
    }

    private void showData(List<DetailStatistic> data) {
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        layout.bottomMargin = 10;

        TableRow.LayoutParams linearLayout = new TableRow.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                100
        );
        linearLayout.setMargins(0, 0, 0, 10);

        TableRow.LayoutParams textViewLayout = new TableRow.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                2
        );

        for(DetailStatistic item : data) {
            LinearLayout detail = new LinearLayout(listDetailStatistic.getContext());
            detail.setOrientation(LinearLayout.VERTICAL);
            detail.setLayoutParams(layout);

            TextView textViewPrescription = new TextView(detail.getContext());
            textViewPrescription.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 50));
            textViewPrescription.setText(item.getDisease() + "-" + item.getId().toString());
            textViewPrescription.setTextSize(18);
            textViewPrescription.setTypeface(null, Typeface.BOLD);
            textViewPrescription.setBackground(new ColorDrawable(getResources().getColor(R.color.white)));
            textViewPrescription.setGravity(Gravity.CENTER_VERTICAL);
            textViewPrescription.setPadding(5, 0, 0, 0);

            for(DailyResponse dailyResponse : item.getDailyResponses()) {
                TableRow tableRow = new TableRow(detail.getContext());
                tableRow.setLayoutParams(linearLayout);
                if(dailyResponse.getStatus() == 0) {
                    tableRow.setBackground(new ColorDrawable(getResources().getColor(R.color.cadmium_green_color)));
                }
                else if(dailyResponse.getStatus() == 1) {
                    tableRow.setBackground(new ColorDrawable(getResources().getColor(R.color.orange_color)));
                }
                else if(dailyResponse.getStatus() == 2) {
                    tableRow.setBackground(new ColorDrawable(getResources().getColor(R.color.dark_red_color)));
                }

                TextView textViewName = new TextView(tableRow.getContext());
                textViewName.setLayoutParams(new TableRow.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        5
                ));
                textViewName.setText(dailyResponse.getName());
                textViewName.setTextSize(18);
                textViewName.setTextColor(Color.WHITE);
                textViewName.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
                textViewName.setPadding(5, 0, 0, 0);

                TextView textViewQuantity = new TextView(tableRow.getContext());
                textViewQuantity.setLayoutParams(textViewLayout);
                textViewQuantity.setText(dailyResponse.getQuantity().toString() + " " + dailyResponse.getUnit());
                textViewQuantity.setTextSize(18);
                textViewQuantity.setTextColor(Color.WHITE);
                textViewQuantity.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

                TextView textViewDate = new TextView(tableRow.getContext());
                textViewDate.setLayoutParams(textViewLayout);
                textViewDate.setText(dailyResponse.getTime());
                textViewDate.setTextSize(14);
                textViewDate.setTextColor(Color.WHITE);
                textViewDate.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);

                detail.addView(textViewPrescription);
                detail.addView(tableRow);
                tableRow.addView(textViewName);
                tableRow.addView(textViewQuantity);
                tableRow.addView(textViewDate);
            }

            listDetailStatistic.addView(detail);
        }
    }
}
