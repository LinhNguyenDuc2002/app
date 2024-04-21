package com.example.medication.vinhquang.activity;

import static com.example.medication.util.TransferActivity.transferActivityWithId;

import android.graphics.Color;
import android.os.Bundle;
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
import com.example.medication.vinhquang.data.SearchResponse;
import com.example.medication.vinhquang.util.GlobalValues;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMedActivity extends MainActivity {

    private final ApiService api = ServiceGenerator.createService(ApiService.class);
    GlobalValues globalValues = GlobalValues.getInstance();
    EditText search;
    Button send;
    LinearLayout rootLayout;
    List<SearchResponse> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_med_activity);

        constructor();
    }

    @Override
    public void constructor() {
        super.constructor();

        rootLayout = findViewById(R.id.noti);

        search = findViewById(R.id.search);
        send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMed();
            }
        });
    }

    public void searchMed() {
        api.searchMed(search.getText().toString()).enqueue(new Callback<List<SearchResponse>>() {
            @Override
            public void onResponse(Call<List<SearchResponse>> call, Response<List<SearchResponse>> response) {
                if (response.isSuccessful()) {
                    data = response.body();
                    rootLayout.removeAllViews();

                    data.forEach(d -> {
                        System.out.println(d.getName());
                        render(d);
                    });
                } else {
                    System.out.println("error");
                }
            }

            @Override
            public void onFailure(Call<List<SearchResponse>> call, Throwable t) {
                t.printStackTrace();
                System.err.println("Đã xảy ra lỗi: " + t.getMessage());
            }
        });
    }

    public void render(SearchResponse s) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setTag(R.id.id, s.getId());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(30, 30, 30, 0);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setText(s.getName());
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(0, 0, 0, 20);

        View view = new View(this);
        view.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                10));
        view.setBackgroundColor(Color.parseColor("#00b38f"));

        linearLayout.addView(textView);
        linearLayout.addView(view);


        rootLayout.addView(linearLayout);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy id và code từ tag của LinearLayout
                int idValue = (int) v.getTag(R.id.id);
                System.out.println("xxxxxxxxxxxx: " + idValue);

                transferActivityWithId(SearchMedActivity.this, MedicationActivity.class, idValue);
            }
        });
    }
}









