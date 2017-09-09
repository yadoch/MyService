package tw.com.abc.myservice;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    private MediaPlayer player;
    private Timer timer;
    @Nullable  // 參數可為NULL
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("brad", "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //Log.i("brad", "onCreate");
        timer = new Timer();

        player = MediaPlayer.create(this, R.raw.music);

        //取得播放時間存入 len
        int len = player.getDuration();

        Intent it =new Intent("brad");  //action ="brad"
        it.putExtra("len", len);
        sendBroadcast(it);

        player.setLooping(true); // 循環播放
        player.start();
        Log.i("brad", "Player Start run!");

        timer.schedule(new PlayTask(),0,200);
    }

    private class PlayTask extends TimerTask{
        @Override
        public void run() {
            if(player != null && player.isPlaying() ){
                int now=player.getCurrentPosition();
                Intent it = new Intent("brad");
                it.putExtra("now",now);
                // 所有的Intent 都會收到廣播,只是要不要處理
                sendBroadcast(it);
            }
        }
    }

    @Override
    // 透過intent 取得MainActivity 傳入的狀態,決定撥放或暫停音樂
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("brad", "onStartCommand");
        boolean isStart=intent.getBooleanExtra("isStart",false);
        boolean isPause=intent.getBooleanExtra("isPause",false);
        int progress = intent.getIntExtra("progress",-1);
        if(progress >= 0){
            // 用seekTo() 修改播放位置
            player.seekTo(progress);
        }
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
        if(timer != null){
            timer.cancel();
            // purge 清除  之意
            timer.purge();
            timer = null;
        }
        if(player != null){
            player.stop();
        }
    }

}