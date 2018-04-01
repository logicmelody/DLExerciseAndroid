package com.dl.dlexerciseandroid.backgroundtask.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.ui.main.MainActivity;
import com.dl.dlexerciseandroid.utility.utils.GeneralUtils;
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
        // 這個super已經可以handle大部分的message，但有兩個狀況是例外：
        // 1. Notifications delivered when your app is in the background.
        // 2. Messages with both notification and data payload, both background and foreground.
        super.onMessageReceived(remoteMessage);

        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

        // onMessageReceived()這個callback，只有在app在foreground執行的時候才會呼叫，若是app在background或是closed的狀態，
        // 則只會在device上跳出notification並且顯示body跟title而已
        //
        // Note: 當app被close掉的時候(user進到recent app的頁面，然後把app swipe掉)，
        //       有些device會收不到Firebase傳來的notification(e.g. Zenfone 2)，但是Nexus 7和Nexus 5模擬器正常可以收到

        // app在foreground的時候並不會跳出notification，但是可以經由onMessageReceived()拿到完整的資訊，
        // 我們也可以在onMessageReceived()中自己顯示notification

        // getFrom()可以知道message是用哪種方式傳過來
        // /topics/test：Send to a topic
        // 682672505333(id)：Send to a user
        Log.d("danny", "From: " + remoteMessage.getFrom());

        // getBody()可以拿到notification的內容
        // 會顯示在notification的第二行
        Log.d("danny", "Notification Message Body: " + remoteMessage.getNotification().getBody());

        // getTitle()可以拿到notification的title
        // 會顯示在notification的第一行
        Log.d("danny", "Notification Message title: " + remoteMessage.getNotification().getTitle());

        // 使用console中的進階選項，可以設定key/value
        Log.d("danny", "Notification Message data(key/value): " + remoteMessage.getData().get("title"));

        sendNotification(remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage) {
        // 用來建造notification的builder
        Notification.Builder builder = new Notification.Builder(this);
        Intent backIntent = new Intent(this, MainActivity.class);
        backIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent backPendingIntent = PendingIntent.getActivity(this, 0, backIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 一個notification一定要有以下三個資訊：
        // 1. setSmallIcon()：在status bar上出現的小icon
        // 2. setContentTitle()
        // 3. setContentText()
        // Note: 如果使用setContent()來使用customized layout的話，2跟3項可以不需要
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setContentIntent(backPendingIntent)
                .setAutoCancel(true);

        ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).
                notify(GeneralUtils.getNotificationId(), builder.build());
    }
}
