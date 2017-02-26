package org.talcrafts.udhari.widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;

import org.talcrafts.udhari.AnswerActivity;
import org.talcrafts.udhari.R;
import org.talcrafts.udhari.data.DatabaseContract;
import org.talcrafts.udhari.data.Transaction;

import java.util.Random;

public class WidgetUpdateService extends IntentService {
    private static final String TAG = WidgetUpdateService.class.getSimpleName();

    public static final String EXTRA_WIDGET_IDS = "widget_ids";

    public WidgetUpdateService() {
        super(TAG);
    }


    @Override
    protected void onHandleIntent(final Intent intent) {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                UdhariWidget.class));

        for (int id : appWidgetIds) {

            int totalCards = 0;

            Cursor cursor = getContentResolver().query(DatabaseContract.CONTENT_URI,
                    new String[]{DatabaseContract.TableTransactions.COL_DATE, DatabaseContract.TableTransactions.COL_AMOUNT,
                                DatabaseContract.TableTransactions.COL_PARTY, DatabaseContract.TableTransactions.COL_SUMMARY},
                    null,
                    null,
                    null);

            if (cursor != null) {
                totalCards = cursor.getCount();
            }

            if (cursor == null) {
                Log.w(TAG, "Unable to read card database");
                return;
            }

            Random randomNumberGenerator = new Random();
            int randomNumber = randomNumberGenerator.nextInt(totalCards);
            cursor.moveToPosition(randomNumber);
            Transaction randomTransaction = new Transaction(cursor);

            //Load date info into widget view
            RemoteViews view = new RemoteViews(getPackageName(), R.layout.udhari_widget);
            if (randomTransaction == null) {
                view.setTextViewText(R.id.appwidget_text, "No Question!");
            } else {
                view.setTextViewText(R.id.appwidget_text, randomTransaction.date);
            }

            Context context = getBaseContext();
            Intent newIntent = new Intent(context, AnswerActivity.class);
            newIntent.putExtra(AnswerActivity.SELECTED_TRANSACTION, randomTransaction);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            view.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

            //Update the widget
            appWidgetManager.updateAppWidget(id, view);


            //Update the widget
//            ComponentName comp = new ComponentName(getApplicationContext().getPackageName(), UdhariWidget.class.getName());
//            appWidgetManager.updateAppWidget(comp, view);
        }
    }
}

