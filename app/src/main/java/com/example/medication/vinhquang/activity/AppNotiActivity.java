package com.example.medication.vinhquang.activity;

import static com.example.medication.vinhquang.util.DateTimeUtil.toDateString;
import static com.example.medication.vinhquang.util.DateTimeUtil.toTimeString;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
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
import com.example.medication.vinhquang.data.AppointmentNotiResponse;
import com.example.medication.vinhquang.data.NotificationFirebase;
import com.example.medication.vinhquang.util.GlobalValues;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppNotiActivity extends MainActivity {
    private final ApiService api = ServiceGenerator.createService(ApiService.class);
    Integer id;
    GlobalValues globalValues = GlobalValues.getInstance();
    LinearLayout rootLayout;
    AppointmentNotiResponse data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_noti_activity);

        constructor();
    }

    @Override
    public void constructor() {
        super.constructor();
        id = getIntent().getIntExtra("id", 0);
        rootLayout = findViewById(R.id.noti);
        getDetail();
        seen();
    }

    public void getDetail() {
        api.getAppointmentNoti(id).enqueue(new Callback<AppointmentNotiResponse>() {

            @Override
            public void onResponse(Call<AppointmentNotiResponse> call, Response<AppointmentNotiResponse> response) {
                if (response.isSuccessful()) {
                    data = response.body();
                    renderLayout();
                } else {
                    System.out.println("error get old");
                }
            }

            @Override
            public void onFailure(Call<AppointmentNotiResponse> call, Throwable t) {
                t.printStackTrace();
                System.err.println("Đã xảy ra lỗi: " + t.getMessage());
            }
        });
    }

    public void seen() {
        api.update(id).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String data = response.body();
                    System.out.println("vinhquang .... " + data);
                    List<NotificationFirebase> list = globalValues.getNotificationList();
                    list.forEach(n -> {
                        if(n.getId() == id) {
                            n.setStatus(1);
                        }
                    });
                    globalValues.setNotificationList(list);
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

    public void renderLayout() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(30, 30, 30, 30);

        TextView textView = new TextView(this);
        textView.setLayoutParams(layoutParams);
        textView.setText(data.getTitle());
        textView.setTextSize(30);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.parseColor("#000000"));

        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.setMargins(15, 15, 15, 15); // Set margin

        String sender = data.getAppointmentResponse().getDoctor().getFullName();
        if(data.getRole() == 1) {
            sender = data.getAppointmentResponse().getPatient().getFullName();
        }
        Date dateTime = data.getAppointmentResponse().getDateTime();
        LinearLayout linearLayout1 = createItem(R.drawable.user, sender);
        LinearLayout linearLayout2 = createItem(R.drawable.date, toDateString(dateTime));
        LinearLayout linearLayout3 = createItem(R.drawable.time, toTimeString(dateTime));
        LinearLayout linearLayout4 = createItem(R.drawable.location, data.getAppointmentResponse().getLocation());

        rootLayout.addView(textView);
        rootLayout.addView(linearLayout1);
        rootLayout.addView(linearLayout2);
        rootLayout.addView(linearLayout3);
        rootLayout.addView(linearLayout4);
    }

    private LinearLayout createItem(int drawableRes, String text) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(30, 30, 30, 30); // Set margin

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