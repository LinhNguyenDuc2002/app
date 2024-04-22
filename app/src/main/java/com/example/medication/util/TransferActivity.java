package com.example.medication.util;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public final class TransferActivity extends AppCompatActivity {
    public static void transferActivity(Context context, Class<?> activity) {
        Intent intent = new Intent(context, activity);
        context.startActivity(intent);
    }

    public static void transferActivityWithId(Context context, Class<?> activity, Integer id) {
        Intent intent = new Intent(context, activity);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }
    public static void transferActivityWithText(Context context, Class<?> activity, String text) {
        Intent intent = new Intent(context, activity);
        intent.putExtra("text", text);
        context.startActivity(intent);
    }
}
