package android.example.getlightsensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor mLight;

    TextView light;
    MyTimer myTimer;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        light = (TextView) findViewById(R.id.light);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        button = (Button) findViewById(R.id.button);

        myTimer = new MyTimer(60000,10);

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;

        if(sensor.getType()==Sensor.TYPE_LIGHT){
            light.setText("light value : "+ event.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public void buttonClicked(View view) {
        myTimer.start();
        Log.d("log:","start");
    }



    class MyTimer extends CountDownTimer {

        public MyTimer(long millisInFuture, long countDownInterval){
            super(millisInFuture,countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            sensorManager.registerListener((SensorEventListener) MainActivity.this,mLight,SensorManager.SENSOR_DELAY_NORMAL);
            Log.d("log:","value changed");
        }

        @Override
        public void onFinish() {
            light.setText("finished");
            button.setText("RESTART");

        }
    }
}