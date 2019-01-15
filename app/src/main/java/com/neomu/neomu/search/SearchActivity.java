package com.neomu.neomu.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.neomu.neomu.NickName;
import com.neomu.neomu.R;
import com.neomu.neomu.club.Club_New_Activity;
import com.neomu.neomu.club.MainActivity;
import com.neomu.neomu.map.MapyActivity;
import com.neomu.neomu.mypage.MypageActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SearchActivity extends AppCompatActivity {

    DrawerLayout drawerLayout1;
    Intent intent;
    String nick;
    TextView navi_id;

    ImageView imageView;


    // 프레그먼트 세팅
    private FragmentPagerAdapter mPagerAdapter1 = new FragmentPagerAdapter(getSupportFragmentManager()) {
        private final Fragment[] mFragments1 = new Fragment[] {
                new BookFragment(),
                new CultureFragment(),
                new EtcFragment(),
                new GameFragment(),
                new MeetFragment(),
                new PetFragment(),
                new PhotoFragment(),
                new SportFragment()
        };
        private final String[] mFragmentNames1 = new String[]{
                "독서","문화공연","기타","게임","사교","반려동물","사진","스포츠"
        };

        @Override
        public Fragment getItem(int position) {
            return mFragments1[position];
        }
        @Override
        public int getCount() {
            return mFragments1.length;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentNames1[position];
        }
    };

    private ViewPager mViewPager1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // 어뎁터 설정
        mViewPager1 = findViewById(R.id.container4);
        mViewPager1.setAdapter(mPagerAdapter1);
        TabLayout tabLayout = findViewById(R.id.tabs4);
        tabLayout.setupWithViewPager(mViewPager1);

        Intent intentResult = getIntent();
        nick = intentResult.getStringExtra("nickName");


/*
        //검색 창
        imageView = findViewById(R.id.rightSearch);
        imageView.setImageResource(R.drawable.ic_home);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });*/
//닉네임

        // 1-1 툴바, 네비게이션 뷰
        Toolbar toolbar = findViewById(R.id.toolbar_chat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);

        drawerLayout1 =  findViewById(R.id.drawer_layout4);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout1, toolbar, 0, 0);
        drawerLayout1.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        // 2-1 네비게이션뷰 클릭
        NavigationView navigationView = findViewById(R.id.main_navigationview4);

        View view = navigationView.getHeaderView(0);
        navi_id = (TextView)view.findViewById(R.id.navi_id);
        navi_id.setText(nick);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.first:
                        intent = new Intent(SearchActivity.this, MainActivity.class);
                        intent.putExtra("nickName",nick);
                        startActivity(intent);
                        drawerLayout1.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.second:
                        intent = new Intent(SearchActivity.this, MypageActivity.class);
                        intent.putExtra("nickName",nick);
                        startActivity(intent);
                        drawerLayout1.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.third:
                        Toast.makeText(getApplicationContext(), "즐겨찾기 설정해요", Toast.LENGTH_SHORT).show();
                        drawerLayout1.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.fourth:
                        intent = new Intent(SearchActivity.this, MapyActivity.class);
                        intent.putExtra("nickName",nick);
                        startActivity(intent);
                        break;
                    case R.id.fifth:
                        intent = new Intent(SearchActivity.this, Club_New_Activity.class);
                        startActivity(intent);
                        break;

                }
                return false;
            }
        });

    }

}