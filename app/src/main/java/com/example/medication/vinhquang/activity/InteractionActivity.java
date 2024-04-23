package com.example.medication.vinhquang.activity;

import static com.example.medication.util.TransferActivity.transferActivityWithId;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.service.ServiceGenerator;
import com.example.medication.vinhquang.api.ApiService;
import com.example.medication.vinhquang.data.SearchResponse;
import com.example.medication.vinhquang.util.GlobalValues;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InteractionActivity extends MainActivity {
    private final ApiService api = ServiceGenerator.createService(ApiService.class);
    Integer id;
    GlobalValues globalValues = GlobalValues.getInstance();
    LinearLayout rootLayout;
    List<SearchResponse> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.interaction_activity);

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


        id = getIntent().getIntExtra("id", 0);
        rootLayout = findViewById(R.id.noti);
        getDetail();
    }

    public void getDetail() {
        api.getAllInteraction(id).enqueue(new Callback<List<SearchResponse>>() {

            @Override
            public void onResponse(Call<List<SearchResponse>> call, Response<List<SearchResponse>> response) {
                if (response.isSuccessful()) {
                    data = response.body();

                    data.forEach(d -> {
                        renderLayout(d);
                    });

                } else {
                    System.out.println("error get old");
                }
            }

            @Override
            public void onFailure(Call<List<SearchResponse>> call, Throwable t) {
                t.printStackTrace();
                System.err.println("Đã xảy ra lỗi: " + t.getMessage());
            }

        });
    }

    public void renderLayout(SearchResponse d) {
        rootLayout.addView(createItem(d.getName(), d.getDescription(), d.getId()));
    }

    public LinearLayout createItem(String title, String content, Integer _id) {
        LinearLayout medicationNameLayout = new LinearLayout(this);
        medicationNameLayout.setTag(R.id.id, _id);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        medicationNameLayout.setLayoutParams(params);
        medicationNameLayout.setOrientation(LinearLayout.VERTICAL);
        medicationNameLayout.setPadding(20, 20, 20, 20);

        TextView medicationNameTitle = new TextView(this);
        medicationNameTitle.setLayoutParams(params);
        medicationNameTitle.setText(title);
        medicationNameTitle.setTextSize(25);
        medicationNameTitle.setTextColor(Color.parseColor("#008066"));
        medicationNameLayout.addView(medicationNameTitle);

        TextView medicationNameContent = new TextView(this);
        medicationNameContent.setLayoutParams(params);
        medicationNameContent.setText(content);
        medicationNameContent.setTextSize(20);
        medicationNameContent.setPadding(30, 0, 30, 0);
        medicationNameContent.setTextColor(Color.parseColor("#4d4d4d"));
        medicationNameLayout.addView(medicationNameContent);

        medicationNameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idValue = (int) v.getTag(R.id.id);
                System.out.println("xxxxxxxxxxxx: " + idValue);

                transferActivityWithId(InteractionActivity.this, MedicationActivity.class, idValue);
            }
        });

        return medicationNameLayout;
    }
}













