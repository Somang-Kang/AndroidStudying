package android.example.hellocompat;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView mHelloTextView;
    private String[] mColorArray = {"red", "pink", "purple", "deep_purple",
            "indigo", "blue", "light_blue", "cyan", "teal", "green",
            "light_green", "lime", "yellow", "amber", "orange", "deep_orange",
            "brown", "grey", "blue_grey", "black" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelloTextView= findViewById(R.id.hello_textview);
        if(savedInstanceState != null){
            mHelloTextView.setTextColor(savedInstanceState.getInt("color"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("color",mHelloTextView.getCurrentTextColor());
    }

    public void changeColor(View view) {
        //20가지 색상 랜덤으로 추출
        Random random = new Random();
        String colorName = mColorArray[random.nextInt(20)];

        //색상명과 같은 color identifier 얻음
        int colorResourceName = getResources().getIdentifier(colorName,"color",getApplicationContext().getPackageName());

        //resource로부터 color ID 얻음
        int colorRes = ContextCompat.getColor(this,colorResourceName);

        //색상 설정
        mHelloTextView.setTextColor(colorRes);
    }
}