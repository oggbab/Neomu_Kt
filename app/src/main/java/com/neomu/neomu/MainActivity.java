package com.neomu.neomu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    ImageView leftMenu, centerIcon, rightSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // 이미지 뷰
        leftMenu = findViewById(R.id.leftMenu);
        centerIcon = findViewById(R.id.centerIcon);
        rightSearch = findViewById(R.id.rightSearch);

        leftMenu.setOnClickListener(new menuBar());
        centerIcon.setOnClickListener(new menuBar());
        rightSearch.setOnClickListener(new menuBar());


        // 탭 레이아웃, 뷰페이저 2-1
        TabLayout tabLayout = findViewById(R.id.mainTab);
        ViewPager viewPager = findViewById(R.id.mainView);

        Fragment[] arrFragment = new Fragment[3];
        arrFragment[0] = new PopularFragment();
        arrFragment[1] = new NewFragment();
        arrFragment[2] = new NearFragment();

        MyPageAdapter adapter = new MyPageAdapter(getSupportFragmentManager(), arrFragment);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    // 1-1
    class menuBar implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.leftMenu:
                    Toast.makeText(MainActivity.this, "메뉴버튼 눌럿어요", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.centerIcon:
                    Toast.makeText(MainActivity.this, "이모티콘 눌렀어요", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.rightSearch:
                    Toast.makeText(MainActivity.this, "검색 버튼 눌렀어요", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    // 2-1
    private class MyPageAdapter extends FragmentPagerAdapter {

        private Fragment[] arrFragment;

        public MyPageAdapter(FragmentManager fm, Fragment[] arrFragment) {
            super(fm);
            this.arrFragment = arrFragment;
        }

        @Override
        public Fragment getItem(int i) {
            return arrFragment[i];
        }

        @Override
        public int getCount() {
            return arrFragment.length;

        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "인기있는";
                case 1:
                    return "새로운";
                case 2:
                    return "가까운";
                default:
                    return "";
            }
        }
    }
}