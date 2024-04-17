package com.example.medication.data;

import java.io.Serializable;
import java.util.List;

public class Question {
    private Integer id;

    private String content;

    private List<Answer> answers;

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public List<Answer> getAnswers() {
        return answers;
    }
}
