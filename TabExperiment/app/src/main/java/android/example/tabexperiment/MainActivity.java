package android.example.tabexperiment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //기존 action bar대신 toolbar 사용
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //tablayout객체 생성
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        //각각의 tab에 대한 text 설정
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_label3));

        //tab layout설정
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //프레그먼트에서 페이지뷰를 관리하기 위해 PagerAdapter 사용
        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        //클릭여부 감지하고 viewPager을 적절한 탭화면으로 설정
        //TabLayoutOnPageChangeListener : 탭의 선택상태를 동기화해주는 역할(페이지 상태가 변경될 때 페이지 변경 이벤트를 TabLayout에 전달)
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // TabLayout.onTabSelectedListener : 탭의 선택상태가 변경될 때 호출되는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}