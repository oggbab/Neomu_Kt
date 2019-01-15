package com.neomu.neomu.mypage;

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
import com.neomu.neomu.SaveSharedPreference;
import com.neomu.neomu.club.Club_New_Activity;
import com.neomu.neomu.club.MainActivity;
import com.neomu.neomu.map.MapyActivity;
import com.neomu.neomu.search.SearchActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MypageActivity extends AppCompatActivity {

    private static final String TAG = "MypageActivity";

    DrawerLayout drawerLayout;
    Intent intent;
    ImageView imageView;
    String author;
    TextView nick;
    String nickIntent;
    TextView navi_id;


    // 프레그먼트 세팅
    private FragmentPagerAdapter mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        private final Fragment[] mFragments = new Fragment[] {
                new JoinMyFragment(),
                new CompMyFragment(),
                new LikeMyFragment()
        };
        private final String[] mFragmentNames = new String[]{
                "참여중","완료한","즐겨찾기"
        };

        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }
        @Override
        public int getCount() {
            return mFragments.length;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentNames[position];
        }
    };
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);


        // 어뎁터 설정
        mViewPager = findViewById(R.id.container2);
        mViewPager.setAdapter(mPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs2);
        tabLayout.setupWithViewPager(mViewPager);

//        author = SaveSharedPreference.getUserName(this);
        nick = findViewById(R.id.mypage_activity_id);

        Intent intentResult = getIntent();
        nickIntent=intentResult.getStringExtra("nickName");
        nick.setText(nickIntent);

        NickName su = new NickName(nickIntent);

        //검색 창
        imageView = findViewById(R.id.rightSearch);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageActivity.this, SearchActivity.class);
                intent.putExtra("nickName",NickName.getNick());
                startActivity(intent);
            }
        });

        // 1-1 툴바, 네비게이션 뷰
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);

        drawerLayout =  findViewById(R.id.drawer_layout2);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        // 2-1 네비게이션뷰 클릭
        NavigationView navigationView = findViewById(R.id.main_navigationview);

        View view = navigationView.getHeaderView(0);
        navi_id = view.findViewById(R.id.navi_id);
        navi_id.setText(nickIntent);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.first:
                        intent = new Intent(MypageActivity.this, MainActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.second:
                        intent = new Intent(MypageActivity.this, MypageActivity.class);
                        intent.putExtra("nickName",nickIntent);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.third:
                        Toast.makeText(getApplicationContext(), "즐겨찾기 설정해요", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.fourth:
                        intent = new Intent(MypageActivity.this, MapyActivity.class);
                        intent.putExtra("nickName",nickIntent);
                        startActivity(intent);
                        break;
                    case R.id.fifth:
                        intent = new Intent(MypageActivity.this, Club_New_Activity.class);
                        startActivity(intent);
                        break;

                }
                return false;
            }
        });


    }



}

