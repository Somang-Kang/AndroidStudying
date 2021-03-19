package android.example.takeawalk;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public GoogleApiClient mApiClient;
    public static Context context_main;
    private static final String TAG = "ActivityRecognizedServi";
    public boolean isThread = false;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotifyManager;
    private static final int NOTIFICATION_ID = 0;
    int count = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isThread = true;
        //BackThread thread = new BackThread();
        //thread.start();
        mApiClient = new GoogleApiClient.Builder(MainActivity.this)
                .addApi(ActivityRecognition.API)
                .addConnectionCallbacks(MainActivity.this)
                .addOnConnectionFailedListener(MainActivity.this)
                .build();
        mApiClient.connect();
        context_main = this;
       createNotificationChannel();

       //Handler han = new Handler();
       Runnable runnable = new Runnable() {
           @Override
           public void run() {
               new Thread(() -> {

                   while (isThread) {

                       count--;
                       try {
                           Thread.sleep(1000);
                           Log.d(TAG,"thread"+Integer.toString(count+1));
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                       if(count == 0){
                           count = 10;

                           //handler.sendEmptyMessage(0);

                       }


                   }


               }


               ).start();
           }

       };
        handler.post(runnable);
       //handler.postDelayed(runnable, 10000);
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Intent intent = new Intent(MainActivity.this,RecognizedService.class);
        PendingIntent pendingIntent = PendingIntent.getService(MainActivity.this, 0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(mApiClient,1000,pendingIntent);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void createNotificationChannel() {
        mNotifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Notification", NotificationManager
                    .IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from My App");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    public NotificationCompat.Builder getNotificationBuilder() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                .setContentTitle("Take a walk")
                .setContentText("You didn't move for a hour. Take a walk!")
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        return notifyBuilder;
    }

    /*class BackThread extends Thread{
        @Override
        public void run() {
            int count = 10;

            while (isThread) {

                count--;
                try {
                    Thread.sleep(1000);
                    Log.d(TAG,"thread"+Integer.toString(count+1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(count == 0){
                    count = 10;
                    handler.sendEmptyMessage(0);
                }
            }
        }
    }*/

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
            mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
        }
    };


}