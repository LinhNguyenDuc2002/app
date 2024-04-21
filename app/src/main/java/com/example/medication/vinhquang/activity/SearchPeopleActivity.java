package com.example.medication.vinhquang.activity;

import android.graphics.Color;
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
import androidx.core.content.ContextCompat;
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

public class SearchPeopleActivity extends MainActivity {
    private final ApiService api = ServiceGenerator.createService(ApiService.class);
    GlobalValues globalValues = GlobalValues.getInstance();
    EditText searchPeople;
    Button send;
    LinearLayout rootLayout;
    List<SearchResponse> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_people_activity);

        constructor();
    }

    @Override
    public void constructor() {
        super.constructor();

        rootLayout = findViewById(R.id.noti);
        Button returnButton = findViewById(R.id.returnBtn);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        searchPeople = findViewById(R.id.searchPeople);
        send = findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(globalValues.getSearchType() == 0) {
                    searchDoctor();
                } else {
                    searchPatient();
                }
            }
        });
    }

    public void searchDoctor() {
        api.searchDoctor(searchPeople.getText().toString()).enqueue(new Callback<List<SearchResponse>>() {
            @Override
            public void onResponse(Call<List<SearchResponse>> call, Response<List<SearchResponse>> response) {
                if (response.isSuccessful()) {
                    data = response.body();

                    data.forEach(d -> {
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

    public void searchPatient() {
        api.searchPatient(globalValues.getUserId(), searchPeople.getText().toString()).enqueue(new Callback<List<SearchResponse>>() {
            @Override
            public void onResponse(Call<List<SearchResponse>> call, Response<List<SearchResponse>> response) {
                if (response.isSuccessful()) {
                    data = response.body();

                    data.forEach(d -> {
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
        LinearLayout parentLayout = new LinearLayout(this);
        parentLayout.setTag(R.id.id, s.getId());
        parentLayout.setTag(R.id.name, s.getName());
        if(s.getDescription() != null) {
            parentLayout.setTag(R.id.des, s.getDescription());
        } else {
            parentLayout.setTag(R.id.des, "avatar");
        }
        parentLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        parentLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout firstChildLayout = new LinearLayout(this);
        firstChildLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        firstChildLayout.setOrientation(LinearLayout.HORIZONTAL);
        firstChildLayout.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout buttonLayout = new LinearLayout(this);
        buttonLayout.setLayoutParams(new LinearLayout.LayoutParams(
                200, 200));
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLayout.setPadding(30, 30, 30, 30);

        Button userButton = new Button(this);
        userButton.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        userButton.setBackground(ContextCompat.getDrawable(this, R.color.transparent));
        userButton.setForeground(ContextCompat.getDrawable(this, R.drawable.user));

        buttonLayout.addView(userButton);

        LinearLayout textLayout = new LinearLayout(this);
        textLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        textLayout.setOrientation(LinearLayout.VERTICAL);

        TextView nameTextView = new TextView(this);
        nameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        nameTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        nameTextView.setText(s.getName());
        nameTextView.setTextColor(Color.parseColor("#000000"));

        textLayout.addView(nameTextView);

        firstChildLayout.addView(buttonLayout);
        firstChildLayout.addView(textLayout);

        parentLayout.addView(firstChildLayout);

        View dividerView = new View(this);
        dividerView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                10));
        dividerView.setBackgroundColor(Color.parseColor("#00b38f"));

        parentLayout.addView(dividerView);

        rootLayout.addView(parentLayout);

        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idValue = (int) v.getTag(R.id.id);
                String name = String.valueOf(v.getTag(R.id.name));
                String des = String.valueOf(v.getTag(R.id.des));
                SearchResponse people = new SearchResponse();
                people.setId(idValue);
                people.setName(name);
                people.setDescription(des);

                if(globalValues.getSearchType() == 0) {
                    globalValues.setDocterSearch(people);
                } else {
                    globalValues.setPatientSearch(people);
                }

                finish();
            }
        });

    }
}