package android.example.droidcafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //getIntent()를 통해 새 intent생성
        Intent intent = getIntent();
        String message = "Order: " + intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //<textview 생성> textview의 참조객체얻음 -> order_textview
        TextView textView=findViewById(R.id.order_textview);
        textView.setText(message);

        //<spinner 생성> spinner의 참조객체얻음 ->label_spinner
        Spinner spinner = findViewById(R.id.label_spinner);

        if(spinner!=null){
            spinner.setOnItemSelectedListener(this);  //event listener 등록
        }

        //spinner ArrayAdapter,dropdown설정
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.labels_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if(spinner!=null){
            spinner.setAdapter(adapter); //spinner에 adapter적용
        }
    }

    public void displayToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }


    public void onRadioButtonClicked(View view) {
       boolean checked = ((RadioButton)view).isChecked();
       switch(view.getId()){
           case R.id.sameday:
               if(checked)
                   displayToast(getString(R.string.same_day_messenger_service));
               break;
           case R.id.nextday:
               if(checked)
                   displayToast(getString(R.string.next_day_ground_delivery));
               break;
           case R.id.pickup:
               if(checked)
                   displayToast(getString(R.string.pick_up));
               break;
           default:
               break;
       }
    }

    //이벤트리스너 호출시 자동생성(?), i는 spinnerLabel순서 이것도 자동으로 할당되는듯
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String spinnerLabel = adapterView.getItemAtPosition(i).toString();
        displayToast(spinnerLabel);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}