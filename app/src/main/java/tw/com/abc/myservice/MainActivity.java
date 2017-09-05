package tw.com.abc.myservice;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private MyServiceConnection myServiceConnection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 一開始為 Start 模式 -註解掉下面Bind 模式測試觀察生命週期的差異
        setContentView(R.layout.activity_main);
        Intent it =new Intent(MainActivity.this,MyService.class);
        // Bind 模式
        myServiceConnection=new MyServiceConnection();
        bindService(it,myServiceConnection, Context.BIND_AUTO_CREATE);
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
        startService(it);
    }

    public void pause(View view){
        Intent it = new Intent(MainActivity.this, MyService.class);
        startService(it);
    }

    public void stop(View view){
        Intent it = new Intent(MainActivity.this, MyService.class);
        stopService(it);
    }

    public void test(View view){
        // Bind Service 透過ServiceConnection 傳入值
        unbindService(myServiceConnection);
    }
}
