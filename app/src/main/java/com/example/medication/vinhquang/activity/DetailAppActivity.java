package com.example.medication.vinhquang.activity;

import static com.example.medication.vinhquang.util.DateTimeUtil.toDateString;
import static com.example.medication.vinhquang.util.DateTimeUtil.toTimeString;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.service.ServiceGenerator;
import com.example.medication.vinhquang.api.ApiService;
import com.example.medication.vinhquang.data.AppointmentResponse;
import com.example.medication.vinhquang.util.GlobalValues;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailAppActivity extends MainActivity {
    private final ApiService api = ServiceGenerator.createService(ApiService.class);
    Integer id;
    GlobalValues globalValues = GlobalValues.getInstance();
    LinearLayout rootLayout;
    AppointmentResponse data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_app_activity);

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
        api.getOneApp(id).enqueue(new Callback<AppointmentResponse>() {

            @Override
            public void onResponse(Call<AppointmentResponse> call, Response<AppointmentResponse> response) {
                if (response.isSuccessful()) {
                    data = response.body();
                    renderLayout();
                } else {
                    System.out.println("error get old");
                }
            }

            @Override
            public void onFailure(Call<AppointmentResponse> call, Throwable t) {
                t.printStackTrace();
                System.err.println("Đã xảy ra lỗi: " + t.getMessage());
            }

        });
    }

    public void renderLayout() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(30, 30, 30, 30);

        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.setMargins(15, 15, 15, 15);

        Date dateTime = data.getDateTime();
        LinearLayout linearLayout1 = createItem(R.drawable.user, "Bs: " + data.getDoctor().getFullName());
        LinearLayout linearLayout2 = createItem(R.drawable.user, "Bn: " + data.getPatient().getFullName());
        LinearLayout linearLayout3 = createItem(R.drawable.date, toDateString(dateTime));
        LinearLayout linearLayout4 = createItem(R.drawable.time, toTimeString(dateTime));
        LinearLayout linearLayout5 = createItem(R.drawable.location, data.getLocation());

        rootLayout.addView(linearLayout1);
        rootLayout.addView(linearLayout2);
        rootLayout.addView(linearLayout3);
        rootLayout.addView(linearLayout4);
        rootLayout.addView(linearLayout5);

    }

    private LinearLayout createItem(int drawableRes, String text) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(30, 30, 30, 30);

        LinearLayout buttonLayout = new LinearLayout(this);
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonLayoutParams.setMargins(30, 30, 30, 30);
        buttonLayout.setLayoutParams(buttonLayoutParams);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);

        Button button = new Button(this);
        button.setLayoutParams(new LinearLayout.LayoutParams(150, 150));
        button.setBackground(ContextCompat.getDrawable(this, R.color.transparent));
        button.setForeground(ContextCompat.getDrawable(this, drawableRes));
        buttonLayout.addView(button);

        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(text);
        textView.setTextColor(Color.parseColor("#595959"));
        textView.setTextSize(30);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.addView(buttonLayout);
        linearLayout.addView(textView);

        return linearLayout;
    }


}