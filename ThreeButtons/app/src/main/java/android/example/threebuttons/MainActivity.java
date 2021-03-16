package android.example.threebuttons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    public void launchSecondActivity_2(View view) {
        Intent intent = new Intent(this, SecondActivity_2.class);
        startActivity(intent);
    }

    public void launchSecondActivity_3(View view) {
        Intent intent = new Intent(this, SecondActivity_3.class);
        startActivity(intent);
    }
}