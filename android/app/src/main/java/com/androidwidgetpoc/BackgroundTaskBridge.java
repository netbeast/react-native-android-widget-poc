package com.androidwidgetpoc;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;

public class BackgroundTaskBridge extends ReactContextBaseJavaModule {

    public BackgroundTaskBridge(final ReactApplicationContext reactContext) { super(reactContext); }

    @ReactMethod
    public void pinWidgetToHomeScreen () {
        Context context = this.getReactApplicationContext();

        ComponentName name = new ComponentName(context, WidgetProvider.class);
        int [] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(name);

        Intent pickIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
        pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, ids[0]);
        context.startActivity(pickIntent);
    }

    @Override
    public String getName() {
        return "BackgroundTaskBridge";
    }

    @ReactMethod
    public void taskResult(Boolean result) {
        RemoteViews views = new RemoteViews(this.getReactApplicationContext().getPackageName(), R.layout.appwidget);
        if (result == true) {
            Log.d("TASK_RESULT_TRUE", result.toString());
        } else {
            Log.d("TASK_RESULT_FALSE", result.toString());
        }
        AppWidgetManager.getInstance(this.getReactApplicationContext()).updateAppWidget(new ComponentName(this.getReactApplicationContext(), WidgetProvider.class), views);
    }

    @ReactMethod
    public void initializeWidgetBridge(ReadableArray starredCharms) {
        RemoteViews widgetView = new RemoteViews(this.getReactApplicationContext().getPackageName(), R.layout.appwidget);
        widgetView.removeAllViews(R.id.charms_layout);
        for (int i = 0; i < starredCharms.size(); i++) {
            ReadableMap charm = starredCharms.getMap(i);
            switch (i) {
                case 0:
                    updateView(widgetView, charm, R.layout.charm_button1, R.id.charm_button1);
                    registerTask("com.androidwidgetpoc.CHARM_1", charm, widgetView, R.id.charm_button1);
                    break;
                case 1:
                    updateView(widgetView, charm, R.layout.charm_button2, R.id.charm_button2);
                    registerTask("com.androidwidgetpoc.CHARM_2", charm, widgetView, R.id.charm_button2);
                    break;
                case 2:
                    updateView(widgetView, charm, R.layout.charm_button3, R.id.charm_button3);
                    registerTask("com.androidwidgetpoc.CHARM_3", charm, widgetView, R.id.charm_button3);
                    break;
            }
        }

        if (starredCharms.size() < 3) {
            RemoteViews add_view = new RemoteViews(this.getReactApplicationContext().getPackageName(), R.layout.add_button);
            add_view.setImageViewResource(R.id.add_button, getDrawable(this.getReactApplicationContext(), "add"));
            widgetView.addView(R.id.charms_layout, add_view);

            Intent launchActivity = new Intent(this.getReactApplicationContext(), MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this.getReactApplicationContext(), 0, launchActivity, 0);
            add_view.setOnClickPendingIntent(R.id.add_button, pendingIntent);
        }

        Intent launchActivity = new Intent(this.getReactApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this.getReactApplicationContext(), 0, launchActivity, 0);
        widgetView.setOnClickPendingIntent(R.id.yetiLogo, pendingIntent);

        AppWidgetManager.getInstance(this.getReactApplicationContext()).updateAppWidget(new ComponentName(this.getReactApplicationContext(), WidgetProvider.class), widgetView);
    }

    private void updateView(RemoteViews widgetView, ReadableMap charm, Integer layout, Integer button) {
        RemoteViews charm_view = new RemoteViews(this.getReactApplicationContext().getPackageName(), layout);
        charm_view.setImageViewResource(button, getDrawable(this.getReactApplicationContext(), charm.getString("cover")));
        charm_view.setTextViewText(R.id.charm_name, charm.getString("name"));
        widgetView.addView(R.id.charms_layout, charm_view);
    }

    private void registerTask(String action, ReadableMap charm, RemoteViews widgetView, Integer button) {
        Intent intent = new Intent();
        intent.putExtra("id", charm.getString("id"));
        intent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getReactApplicationContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        widgetView.setOnClickPendingIntent(button, pendingIntent);
    }

    private static int getDrawable(Context context, String name)
    {
        return context.getResources().getIdentifier(name.toLowerCase(),
                "drawable", context.getPackageName());
    }
}
