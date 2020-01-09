package org.odk.getin.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import timber.log.Timber;

public class NotificationAlarmTrigger extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.d("alarm started");
        Intent i = new Intent(context, NotificationTriggerService.class);
        context.startService(i);
    }
}
