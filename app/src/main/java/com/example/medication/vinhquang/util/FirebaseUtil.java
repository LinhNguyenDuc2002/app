package com.example.medication.vinhquang.util;

import static android.content.ContentValues.TAG;


import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.medication.service.ServiceGenerator;
import com.example.medication.vinhquang.api.ApiService;
import com.example.medication.vinhquang.data.NotificationFirebase;
import com.example.medication.vinhquang.data.NotificationResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirebaseUtil {
    private static final ApiService api = ServiceGenerator.createService(ApiService.class);
    private static GlobalValues globalValues = GlobalValues.getInstance();
    public static void getToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        globalValues.setToken(token);
                        Log.d(TAG, token);
                    }
                });
    }

    public static void setTokenToUser() {
        globalValues.setUserId(1);
        globalValues.setRole(0);
        api.setToken(globalValues.getUserId(), globalValues.getToken()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String data = response.body();
                    System.out.println(data);
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

    public static void getListOldNoti() {
        api.getListOldNoti(globalValues.getUserId()).enqueue(new Callback<List<NotificationResponse>>() {
            @Override
            public void onResponse(Call<List<NotificationResponse>> call, Response<List<NotificationResponse>> response) {
                if (response.isSuccessful()) {
                    List<NotificationResponse> data = response.body();
                    List<NotificationFirebase> notiList = data.stream().map(d -> {
                        NotificationFirebase notification = new NotificationFirebase();
                        notification.setId(d.getId());
                        notification.setType(d.getType());
                        notification.setMessage(d.getMessage());
                        notification.setTitle(d.getTitle());
                        notification.setStatus(d.getStatus());
                        return notification;
                    }).collect(Collectors.toList());

                    globalValues.setNotificationList(notiList);
                    System.out.println(notiList.size());
                } else {
                    System.out.println("error get old");
                }
            }

            @Override
            public void onFailure(Call<List<NotificationResponse>> call, Throwable t) {
                t.printStackTrace();
                System.err.println("Đã xảy ra lỗi: " + t.getMessage());
            }
        });
    }
}
