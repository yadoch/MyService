package tw.com.abc.myservice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private class MyServiceConnection  implements  serviceConnection {
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
}
    public void test(View view){

        //unbindService(mySe);
    }
}
