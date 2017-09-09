package tw.com.abc.myservice;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private MyServiceConnection myServiceConnection;
    private SeekBar seekBar;
    private MyReceive myReceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar =(SeekBar) findViewById(R.id.seekbar);
        myReceive = new MyReceive();

        IntentFilter filter = new IntentFilter("brad");
        registerReceiver(myReceive, filter);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //formUser 使用者有無異動- 來自背景服務的異動不處理
                if(fromUser){
                    Intent it = new Intent(MainActivity.this,MyService.class);
                    it.putExtra("progress",progress);
                    startService(it);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // SeekBar 按下時   如:"會有時間點顯示"
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // SeekBar 放開時 如:"時間點會消失"
            }
        });

/*
        // 一開始為 Start 模式 -註解掉下面Bind 模式測試觀察生命週期的差異
        Intent it =new Intent(MainActivity.this,MyService.class);
        // Bind 模式
        myServiceConnection=new MyServiceConnection();
        bindService(it,myServiceConnection, Context.BIND_AUTO_CREATE);
*/
    }

    @Override
    public void finish() {
        // 關閉程式前取消myReceive註冊
        unregisterReceiver(myReceive);
        super.finish();
    }

    // 除非有在服務中做動作,否則只要維持下面狀況即可
    private class  MyServiceConnection implements ServiceConnection{

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }
    }

    public void start(View view){
        Intent it = new Intent(MainActivity.this, MyService.class);
        it.putExtra("isStart",true);
        startService(it);
    }

    public void pause(View view){
        Intent it = new Intent(MainActivity.this, MyService.class);
        it.putExtra("isPause", true);
        startService(it);
    }

    public void stop(View view){
        Intent it = new Intent(MainActivity.this, MyService.class);
        it.putExtra("isStop", true);
        stopService(it);
    }

    public void test(View view){
        // Bind Service 透過ServiceConnection 傳入值
        unbindService(myServiceConnection);
    }

    //自訂類別:收到歌曲長度後做SeekBar 設定處理
    private class MyReceive extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int len=intent.getIntExtra("len",-1);
            int now = intent.getIntExtra("now",-1);
            //Log.i("brad", "len | now :" + len + "|" +now);
            if(len >= 0){
                seekBar.setMax(len);
                Log.i("brad","len="+len);
            }
            if(now >=0){

                //Progress 進展  之意
                seekBar.setProgress(now);
            }
        }
    }
}
