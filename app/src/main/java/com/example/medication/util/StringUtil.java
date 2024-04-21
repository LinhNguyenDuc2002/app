package com.example.medication.util;

public final class StringUtil {
    public static String markQuestionNumber(int i, String question) {
        return i + ". " + question;
    }

    public static String transferToMessage(String message) {
        String result = message.replaceAll("_", " ");
        return result.toUpperCase();
    }
}
