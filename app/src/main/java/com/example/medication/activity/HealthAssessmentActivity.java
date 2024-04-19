package com.example.medication.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Space;
import android.widget.TextView;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.data.AssessmentResult;
import com.example.medication.data.Question;
import com.example.medication.data.request.AnswerForm;
import com.example.medication.service.AssessmentService;
import com.example.medication.service.ServiceGenerator;
import com.example.medication.util.StringUtil;
import com.example.medication.util.TransferActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HealthAssessmentActivity extends MainActivity {
    private final AssessmentService assessmentService = ServiceGenerator.createService(AssessmentService.class);

    private LinearLayout listQuestionLayout;
    private Button resultButton;
    private Button healthStatisticButton;
    private TextView bmiResult;
    private TextView finalResult;

    private List<RadioGroup> radioGroups;
    private List<EditText> editTexts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_assessment_activity);

        constructor();
        loadListQuestion();
    }

    @Override
    public void constructor() {
        super.constructor();

        radioGroups = new ArrayList<>();
        editTexts = new ArrayList<>();

        listQuestionLayout = findViewById(R.id.listQuestionLayout);

        resultButton = findViewById(R.id.resultButton);
        healthStatisticButton =findViewById(R.id.healthStatisticButton);
        bmiResult = findViewById(R.id.bmiResult);
        finalResult = findViewById(R.id.finalResult);

        resultButton.setOnClickListener(this);
        healthStatisticButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        int id = v.getId();

        if (id == R.id.resultButton)
            postAnswerForm();
        else if(id == R.id.healthStatisticButton)
            TransferActivity.transferActivity(this, HealthStatisticActivity.class);
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

    private AnswerForm packData() {
        AnswerForm answerForm = new AnswerForm();

        List<Integer> answerIds = new ArrayList<>();
        for (RadioGroup radioGroup : radioGroups) {
            int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

            if (selectedRadioButtonId != -1) {
                RadioButton selectedRadioButton = radioGroup.findViewById(selectedRadioButtonId);
                answerIds.add(selectedRadioButton.getId());
            }
        }
        answerForm.setAnswerIds(answerIds);

        for(EditText editText : editTexts) {
            if(editText.getId() == 0) {
                answerForm.setHeight(Double.valueOf(editText.getText().toString()));
            }
            else {
                answerForm.setWeight(Double.valueOf(editText.getText().toString()));
            }
        }

        return answerForm;
    }

    private void postAnswerForm() {
        assessmentService.postAnswer(1, packData()).enqueue(new Callback<AssessmentResult>() {
            @Override
            public void onResponse(Call<AssessmentResult> call, Response<AssessmentResult> response) {
                if (response.isSuccessful()) {
                    AssessmentResult data = response.body();
                    showResult(data);
                } else {
                    System.out.println("error");
                }
            }

            @Override
            public void onFailure(Call<AssessmentResult> call, Throwable t) {
                t.printStackTrace();
                System.err.println("Đã xảy ra lỗi: " + t.getMessage());
            }
        });
    }

    private void showResult(AssessmentResult assessmentResult) {
        bmiResult.setText("Result from BMI: " + StringUtil.transferToMessage(assessmentResult.getBmiStatus()));
        finalResult.setText("Final result: " +
                StringUtil.transferToMessage(assessmentResult.getHealthStatus()) +
                " (" + assessmentResult.getScore() + "/100" + ")" );
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
        radioGroupLayout.setMargins(0,5,0,5);

        LinearLayout.LayoutParams customLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                60
        );

        LinearLayout.LayoutParams spaceLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        spaceLayout.width=60;

        for (Question question : questions) {
            LinearLayout questionLayout = new LinearLayout(this.listQuestionLayout.getContext());
            questionLayout.setOrientation(LinearLayout.VERTICAL);
            questionLayout.setLayoutParams(linearLayout);

            TextView textView = new TextView(questionLayout.getContext());
            textView.setText(StringUtil.markQuestionNumber(i, question.getContent()));
            textView.setLineSpacing(8, 1);
            textView.setTextSize(18);

            questionLayout.addView(textView);

            if(question.getId() == 1) {
                LinearLayout enterLayout = new LinearLayout(this.listQuestionLayout.getContext());
                enterLayout.setOrientation(LinearLayout.HORIZONTAL);
                enterLayout.setLayoutParams(linearLayout);
                enterLayout.setGravity(Gravity.CENTER);

                TextView textViewHeight = new TextView(enterLayout.getContext());
                textViewHeight.setText("Chiều cao");
                textViewHeight.setTextSize(18);
                textViewHeight.setLayoutParams(customLayout);

                TextView textViewWidth = new TextView(enterLayout.getContext());
                textViewWidth.setText("Cân nặng");
                textViewWidth.setTextSize(18);
                textViewWidth.setLayoutParams(customLayout);

                EditText editTextHeight = new EditText(enterLayout.getContext());
                editTextHeight.setHeight(60);
                editTextHeight.setWidth(200);
                editTextHeight.setMaxEms(10);
                editTextHeight.setInputType(InputType.TYPE_CLASS_NUMBER);
                editTextHeight.setHint("cm");
                editTextHeight.setId(0);

                EditText editTextWidth = new EditText(enterLayout.getContext());
                editTextWidth.setHeight(60);
                editTextWidth.setWidth(200);
                editTextWidth.setMaxEms(10);
                editTextWidth.setInputType(InputType.TYPE_CLASS_NUMBER);
                editTextWidth.setHint("kg");
                editTextWidth.setId(1);

                Space space = new Space(enterLayout.getContext());
                space.setLayoutParams(spaceLayout);

                enterLayout.addView(textViewHeight);
                enterLayout.addView(editTextHeight);
                enterLayout.addView(space);
                enterLayout.addView(textViewWidth);
                enterLayout.addView(editTextWidth);
                questionLayout.addView(enterLayout);
                editTexts.addAll(Arrays.asList(editTextHeight, editTextWidth));
            }
            else {
                RadioGroup radioGroup = new RadioGroup(questionLayout.getContext());
                radioGroup.setLayoutParams(radioGroupLayout);
                radioGroup.setId(question.getId());
                question.getAnswers().stream()
                        .forEach(answer -> {
                            RadioButton radioButton = new RadioButton(radioGroup.getContext());
                            radioButton.setText(answer.getContent());
                            radioButton.setTextSize(18);
                            radioButton.setLineSpacing(8, 1);
                            radioButton.setId(answer.getId());

                            radioGroup.addView(radioButton);
                        });
                questionLayout.addView(radioGroup);
                radioGroups.add(radioGroup);
            }

            this.listQuestionLayout.addView(questionLayout);
            i++;
        }
    }
}
