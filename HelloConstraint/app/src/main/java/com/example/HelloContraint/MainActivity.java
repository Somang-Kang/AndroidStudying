package com.example.HelloContraint;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.HelloContraint.R;

public class MainActivity extends AppCompatActivity {

    private int mCount = 0;
    private TextView mShowCount;
    Button buttonEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = (TextView) findViewById(R.id.show_count);
        Log.d("MainActivity","Hello World");
    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(this,R.string.toast_message,Toast.LENGTH_SHORT);
        toast.show();
    }

    public void countUp(View view) {
        ++mCount;
        if(mShowCount !=null) {
            if (mCount == 0){
                buttonEvent.setBackgroundColor(Color.GRAY);
            }
            else if(mCount/2 ==0){
                buttonEvent.setBackgroundColor(Color.BLACK);
            }
            else{
                buttonEvent.setBackgroundColor(Color.YELLOW);
            }

            mShowCount.setText(Integer.toString(mCount));
        }
    }
}