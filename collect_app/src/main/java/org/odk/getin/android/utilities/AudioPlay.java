package org.odk.getin.android.utilities;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;

import org.odk.getin.android.R;

import java.util.Timer;
import java.util.TimerTask;

public class AudioPlay {

    public static MediaPlayer player;
    private static int vibrationCycles;
    private static Vibrator vibrator;

    public static void playAudio(Context context){
        player = MediaPlayer.create(context, R.raw.notif);
        player.setLooping(true);
        player.start();
        if(!player.isPlaying())
        {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    player.stop();
                }
            }, player.getDuration() * 2);
        }
    }

    public static void stopAudio(Context context){
        player.stop();
    }
}
