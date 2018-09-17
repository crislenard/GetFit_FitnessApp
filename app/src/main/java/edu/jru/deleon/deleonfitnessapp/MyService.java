package edu.jru.deleon.deleonfitnessapp;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

/**
 * Created by Dell pc on 8/7/2017.
 */

public class MyService extends Service {

    private MediaPlayer mp;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
         mp = MediaPlayer.create(this,R.raw.bg);

        mp.setLooping(true);
        mp.setVolume(20,20);
        mp.start();

        return  START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mp.stop();
    }

}
