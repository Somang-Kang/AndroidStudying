package android.example.takeawalk;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RecognizedService extends IntentService {
    private static final String TAG = "ActivityRecognizedServi";

    public RecognizedService(){
        super("RecognizedService");
    }

    public RecognizedService(String name) {
        super(name);
    }


    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG,"onHandle0");
        if(ActivityRecognitionResult.hasResult(intent)){
            Log.d(TAG,"onHandle1");

            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivity(result.getProbableActivities());
            Log.d(TAG,"onHandle2");
        }
    }

    private void handleDetectedActivity(List<DetectedActivity> probableActivities) {
        for(DetectedActivity activity: probableActivities){
            switch(activity.getType()){
                case DetectedActivity.IN_VEHICLE:
                    Log.d(TAG,"handleDetectedActivity: IN_VEHICLE"+activity.getConfidence());
                    ((MainActivity)MainActivity.context_main).count = 10;
                    ((MainActivity)MainActivity.context_main).isThread = true;

                    break;
                case DetectedActivity.ON_BICYCLE:
                    Log.d(TAG,"handleDetectedActivity: ON_BICYCLE"+activity.getConfidence());
                    ((MainActivity)MainActivity.context_main).isThread = false;
                    ((MainActivity)MainActivity.context_main).isThread = true;

                    break;
                case DetectedActivity.ON_FOOT:
                    Log.d(TAG,"handleDetectedActivity: ON_FOOT"+activity.getConfidence());
                    ((MainActivity)MainActivity.context_main).isThread = false;
                    ((MainActivity)MainActivity.context_main).isThread = true;
                    break;
                case DetectedActivity.RUNNING:
                    Log.d(TAG,"handleDetectedActivity: RUNNING"+activity.getConfidence());
                    ((MainActivity)MainActivity.context_main).isThread = false;
                    ((MainActivity)MainActivity.context_main).isThread = true;

                    break;
                case DetectedActivity.STILL:
                    Log.d(TAG,"handleDetectedActivity: STILL"+activity.getConfidence());
                    break;
                case DetectedActivity.WALKING:
                    Log.d(TAG,"handleDetectedActivity: WALKING"+activity.getConfidence());
                    ((MainActivity)MainActivity.context_main).isThread = false;
                    ((MainActivity)MainActivity.context_main).isThread = true;

                    break;
                case DetectedActivity.TILTING:
                    Log.d(TAG,"handleDetectedActivity: TILTING"+activity.getConfidence());
                    ((MainActivity)MainActivity.context_main).count = 10;
                    //((MainActivity)MainActivity.context_main).isThread = true;

                    break;
                case DetectedActivity.UNKNOWN:
                    Log.d(TAG,"handleDetectedActivity: UNKNOWN"+activity.getConfidence());
                    ((MainActivity)MainActivity.context_main).isThread = false;
                    ((MainActivity)MainActivity.context_main).isThread = true;

                    break;
            }
        }
    }
}