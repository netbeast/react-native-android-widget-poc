package com.androidwidgetpoc;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;

public class BackgroundTask extends HeadlessJsTaskService {

    @Override
    protected @Nullable
    HeadlessJsTaskConfig getTaskConfig(Intent intent) {
        /*
        * This is run inside onStartCommand
        * */
        startForeground(12345678, getNotification(intent));
        // startForeground(intent, getNotification(intent));

        Bundle extras = intent.getExtras();
        return new HeadlessJsTaskConfig(
                "WidgetTask",
                extras != null ? Arguments.fromBundle(extras) : null,
                10000);
    }

    private Notification getNotification(Intent intent) {

        // The PendingIntent that leads to a call to onStartCommand() in this service.
        PendingIntent servicePendingIntent = PendingIntent.getService(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // The PendingIntent to launch activity.
        PendingIntent activityPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .addAction(R.drawable.favicon_white, "Hola caracola",
                        activityPendingIntent)
                .addAction(R.drawable.add, "Adios caracol",
                        servicePendingIntent)
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