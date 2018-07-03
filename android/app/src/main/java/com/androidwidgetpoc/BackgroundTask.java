package com.androidwidgetpoc;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;

import android.support.v4.app.NotificationCompat;

public class BackgroundTask extends HeadlessJsTaskService {

    private final int NOTIFICATION_ID = 12345678;
    private final String CHANNEL_ID = "channel_id";

    @Override
    protected @Nullable
    HeadlessJsTaskConfig getTaskConfig(Intent intent) {
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        /*
        * This is run inside onStartCommand
        * */
        // Android O requires a Notification Channel.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel name";
            // Create the channel for the notification
            NotificationChannel mChannel =
                    new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);

            // Set the Notification Channel for the Notification Manager.
            notificationManager.createNotificationChannel(mChannel);
            // notificationManager.startServiceInForeground(intent, NOTIFICATION_ID, getNotification());
        }

        startForeground(NOTIFICATION_ID, getNotification());

        Bundle extras = intent.getExtras();
        return new HeadlessJsTaskConfig(
                "WidgetTask",
                extras != null ? Arguments.fromBundle(extras) : null,
                10000);
    }

    private Notification getNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentText("Some text")
                .setContentTitle("Some title")
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Some title")
                .setWhen(System.currentTimeMillis());

        // Set the Channel ID for Android O.
        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        //    builder.setChannelId(CHANNEL_ID); // Channel ID
        // }

        return builder.build();
    }
}