package org.talcrafts.udhari.reminders;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class ScheduledJobService extends JobService {
    @Override
    public boolean onStartJob(JobParameters params) {
        startService(new Intent(this, NotificationService.class));
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}