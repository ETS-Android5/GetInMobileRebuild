package org.odk.getin.android;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.pixplicity.easyprefs.library.Prefs;

import org.odk.getin.android.utilities.ApplicationConstants;
import org.odk.getin.android.utilities.NotificationUtils;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;


/**
 * Sends app usage reminders every day at 11am
 */
public class NotificationTriggerService extends IntentService {
    private static final String TAG = NotificationAlarmTrigger.class.getSimpleName();

    public NotificationTriggerService() {
        super("NotificationTriggerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String activityName = cn.getPackageName();

        long lastNotitifcationTime = Prefs.getLong(ApplicationConstants.LAST_NOTIFICATION_TIME, 0);
        long currentTimeMillis = System.currentTimeMillis();
        boolean hasDisplayedNotification = (currentTimeMillis - lastNotitifcationTime) < TimeUnit.HOURS.toMillis(24);

        // only show notification when app is not open
        if (!activityName.contains("org.odk.getin.android") && !hasDisplayedNotification) {
            NotificationUtils.showNotificationMessage("Please don't forget to use the GetIn app to map", getString(R.string.getin_reminder));
            updateLastNotificationPref();
        }
    }

    private void updateLastNotificationPref() {
        Prefs.putLong(ApplicationConstants.LAST_NOTIFICATION_TIME, System.currentTimeMillis());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        triggerAppUsageReminderNotificationAlarm();
        triggerAppUsageReminderWorkManager();
    }

    private void triggerAppUsageReminderNotificationAlarm() {
        Log.d(TAG, "triggerAppUsageReminderNotification: started");
        Intent alarmIntent = new Intent(this, NotificationAlarmTrigger.class);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, 1);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 4376725, alarmIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis() + AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                AlarmManager.INTERVAL_HALF_DAY, pendingIntent);
    }

    private void triggerAppUsageReminderWorkManager() {
        String TASK_ID = "NotificationWorker";
        WorkManager workManager = WorkManager.getInstance();
        PeriodicWorkRequest notifyAlarmRequest = new PeriodicWorkRequest.Builder(NotifyWorker.class, 1, TimeUnit.DAYS).build();
        workManager.enqueueUniquePeriodicWork(TASK_ID, ExistingPeriodicWorkPolicy.REPLACE, notifyAlarmRequest);
    }
}