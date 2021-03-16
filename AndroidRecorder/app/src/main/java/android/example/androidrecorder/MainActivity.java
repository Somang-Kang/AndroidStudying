package android.example.androidrecorder;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Button buttonStart, buttonStop, buttonPlayLastRecord, buttonStopPlaying;

    TextView textView;

    Thread runner;
    private  static double mEMA = 0.0;
    static final private double EMA_FILTER = 0.6;
    public static final int RequestPermissionCode = 1;

    String AudioSavePathInDevice = null;

    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;


    public double soundDb(double apml){
        return 20*Math.log10(getApmlitudeEMA()/apml);
    }

    public double getAmlitude(){
        if(mediaRecorder != null){
            return (mediaRecorder.getMaxAmplitude());
        }
        else{
            return 0;
        }

    }

    public double getApmlitudeEMA(){
        double amp = getAmlitude();
        mEMA = EMA_FILTER*amp +(1.0 - EMA_FILTER)*mEMA;
        return (int) mEMA;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonStart = (Button) findViewById(R.id.button1);
        buttonStop = (Button) findViewById(R.id.button2);
        buttonPlayLastRecord = (Button) findViewById(R.id.button3);
        buttonStopPlaying = (Button) findViewById(R.id.button4);

        buttonStop.setEnabled(false);
        buttonStopPlaying.setEnabled(false);
        buttonPlayLastRecord.setEnabled(false);

        textView = (TextView) findViewById(R.id.textView);

        if(runner == null){
            runner = new Thread(){
                public void run(){
                    while(runner!=null){
                        try{
                            Thread.sleep(1000);
                            Log.i(TAG,"run: Tock");
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText(Double.toString(soundDb(1))+"dB");
                            }
                        });
                    }
                }
            };

            runner.start();
            Log.d(TAG,"oncreate: runner started");
        }


        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CheckPermission()){
                    AudioSavePathInDevice = Environment.getExternalStorageDirectory().getAbsolutePath() +"/"+ "AudioRecord.3gpp";
                    MediaRecorderReady();

                    try{
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    }catch (IllegalStateException e){
                        e.printStackTrace();
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                    buttonStart.setEnabled(false);
                    buttonStop.setEnabled(true);
                    buttonPlayLastRecord.setEnabled(false);
                    Toast.makeText(MainActivity.this,"Recording Started",Toast.LENGTH_SHORT).show();


                }
                else{
                    RequestPermission();
                }
            }
        });


        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaRecorder.stop();
                buttonStop.setEnabled(false);
                buttonPlayLastRecord.setEnabled(true);
                buttonStart.setEnabled(true);
                buttonStopPlaying.setEnabled(false);

                Toast.makeText(MainActivity.this,"Recording Completed",Toast.LENGTH_SHORT).show();


            }
        });

        buttonPlayLastRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) throws IllegalArgumentException, SecurityException, IllegalStateException{
                buttonStop.setEnabled(false);
                buttonStart.setEnabled(false);
                buttonStopPlaying.setEnabled(true);

                mediaPlayer = new MediaPlayer();
                try{
                    mediaPlayer.setDataSource(AudioSavePathInDevice);
                    mediaPlayer.prepare();
                }catch (IOException e){
                    e.printStackTrace();
                }

                mediaPlayer.start();
                Toast.makeText(MainActivity.this,"Last Recording Playing",Toast.LENGTH_SHORT).show();


            }
        });

        buttonStopPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonStop.setEnabled(false);
                buttonStart.setEnabled(true);
                buttonStopPlaying.setEnabled(false);
                buttonPlayLastRecord.setEnabled(true);

                if(mediaPlayer != null){
                    mediaPlayer.start();
                    mediaPlayer.release();
                }
            }
        });
    }

    public void MediaRecorderReady(){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    private void RequestPermission(){
        ActivityCompat.requestPermissions
                (MainActivity.this,new String[]{WRITE_EXTERNAL_STORAGE,RECORD_AUDIO},RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case RequestPermissionCode:
                if(grantResults.length>0){
                    boolean StoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if(StoragePermission && RecordPermission){
                        Toast.makeText(this,"Permission is Granted", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    public boolean CheckPermission(){
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),RECORD_AUDIO);

        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }
}