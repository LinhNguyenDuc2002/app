package com.example.medication.vinhquang.activity;

import static com.example.medication.util.TransferActivity.transferActivityWithId;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.medication.R;
import com.example.medication.activity.DetailNotificationActivity;
import com.example.medication.activity.base.MainActivity;
import com.example.medication.vinhquang.data.NotificationFirebase;
import com.example.medication.vinhquang.util.GlobalValues;

import java.util.List;

public class NotificationActivity extends MainActivity {
    public final GlobalValues globalValues = GlobalValues.getInstance();
    LinearLayout rootLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);

        constructor();
    }

    public void constructor() {
        super.constructor();
        rootLayout = findViewById(R.id.noti);

        showNotifications();
    }

    public void showNotifications() {

        List<NotificationFirebase> list = globalValues.getNotificationList();
        list.forEach(n -> {
            System.out.println(n.getTitle() + ": " + n.getMessage());
            renderNoti(this, rootLayout, n);
        });
    }

    public void renderNoti(Context context, LinearLayout rootLayout, NotificationFirebase n) {
        // Khởi tạo LinearLayout cha
        LinearLayout parentLayout = new LinearLayout(this);
        parentLayout.setTag(R.id.id, n.getId());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(50, 30, 50, 30);
        parentLayout.setLayoutParams(layoutParams);

        parentLayout.setOrientation(LinearLayout.HORIZONTAL);
        parentLayout.setGravity(Gravity.CENTER_VERTICAL);
        if(n.getStatus() == 0) {
            parentLayout.setBackgroundResource(R.drawable.rounded_corner);
        } else {
            parentLayout.setBackgroundResource(R.drawable.rounded_corner_gray);
        }
        parentLayout.setPadding(20, 20, 20, 20);

        // Khởi tạo ImageView
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                200, 200);
        imageView.setLayoutParams(imageParams);
        imageView.setImageResource(R.drawable.baseline_notifications_24);
        imageView.setColorFilter(ContextCompat.getColor(this, R.color.main_green));
        imageView.setVisibility(View.VISIBLE);
        imageView.setPadding(20, 20, 20, 20);

        // Thêm ImageView vào LinearLayout cha
        parentLayout.addView(imageView);

        // Khởi tạo LinearLayout con
        LinearLayout childLayout = new LinearLayout(this);
        LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        childLayout.setLayoutParams(childParams);
        childLayout.setOrientation(LinearLayout.VERTICAL);
        childLayout.setGravity(Gravity.CENTER_VERTICAL);
        childLayout.setPadding(20, 20, 20, 20);

        // Khởi tạo TextView 1
        TextView textView1 = new TextView(this);
        LinearLayout.LayoutParams textParams1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                150);
        textView1.setLayoutParams(textParams1);
        textView1.setText(n.getTitle());
        textView1.setTextSize(20);
        textView1.setTypeface(null, Typeface.BOLD);

        // Khởi tạo TextView 2
        TextView textView2 = new TextView(this);
        LinearLayout.LayoutParams textParams2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        textView2.setLayoutParams(textParams2);
        textView2.setText(n.getMessage());
        textView2.setTextSize(18);

        // Thêm TextView vào LinearLayout con
        childLayout.addView(textView1);
        childLayout.addView(textView2);

        // Thêm LinearLayout con vào LinearLayout cha
        parentLayout.addView(childLayout);

        // Thêm LinearLayout cha vào root layout (ví dụ, một ConstraintLayout)
        rootLayout.addView(parentLayout);


        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy id và code từ tag của LinearLayout
                int idValue = (int) v.getTag(R.id.id);
                System.out.println("xxxxxxxxxxxx: " + idValue);
                if(n.getType() == 0) {
                    transferActivityWithId(NotificationActivity.this, DetailNotificationActivity.class, idValue);
                } else if(n.getType() == 1) {
                    transferActivityWithId(NotificationActivity.this, AppNotiActivity.class, idValue);
                } else if(n.getType() == 2) {
                    transferActivityWithId(NotificationActivity.this, ConfirmNotiActivity.class, idValue);
                } else if(n.getType() == 3) {
                    transferActivityWithId(NotificationActivity.this, ResultNotiActivity.class, idValue);
                }
            }
        });
    }
}
