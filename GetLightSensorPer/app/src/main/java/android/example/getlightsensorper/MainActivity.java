package android.example.getlightsensorper;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor mLight;

    TextView light;
    TimerTask timerTask;
    Timer timer = new Timer();
    Button button;
    public static String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        light = (TextView) findViewById(R.id.light);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        button = (Button) findViewById(R.id.button);
        sensorManager.registerListener((SensorEventListener) MainActivity.this,mLight, SensorManager.SENSOR_DELAY_NORMAL);



    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;

        if(sensor.getType()==Sensor.TYPE_LIGHT){
            text = "light value : " + event.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public void buttonClicked(View view) {
        startTimerTask();
        Log.d("log:","start");
    }

    private void startTimerTask()
    {
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {

                light.post(new Runnable() {
                    @Override
                    public void run() {
                        light.setText(text);
                        Log.d("log:","value changed");
                    }
                });
            }
        };
        timer.schedule(timerTask,0,60000);
    }


}