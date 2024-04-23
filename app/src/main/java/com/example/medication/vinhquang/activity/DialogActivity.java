package com.example.medication.vinhquang.activity;

import static com.example.medication.util.TransferActivity.transferActivity;
import static com.example.medication.vinhquang.util.DateTimeUtil.dateTimeToString;
import static com.example.medication.vinhquang.util.DateTimeUtil.toTimeString;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
import com.example.medication.vinhquang.data.DialogResponse;
import com.example.medication.vinhquang.data.MessageRequest;
import com.example.medication.vinhquang.data.MessageResponse;
import com.example.medication.vinhquang.util.GlobalValues;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogActivity extends MainActivity {
    private BroadcastReceiver receiver;
    GlobalValues globalValues = GlobalValues.getInstance();
    private final ApiService api = ServiceGenerator.createService(ApiService.class);
    DialogResponse data;
    LinearLayout rootLayout;
    Integer id;
    EditText typeMess;
    Button sendBtn;
    MessageRequest messageRequest;
    ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_activity);

        constructor();
    }

    @Override
    public void constructor() {
        super.constructor();

        receiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String message = intent.getStringExtra("message");
                System.out.println(message);
                getDialog();
            }
        };

        typeMess = findViewById(R.id.typeMess);
        sendBtn = findViewById(R.id.sendBtn);
        scroll = findViewById(R.id.scroll);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        id = getIntent().getIntExtra("id", 0);
        rootLayout = findViewById(R.id.noti);
        getDialog();
        Button returnButton = findViewById(R.id.returnBtn);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferActivity(DialogActivity.this, ChatActivity.class);
            }
        });
    }

    public void sendMessage() {
        messageRequest = new MessageRequest();
        messageRequest.setDialogId(id);
        messageRequest.setContent(typeMess.getText().toString());
        messageRequest.setStatus(0);
        messageRequest.setDateTime(dateTimeToString(new Date(System.currentTimeMillis())));
        messageRequest.setUserId(globalValues.getUserId());
        api.sendMessage(messageRequest).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String _data = response.body();

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(typeMess.getWindowToken(), 0);

                    typeMess.setVisibility(View.GONE);

                    typeMess.setVisibility(View.VISIBLE);
                    typeMess.setText("");

                } else {
                    System.out.println("error");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                System.err.println("Đã xảy ra lỗi: " + t.getMessage());
            }
        });
    }
    public void getDialog() {
        rootLayout.removeAllViews();
        api.getOneDialog(id).enqueue(new Callback<DialogResponse>() {

            @Override
            public void onResponse(Call<DialogResponse> call, Response<DialogResponse> response) {
                if (response.isSuccessful()) {
                    data = response.body();
                    System.out.println("vinhquang .... " + data.getId());

                    setHeader(data);
                    data.getMessageResponseList().forEach(m -> {
                        createItem(m);
                    });

                    scroll.post(new Runnable() {
                        @Override
                        public void run() {
                            scroll.fullScroll(View.FOCUS_DOWN);
                        }
                    });
                } else {
                    System.out.println("error get old");
                }
            }

            @Override
            public void onFailure(Call<DialogResponse> call, Throwable t) {
                t.printStackTrace();
                System.err.println("Đã xảy ra lỗi: " + t.getMessage());
            }

        });
    }

    public void setHeader(DialogResponse d) {
        Button avatar = findViewById(R.id.avatar);
        TextView fullName = findViewById(R.id.fullName);

        avatar.setForeground(getDrawable(R.drawable.user));
        fullName.setText(d.getDoctor().getFullName());
        if(globalValues.getRole() == 1) {
            fullName.setText(d.getPatient().getFullName());
        }
    }

    public void createItem(MessageResponse m) {
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 50, 0, 0);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.RIGHT);
        if(m.getUserResponse().getId() != globalValues.getUserId()) {
            linearLayout.setGravity(Gravity.LEFT);
        }

        TextView textView1 = new TextView(this);
        textView1.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        textView1.setText(m.getContent());
        textView1.setTextSize(20);
        textView1.setTextColor(Color.parseColor("#000000"));
        textView1.setPadding(40, 20, 40, 20);
        textView1.setBackgroundResource(R.drawable.message_frame1);
        if(m.getUserResponse().getId() != globalValues.getUserId()) {
            textView1.setBackgroundResource(R.drawable.message_frame2);
        }
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params1.setMargins(100, 0, 0, 0);
        if(m.getUserResponse().getId() != globalValues.getUserId()) {
            params1.setMargins(0, 0, 100, 0);
        }
        textView1.setLayoutParams(params1);

        TextView textView2 = new TextView(this);
        textView2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        textView2.setText(toTimeString(m.getDateTime()));
        textView2.setTextSize(18);
        textView2.setTextColor(Color.parseColor("#737373"));
        textView2.setPadding(40, 0, 40, 0);

        linearLayout.addView(textView1);
        linearLayout.addView(textView2);

        rootLayout.addView(linearLayout);
    }


    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("resume-mmmm............");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(receiver, new IntentFilter("ACTION_SHOW_MESSAGE"), RECEIVER_EXPORTED);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        System.out.println("pause-mmmm............");
        unregisterReceiver(receiver);
    }
}