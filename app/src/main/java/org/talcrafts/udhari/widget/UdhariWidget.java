package org.talcrafts.udhari.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class UdhariWidget extends AppWidgetProvider {
    private static final String TAG = UdhariWidget.class.getSimpleName();

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //Start the background service to update the widgets
        context.startService(new Intent(context, WidgetUpdateService.class));
    }
}
