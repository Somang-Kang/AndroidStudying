package android.example.takeawalk;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    TimerTask timerTask;
    Timer timer = new Timer();
    public GoogleApiClient mApiClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(MainActivity.this)
                .addOnConnectionFailedListener(MainActivity.this)
                .build();
        mApiClient.connect();
        Log.d("log : ","onCreate");

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Intent intent = new Intent(MainActivity.this,RecognizedService.class);
        PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this, 0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mApiClient,1000,pendingIntent);
        Log.d("log : ","onCon");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    public void startTimerTask()
    {

            timerTask = new TimerTask()
            {
                int count = 60;

                @Override
                public void run()
                {
                    count--;
                    Log.d("log : ",Integer.toString(count));
                    if(count == 0){
                        createNotification();
                        count = 60;
                    }
                }

            };
            timer.schedule(timerTask,0 ,1000);



    }

    private void createNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"default");
        builder.setContentTitle("Take a Walk!");
        builder.setContentText("You didn't move for 1 hour. How about taking a walk?");

        builder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT));
        }


        notificationManager.notify(1,builder.build());
    }


    public class RecognizedService extends IntentService {


        /**
         * Creates an IntentService.  Invoked by your subclass's constructor.
         *
         * @param name Used to name the worker thread, important only for debugging.
         */
        public RecognizedService(String name) {
            super(name);
        }
        public RecognizedService(){
            super("ActivityRecognizedService");
        }

        @Override
        protected void onHandleIntent(@Nullable Intent intent) {
            if(ActivityRecognitionResult.hasResult(intent)){
                ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
                handleDetectedActivity(result.getProbableActivities());
            }
        }

        private void handleDetectedActivity(List<DetectedActivity> probableActivities) {
            for(DetectedActivity activity: probableActivities){
                Log.d("log : ","good");
                if (activity.getType() != DetectedActivity.STILL){
                    startTimerTask();
                }
            }
        }
    }

}