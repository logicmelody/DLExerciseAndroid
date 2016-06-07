package com.dl.dlexerciseandroid.background.service;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by logicmelody on 2016/6/5.
 */

// FCM = Firebase Cloud Messaging
// 如果我們要接收notification或是message的處理，必須要有一個class extend FirebaseMessagingService
// e.g. To receive notifications in foregrounded apps, to receive data payload, to send upstream messages, and so on.
public class FCMService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        Log.d("danny", "From: " + remoteMessage.getFrom());
        Log.d("danny", "Notification Message Body: " + remoteMessage.getNotification().getBody());
    }
}
