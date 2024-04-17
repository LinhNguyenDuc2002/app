package com.example.medication.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.data.Answer;
import com.example.medication.data.Question;
import com.example.medication.service.AssessmentService;
import com.example.medication.service.ServiceGenerator;
import com.example.medication.util.QuestionUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthAssessmentActivity extends MainActivity {
    private LinearLayout listQuestionLayout;
    private final AssessmentService assessmentService = ServiceGenerator.createService(AssessmentService.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadListQuestion();
        setContentView(R.layout.health_assessment_activity);

        constructor();
    }

    @Override
    public void constructor() {
        super.constructor();

        listQuestionLayout = findViewById(R.id.listQuestionLayout);
    }

    private void loadListQuestion() {
        assessmentService.getListQuestions().enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if (response.isSuccessful()) {
                    List<Question> data = response.body();
                    showData(data);
                } else {
                    System.out.println("error");
                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                t.printStackTrace();
                System.err.println("Đã xảy ra lỗi: " + t.getMessage());
            }
        });
    }

    private void showData(List<Question> questions) {
        int i = 1;
        LinearLayout.LayoutParams linearLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // chiều rộng
                LinearLayout.LayoutParams.MATCH_PARENT // chiều cao
        );
        linearLayout.setMargins(0, 10, 0, 10);

        RadioGroup.LayoutParams radioGroupLayout = new RadioGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        for (Question question : questions) {
            LinearLayout questionLayout = new LinearLayout(this.listQuestionLayout.getContext());
            questionLayout.setOrientation(LinearLayout.VERTICAL);
            questionLayout.setLayoutParams(linearLayout);

            TextView textView = new TextView(questionLayout.getContext());
            textView.setText(QuestionUtil.markQuestion(i, question.getContent()));
            textView.setTextSize(16);

            RadioGroup radioGroup = new RadioGroup(questionLayout.getContext());
            radioGroup.setLayoutParams(radioGroupLayout);
            question.getAnswers().stream()
                    .forEach(answer -> {
                        RadioButton radioButton = new RadioButton(radioGroup.getContext());
                        radioButton.setText(answer.getContent());

                        radioGroup.addView(radioButton);
                    });

            questionLayout.addView(textView);
            questionLayout.addView(radioGroup);
            this.listQuestionLayout.addView(questionLayout);

            i++;
        }
    }
}
