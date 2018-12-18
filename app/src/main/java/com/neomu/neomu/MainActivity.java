package com.neomu.neomu;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.mainTab);

        Fragment[] arrFragment = new Fragment[3];
        arrFragment[0] = new PopularFragment();
        arrFragment[1] = new NewFragment();
        arrFragment[2] = new NearFragment();

        ViewPager viewPager = (ViewPager) findViewById(R.id.mainView);

        MyPageAdapter adapter = new MyPageAdapter(getSupportFragmentManager(), arrFragment);

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }

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
                case 0: return "인기있는";
                case 1: return "새로운";
                case 2: return "가까운";
                default: return "";
            }
        }
    }
}
