package com.example.medication.vinhquang;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.medication.vinhquang.data.NotificationFirebase;
import com.example.medication.vinhquang.util.GlobalValues;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.List;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);
    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        super.onMessageReceived(remoteMessage);
        String check = remoteMessage.getData().get("check");
        if(check.equals("noti")) {
            receivedNoti(remoteMessage);
        } else if(check.equals("mess")) {
            receivedMess(remoteMessage);
        }
    }

    public void receivedNoti(RemoteMessage remoteMessage) {
        Intent intent = new Intent("ACTION_SHOW_NOTIFICATION");
        intent.putExtra("notification", "Bạn có thông báo mới!!!");
        sendBroadcast(intent);

        Log.d(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage.getNotification() != null) {
            NotificationFirebase notification = new NotificationFirebase();
            notification.setId(Integer.parseInt(remoteMessage.getData().get("id")));
            notification.setTitle(remoteMessage.getNotification().getTitle());
            notification.setMessage(remoteMessage.getNotification().getBody());
            notification.setType(Integer.parseInt(remoteMessage.getData().get("type")));
            notification.setStatus(Integer.parseInt(remoteMessage.getData().get("status")));
            GlobalValues globalValues = GlobalValues.getInstance();

            List<NotificationFirebase> list = globalValues.getNotificationList();
            list.add(0, notification);
            globalValues.setNotificationList(list);

            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getTitle());

        }
    }

    public void receivedMess(RemoteMessage remoteMessage) {
        Log.d(TAG, "CHATTTTTTTTTTTTTTTT!!!!!!!!!!");
        Intent intent = new Intent("ACTION_SHOW_MESSAGE");
        intent.putExtra("message", "Bạn có tin nhắn mới!!!");
        sendBroadcast(intent);
    }
}
