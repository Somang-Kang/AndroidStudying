package android.example.largeimages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private int toggle =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeImage(View view) {
        if(toggle == 0){
            view.setBackgroundResource(R.drawable.dinosaur_medium);
            toggle = 1;
        }
        else {
            view.setBackgroundResource(R.drawable.ankylo);
            toggle = 0;
        }
    }
}