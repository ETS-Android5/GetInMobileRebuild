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


public class NotifyWorker extends Worker {
    private static final String TAG = NotifyWorker.class.getSimpleName();

    public NotifyWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: worker manager running");
        checkAppIsActive();

        return Result.SUCCESS;
    }

    private void checkAppIsActive() {
        //only show the notification when app is closed
        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String activityName = cn.getPackageName();
        Log.d(TAG, "onHandleIntent: current activity " + activityName);

        if (!activityName.contains("org.odk.getin.android")) {
            Log.d(TAG, "doWork: App is not active show notification");

            DateValidator dateValidator = new DateValidator();
            String dateTimePattern = "dd/MM/yyyy HH:mm:ss";
            String date = new SimpleDateFormat(dateTimePattern, Locale.getDefault()).format(new Date());
            Log.d(TAG, "checkAppIsActive: current date " + date);

            if (dateValidator.isThisDateWithinRange(date, dateTimePattern)) {
                Log.d(TAG, "checkAppIsActive: between 10am - 11am");
                triggerNotification();
            } else {
                Log.d(TAG, "checkAppIsActive: not between 10am - 11am");
            }
        }
    }

    private void triggerNotification() {
        Context context = getApplicationContext();
        Intent intentService = new Intent(context, SplashScreenActivity.class);
        NotificationUtils.showNotificationMessage("Please don't forget to use the GetIN app to map girls");
        context.startService(intentService);
    }
}