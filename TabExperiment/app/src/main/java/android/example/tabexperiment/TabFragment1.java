package android.example.tabexperiment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabFragment1 extends Fragment {


    public TabFragment1() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //inflater : xml로 정의된 view (또는 menu 등)를 실제 객체화
        return inflater.inflate(R.layout.tab_fragment1, container, false);
    }
}