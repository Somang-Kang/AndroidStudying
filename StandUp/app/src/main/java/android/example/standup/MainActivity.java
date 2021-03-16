package android.example.standup;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Intent notifyIntent = new Intent(this,AlarmReceiver.class);
        PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(this,NOTIFICATION_ID,notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);

        ToggleButton alarmToggle = findViewById(R.id.alarmToggle);
        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent,
                PendingIntent.FLAG_NO_CREATE) != null);
        alarmToggle.setChecked(alarmUp);

        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String toastMessage;

                if(isChecked){
                    setInexactRepeating();
                    toastMessage = getString(R.string.stand_up_alarm_on);
                }
                else{
                    mNotificationManager.cancelAll();
                    toastMessage=getString(R.string.stand_up_alarm_off);
                }

                Toast.makeText(MainActivity.this,toastMessage,Toast.LENGTH_SHORT).show();
            }

            private void setInexactRepeating() {

                long repeatInterval = 1000*60;
                long triggerTime = SystemClock.elapsedRealtime()
                        + repeatInterval;

                if (alarmManager != null) {
                    alarmManager.setInexactRepeating
                            (AlarmManager.ELAPSED_REALTIME,
                                    triggerTime, repeatInterval, notifyPendingIntent);
                }
                else{
                    alarmManager.cancel(notifyPendingIntent);
                }
            }
        });

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        if(android.os.Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,getString(R.string.stand_up_noti),NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription(getString(R.string.noti_15_minute));
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void deliverNotification(Context context) {
        Intent contentIntent = new Intent(context,MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(context,NOTIFICATION_ID,contentIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stand_up)
                .setContentTitle(getString(R.string.stand_up_alert))
                .setContentText(getString(R.string.you_should))
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
        mNotificationManager.notify(NOTIFICATION_ID,builder.build());
    }
}