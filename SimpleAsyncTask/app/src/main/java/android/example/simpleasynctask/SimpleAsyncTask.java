package android.example.simpleasynctask;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

public class SimpleAsyncTask extends AsyncTask<Void,Void,String> {
    private WeakReference<TextView> mTextView;

    SimpleAsyncTask(TextView tv){
        mTextView = new WeakReference<>(tv);
    }


    @Override
    protected String doInBackground(Void... voids) {    //가변인자
        Random r = new Random();
        int n = r.nextInt(11);

        int s = n*200;

        try{
            Thread.sleep(s);
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        return "Awake at last after sleeping for "+s+"milliseconds!";
    }

    //백그라운드 스레드 완료 후 메인스레드에 완료상태 전달
    protected void onPostExecute(String result){
        mTextView.get().setText(result);
    }

}
