package com.androidwidgetpoc;

import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.facebook.react.HeadlessJsTaskService;

public class WidgetProvider extends AppWidgetProvider {
    private static final String WIDGET_TASK = "com.androidwidgetpoc.WIDGET_TASK";

    /*
    * When enabled on screen, let the BackgroundTaskBridge
    * manipulate it from javascript
    */

    @Override
    public void onEnabled(Context context) {
        Intent intent = new Intent(context, BackgroundTask.class);
        startServiceAfterSdkVersion(context, intent);
        HeadlessJsTaskService.acquireWakeLockNow(context);
    }

    @Override
    public void onReceive(final Context context, final Intent incomingIntent) {
        super.onReceive(context, incomingIntent);

        if (!incomingIntent.getAction().startsWith("com.androidwidgetpoc.CHARM")) {
            return;
        }

        /*
        * Proxy bundle extras towards the service
        * */
        Intent serviceIntent = new Intent(context, BackgroundTask.class);
        serviceIntent.putExtras(incomingIntent);
        startServiceAfterSdkVersion(context, serviceIntent);
    }

    public void startServiceAfterSdkVersion (Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }
}
