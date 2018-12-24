package com.neomu.neomu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MypageFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        TabLayout tabLayout = view.findViewById(R.id.mypage_tab);
        ViewPager viewPager = view.findViewById(R.id.mypage_view);

        Fragment[] arrFragment = new Fragment[3];
        arrFragment[0] = new PopularFragment();
        arrFragment[1] = new NewFragment();
        arrFragment[2] = new NearFragment();

        MypageFragment.MyPageAdapter adapter = new MypageFragment.MyPageAdapter(getChildFragmentManager(), arrFragment);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
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
                case 0:
                    return "참여중";
                case 1:
                    return "완료";
                case 2:
                    return "즐겨찾기";
                default:
                    return "";
            }
        }
    }
}