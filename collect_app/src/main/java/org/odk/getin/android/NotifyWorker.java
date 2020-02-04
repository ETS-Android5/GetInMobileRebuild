package org.odk.getin.android;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.odk.getin.android.activities.SplashScreenActivity;
import org.odk.getin.android.utilities.NotificationUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;


public class NotifyWorker extends Worker {
    private static final String TAG = NotifyWorker.class.getSimpleName();
    private Context context;

    public NotifyWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        checkAppIsActive();
        return Result.SUCCESS;
    }

    private void checkAppIsActive() {
        //only show the notification when app is closed
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String activityName = cn.getPackageName();

        if (!activityName.contains("org.odk.getin.android")) {
            DateValidator dateValidator = new DateValidator();
            String dateTimePattern = "dd/MM/yyyy HH:mm:ss";
            String date = new SimpleDateFormat(dateTimePattern, Locale.getDefault()).format(new Date());

            if (dateValidator.isThisDateWithinRange(date, dateTimePattern)) {
                triggerNotification();
            } else {
                Timber.d(TAG, "checkAppIsActive: not between 10am - 11am");
            }
        }
    }

    private void triggerNotification() {
        Intent intentService = new Intent(context, SplashScreenActivity.class);
        NotificationUtils.showNotificationMessage("Please don't forget to use the GetIN app to map girls", context.getString(R.string.getin_reminder));
        context.startService(intentService);
    }
}