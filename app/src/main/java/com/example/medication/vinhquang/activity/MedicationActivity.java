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
import com.example.medication.vinhquang.data.MedicationResponse;
import com.example.medication.vinhquang.util.GlobalValues;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicationActivity extends MainActivity {

    private final ApiService api = ServiceGenerator.createService(ApiService.class);
    Integer id;
    GlobalValues globalValues = GlobalValues.getInstance();
    LinearLayout rootLayout;
    MedicationResponse data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medication_activity);

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

        Button bugButton = findViewById(R.id.bug);
        bugButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferActivityWithId(MedicationActivity.this, InteractionActivity.class, id);
            }
        });
    }

    public void getDetail() {
        api.getOneMed(id).enqueue(new Callback<MedicationResponse>() {

            @Override
            public void onResponse(Call<MedicationResponse> call, Response<MedicationResponse> response) {
                if (response.isSuccessful()) {
                    data = response.body();
                    renderLayout();
                } else {
                    System.out.println("error get old");
                }
            }

            @Override
            public void onFailure(Call<MedicationResponse> call, Throwable t) {
                t.printStackTrace();
                System.err.println("Đã xảy ra lỗi: " + t.getMessage());
            }

        });
    }

    public void renderLayout() {
        rootLayout.addView(createItem("Medication name:", data.getName()));
        rootLayout.addView(createItem("Medication effect:", data.getEffect()));
        rootLayout.addView(createItem("Medication side-effect:", data.getSideEffect()));
        rootLayout.addView(createItem("Medication description:", data.getDescription()));
    }

    public LinearLayout createItem(String title, String content) {
        LinearLayout medicationNameLayout = new LinearLayout(this);
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

        return medicationNameLayout;
    }
}











