package com.example.medication.data.request;

import java.util.List;

public class AnswerForm {
    private Double height;

    private Double weight;

    private List<Integer> answerIds;

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setAnswerIds(List<Integer> answerIds) {
        this.answerIds = answerIds;
    }
}
