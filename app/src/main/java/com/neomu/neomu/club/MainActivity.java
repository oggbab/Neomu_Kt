package com.neomu.neomu.club;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.neomu.neomu.R;
import com.neomu.neomu.map.MapyActivity;
import com.neomu.neomu.models.User;
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


public class  MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    DrawerLayout drawerLayout1;
    Intent intent;
    TextView nick;
    String result;

    private DatabaseReference mDatabase;

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();

    }

    // 프레그먼트 세팅
    private FragmentPagerAdapter mPagerAdapter1 = new FragmentPagerAdapter(getSupportFragmentManager()) {
        private final Fragment[] mFragments1 = new Fragment[] {
                new PopularFragment(),
                new NewFragment(),
                new NearFragment()
        };
        private final String[] mFragmentNames1 = new String[]{
                "인기있는","새로운","근처에"
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
        setContentView(R.layout.activity_main);

        // 어뎁터 설정
        mViewPager1 = findViewById(R.id.container);
        mViewPager1.setAdapter(mPagerAdapter1);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager1);

        //db 이름 가져오기
/*        nick = findViewById(R.id.navi_id);

        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);
                        result = String.valueOf(user.nickName);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });

                        nick.setText(result);*/

        // 1-1 툴바, 네비게이션 뷰
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);

        drawerLayout1 =  findViewById(R.id.drawer_layout1);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout1, toolbar, 0, 0);
        drawerLayout1.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        // 2-1 네비게이션뷰 클릭
        NavigationView navigationView = findViewById(R.id.main_navigationview);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.first:
                        intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        drawerLayout1.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.second:
                        intent = new Intent(MainActivity.this, MypageActivity.class);
                        startActivity(intent);
                        drawerLayout1.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.third:
                        Toast.makeText(getApplicationContext(), "즐겨찾기 설정해요", Toast.LENGTH_SHORT).show();
                        drawerLayout1.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.fourth:
                        intent = new Intent(MainActivity.this, MapyActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.fifth:
                        intent = new Intent(MainActivity.this, Club_New_Activity.class);
                        startActivity(intent);
                        break;

                }
                return false;
            }
        });


    }


}