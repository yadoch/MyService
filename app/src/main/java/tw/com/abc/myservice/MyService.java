package tw.com.abc.myservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

public class MyService extends Service {
    private MediaPlayer player;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("brad", "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("brad", "onCreate");
        player = MediaPlayer.create(this, R.raw.music);
        player.start();
        Log.i("brad", "Player Start run!");
    }
    @Override
    // 透過intent 取得MainActivity 傳入的狀態,決定撥放或暫停音樂
    public int onStartCommand(Intent intent, int flags, int startId) {
      //  Log.i("brad", "onStartCommand");
        boolean isStart=intent.getBooleanExtra("isStart",false);
        boolean isPause=intent.getBooleanExtra("isPause",false);
        if(isPause) {
            if (player != null && player.isPlaying()) {
                player.pause();
            }else {
                player.start();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("brad", "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Log.i("brad", "onDestroy");
        if(player != null){
            player.stop();
        }
    }

}