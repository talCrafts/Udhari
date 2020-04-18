package org.talcrafts.udhari;

import android.app.AlarmManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.talcrafts.udhari.reminders.ScheduledJobService;

public class SettingsActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = SettingsActivity.class.getSimpleName();
    private static final int JOB_ID = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        final String remindersKey = getString(R.string.pref_key_reminders);
        if (key.equals(remindersKey)) {
            boolean enabled = sharedPreferences.getBoolean(remindersKey, false);
            JobScheduler jobScheduler =
                    (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

            if (!enabled) {
                jobScheduler.cancel(JOB_ID);
                Log.d(TAG, "cancelling scheduled job");
            } else {
                long interval = AlarmManager.INTERVAL_HOUR;
                JobInfo job = new JobInfo.Builder(JOB_ID,
                        new ComponentName(getPackageName(),
                                ScheduledJobService.class.getName()))
                        .setPersisted(true)
                        .setPeriodic(interval)
                        .build();
                jobScheduler.schedule(job);
                Log.d(TAG, "setting scheduled job for: " + interval);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
