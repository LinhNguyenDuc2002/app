package com.example.medication.vinhquang.activity;

import static com.example.medication.util.TransferActivity.transferActivityWithId;
import static com.example.medication.util.TransferActivity.transferActivityWithText;
import static com.example.medication.vinhquang.util.DateTimeUtil.dateTimeToString;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.medication.vinhquang.data.DialogResponse;
import com.example.medication.vinhquang.util.GlobalValues;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends MainActivity {
    GlobalValues globalValues = GlobalValues.getInstance();
    private final ApiService api = ServiceGenerator.createService(ApiService.class);
    List<DialogResponse> data;
    LinearLayout rootLayout;
    EditText searchPeople;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        constructor();
    }

    @Override
    public void constructor() {
        super.constructor();

        rootLayout = findViewById(R.id.noti);
        getAllDialogs();

        searchPeople = findViewById(R.id.search);
        send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferActivityWithText(ChatActivity.this, SearchDialogActivity.class, searchPeople.getText().toString());
            }
        });
    }

    public void getAllDialogs() {
        api.getAllDialog(globalValues.getUserId(), globalValues.getRole()).enqueue(new Callback<List<DialogResponse>>() {

            @Override
            public void onResponse(Call<List<DialogResponse>> call, Response<List<DialogResponse>> response) {
                if (response.isSuccessful()) {
                    data = response.body();
                    System.out.println("vinhquang .... " + data.size());

                    data.forEach(d -> {
                        render(d);
                    });
                } else {
                    System.out.println("error get old");
                }
            }

            @Override
            public void onFailure(Call<List<DialogResponse>> call, Throwable t) {
                t.printStackTrace();
                System.err.println("Đã xảy ra lỗi: " + t.getMessage());
            }

        });
    }

    public void render(DialogResponse d) {
        LinearLayout parentLayout = new LinearLayout(this);
        parentLayout.setTag(R.id.id, d.getId());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0,0,0,20);
        parentLayout.setLayoutParams(layoutParams);
        parentLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout userLayout = new LinearLayout(this);
        userLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        userLayout.setOrientation(LinearLayout.HORIZONTAL);
        userLayout.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout imageLayout = new LinearLayout(this);
        LinearLayout.LayoutParams imageLayoutParams = new LinearLayout.LayoutParams(
                200,
                200);
        imageLayoutParams.setMargins(30, 30, 30, 30);
        imageLayout.setLayoutParams(imageLayoutParams);
        imageLayout.setOrientation(LinearLayout.HORIZONTAL);

        Button userButton = new Button(this);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        userButton.setLayoutParams(buttonParams);
        userButton.setBackgroundResource(R.color.transparent);
        userButton.setForeground(getDrawable(R.drawable.user));

        imageLayout.addView(userButton);

        LinearLayout infoLayout = new LinearLayout(this);
        infoLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        infoLayout.setOrientation(LinearLayout.VERTICAL);

        TextView nameTextView = new TextView(this);
        nameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        nameTextView.setTypeface(null, Typeface.BOLD);
        String fullName = d.getDoctor().getFullName();
        if(globalValues.getRole() == 1) {
            fullName = d.getPatient().getFullName();
        }
        nameTextView.setText(fullName);
        nameTextView.setTextColor(Color.parseColor("#000000"));

        TextView timeTextView = new TextView(this);
        timeTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        timeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        timeTextView.setTypeface(null, Typeface.ITALIC);
        timeTextView.setText(dateTimeToString(d.getUpdatedAt()));
        timeTextView.setTextColor(Color.parseColor("#000000"));

        infoLayout.addView(nameTextView);
        infoLayout.addView(timeTextView);

        userLayout.addView(imageLayout);
        userLayout.addView(infoLayout);

        parentLayout.addView(userLayout);

        View lineView = new View(this);
        lineView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,10));
        lineView.setBackgroundColor(Color.parseColor("#00b38f"));

        parentLayout.addView(lineView);

        rootLayout.addView(parentLayout);


        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idValue = (int) v.getTag(R.id.id);
                transferActivityWithId(ChatActivity.this, DialogActivity.class, idValue);
            }
        });
    }
}










