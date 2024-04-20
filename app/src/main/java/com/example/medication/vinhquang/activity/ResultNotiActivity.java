package com.example.medication.vinhquang.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.medication.R;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.vinhquang.util.GlobalValues;

public class ResultNotiActivity extends MainActivity {
    Integer id;
    GlobalValues globalValues = GlobalValues.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_noti_activity);

        constructor();
    }

    @Override
    public void constructor() {
        super.constructor();
        id = getIntent().getIntExtra("id", 0);
    }
}