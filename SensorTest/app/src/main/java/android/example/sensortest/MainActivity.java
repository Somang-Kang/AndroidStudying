package android.example.sensortest;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;

    private Sensor accelerometer, mGyro,mMagno,mLight,mPressure,mTemp,mHumi;

    TextView xValue;
    TextView yValue;
    TextView zValue;
    TextView xGyroValue, yGyroValue, zGyroValue,xMagnoValue, yMagnoValue,zMagnoValue,light,pressure,temp,humi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xValue = (TextView) findViewById(R.id.xValue);
        yValue = (TextView) findViewById(R.id.yValue);
        zValue = (TextView) findViewById(R.id.zValue);

        xGyroValue = (TextView) findViewById(R.id.xGyroValue);
        yGyroValue = (TextView) findViewById(R.id.yGyroValue);
        zGyroValue = (TextView) findViewById(R.id.zGyroValue);

        xMagnoValue = (TextView) findViewById(R.id.xMagnoValue);
        yMagnoValue = (TextView) findViewById(R.id.yMagnoValue);
        zMagnoValue = (TextView) findViewById(R.id.zMagnoValue);

        light = (TextView) findViewById(R.id.light);
        pressure = (TextView) findViewById(R.id.pressure);
        temp = (TextView) findViewById(R.id.temp);
        humi = (TextView) findViewById(R.id.humi);

        //getSystemService() 사용하여 sensor객체 생성,반환
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //가속도계 센서 생성
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //이벤트리스너등록
        if(accelerometer != null){
            sensorManager.registerListener((SensorEventListener) MainActivity.this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);

        }
        else{
            xValue.setText("Acceleromter Not Supported");
            yValue.setText("Acceleromter Not Supported");
            zValue.setText("Acceleromter Not Supported");

        }

        mGyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if(mGyro != null){
            sensorManager.registerListener((SensorEventListener) MainActivity.this,mGyro,SensorManager.SENSOR_DELAY_NORMAL);

        }
        else{
            xGyroValue.setText("Gyro Not Supported");
            yGyroValue.setText("Gyro Not Supported");
            zGyroValue.setText("Gyro Not Supported");
        }

        mMagno = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if(mMagno != null){
            sensorManager.registerListener((SensorEventListener) MainActivity.this,mMagno,SensorManager.SENSOR_DELAY_NORMAL);

        }
        else{
            xMagnoValue.setText("Magno Not Supported");
            yMagnoValue.setText("Magno Not Supported");
            zMagnoValue.setText("Magno Not Supported");
        }

        mLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if(mLight != null){
            sensorManager.registerListener((SensorEventListener) MainActivity.this,mLight,SensorManager.SENSOR_DELAY_NORMAL);

        }
        else{
            light.setText("Light Not Supported");

        }


        mPressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        if(mPressure != null){
            sensorManager.registerListener((SensorEventListener) MainActivity.this,mPressure,SensorManager.SENSOR_DELAY_NORMAL);

        }
        else{
            pressure.setText("Pressure Not Supported");

        }

        mTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        if(mTemp != null){
            sensorManager.registerListener((SensorEventListener) MainActivity.this,mTemp,SensorManager.SENSOR_DELAY_NORMAL);

        }
        else{
            temp.setText("Tempature Not Supported");

        }

        mHumi = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        if(mHumi != null){
            sensorManager.registerListener((SensorEventListener) MainActivity.this,mHumi,SensorManager.SENSOR_DELAY_NORMAL);

        }
        else{
            humi.setText("Humi Not Supported");

        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;

        if(sensor.getType()== Sensor.TYPE_ACCELEROMETER){
            xValue.setText("xValue: "+ sensorEvent.values[0]);
            yValue.setText("yValue: "+ sensorEvent.values[1]);
            zValue.setText("zValue: "+ sensorEvent.values[2]);
        }
        else if(sensor.getType()==Sensor.TYPE_GYROSCOPE){
            xGyroValue.setText("xGyroValue: "+ sensorEvent.values[0]);
            yGyroValue.setText("yGyroValue: "+ sensorEvent.values[1]);
            zGyroValue.setText("zGyroValue: "+ sensorEvent.values[2]);
        }
        else if(sensor.getType()==Sensor.TYPE_MAGNETIC_FIELD){
            xMagnoValue.setText("xMagnoValue: "+ sensorEvent.values[0]);
            yMagnoValue.setText("yMagnoValue: "+ sensorEvent.values[1]);
            zMagnoValue.setText("zMagnoValue: "+ sensorEvent.values[2]);
        }
        else if(sensor.getType()==Sensor.TYPE_LIGHT){
            light.setText("light: "+ sensorEvent.values[0]);
        }
        else if(sensor.getType()==Sensor.TYPE_PRESSURE){
            pressure.setText("pressure: "+ sensorEvent.values[0]);
        }
        else if(sensor.getType()==Sensor.TYPE_AMBIENT_TEMPERATURE){
            temp.setText("temperature: "+ sensorEvent.values[0]);
        }
        else if(sensor.getType()==Sensor.TYPE_RELATIVE_HUMIDITY){
            humi.setText("Humidity: "+ sensorEvent.values[0]);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}