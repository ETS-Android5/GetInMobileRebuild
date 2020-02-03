/*
 * Copyright 2018 Nafundi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.odk.getin.android.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.text.TextUtils;

import androidx.core.app.NotificationCompat;

import org.odk.getin.android.R;
import org.odk.getin.android.activities.MainMenuActivity;
import org.odk.getin.android.application.Collect;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationUtils {

    public static final String CHANNEL_ID = "collect_notification_channel";
    public static final int FORM_UPDATE_NOTIFICATION_ID = 0;
    private int vibrationCycles = 0;

    public NotificationUtils() {
    }

    public static void createNotificationChannel(Collect collect) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = collect.getSystemService(NotificationManager.class);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(new NotificationChannel(
                                        CHANNEL_ID,
                                        collect.getString(R.string.notification_channel_name),
                                        NotificationManager.IMPORTANCE_DEFAULT)
                );
            }
        }
    }

    public static void showNotification(PendingIntent contentIntent,
                                        int notificationId,
                                        int title,
                                        String contentText) {
        Context context = Collect.getInstance();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID).setContentIntent(contentIntent);

        builder
                .setContentTitle(context.getString(title))
                .setContentText(contentText)
                .setSmallIcon(IconUtils.getNotificationAppIcon())
                .setAutoCancel(true);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(notificationId, builder.build());
        }
    }

    public static void showNotificationMessage(String messageBody) {
        Context context = Collect.getInstance();
        Intent intent = new Intent(context, MainMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = context.getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.ic_getinlogo_notification)
                        .setContentTitle(context.getString(R.string.fcm_message))
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(46 /* ID of notification */, notificationBuilder.build());
    }

    public void showNotificationMessage(final String title, final String message, Intent intent, int id) {
        Context context = Collect.getInstance();
        // Check for empty push message
        if (TextUtils.isEmpty(message))
            return;

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        final PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        showSmallNotification(title, message, resultPendingIntent, id);
    }

    private void showSmallNotification(String title, String message, PendingIntent resultPendingIntent, int id) {
        Context context = Collect.getInstance();
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("General App notifications");
            mNotificationManager.createNotificationChannel(channel);
        }

        long[] pattern = new long[]{0, 400, 800, 600, 800, 800, 800, 800, 600, 1000};
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, -1);
        vibrationCycles = 1;

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                vibrationCycles++;
                vibrator.vibrate(pattern, -1);
                if(vibrationCycles >= 2){
                    timer.cancel();
                }
            }
        }, 6000, 6000);

        playNotificationSound();

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(resultPendingIntent)
                .setDefaults(Notification.DEFAULT_LIGHTS);
        mNotificationManager.notify(id, mBuilder.build());
    }

    private void playNotificationSound(){
        Context context = Collect.getInstance();
        AudioPlay.playAudio(context);
    }

    public void showNetworkNotificationMessage(final String title, final String message, Intent intent, int id) {
        Context context = Collect.getInstance();
        // Check for empty push message
        if (TextUtils.isEmpty(message))
            return;

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        final PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        showNetworkNotification(title, message, resultPendingIntent, id);
    }

    private void showNetworkNotification(String title, String message, PendingIntent resultPendingIntent, int id) {
        Context context = Collect.getInstance();

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("General App notifications");
            mNotificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "default")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(resultPendingIntent)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true);

        mNotificationManager.notify(id, mBuilder.build());
    }

}
