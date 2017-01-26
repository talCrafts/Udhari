package org.talcrafts.udhari.reminders;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

import org.talcrafts.udhari.R;


public class NotificationService extends IntentService {
    public static final int NOTIFICATION_ID = 8;
    private static final String TAG = NotificationService.class.getSimpleName();
    private NotificationManager mNotificationManager;
    private int mNotificationId = 001;

    public NotificationService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder= new NotificationCompat.Builder(getApplicationContext());
        builder.setContentTitle(getString(R.string.time_to_practice));
        builder.setContentText(getString(R.string.it_is_time_to_practice));
        //TODO set it correct
        builder.setSmallIcon(R.drawable.sym_action_email);
        mNotificationManager.notify(mNotificationId,builder.build());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    }
}
