package com.androidwidgetpoc;

import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
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
        Log.d("WIDGET_PROVIDER", "En onEnabled");
        Intent serviceIntent = new Intent(context, BackgroundTask.class);
        context.startService(serviceIntent);
        HeadlessJsTaskService.acquireWakeLockNow(context);
    }

    @Override
    public void onReceive(final Context context, final Intent incomingIntent) {
        super.onReceive(context, incomingIntent);

        if (!incomingIntent.getAction().startsWith("com.androidwidgetpoc.CHARM")) {
            return;
        }

        Intent silentStartIntent = new Intent(context, BackgroundTask.class);
        context.startService(silentStartIntent);

        /*
        * Proxy bundle extras towards the service
        * */
        Intent serviceIntent = new Intent(context, BackgroundTask.class);
        serviceIntent.putExtras(incomingIntent);
        context.startService(serviceIntent);
    }
}
