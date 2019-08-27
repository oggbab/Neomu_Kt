package com.neomu.neomu.app.club;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.neomu.neomu.NickName;
import com.neomu.neomu.R;
import com.neomu.neomu.app.map.MapyActivity;
import com.neomu.neomu.app.mypage.MypageActivity;
import com.neomu.neomu.app.search.SearchActivity;
import com.neomu.neomu.common.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    Intent intent;
    FirebaseAuth mAuth;
    FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;
    FirebaseUser user;
    NickName su;
    String nick;
    FragmentPagerAdapter mPagerAdapter1;

    @BindView(R.id.main_navigationview) NavigationView navigationView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout1) DrawerLayout drawerLayout1;
    @BindView(R.id.container) ViewPager mViewPager1;
    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.rightSearch) ImageView imageView;
    @BindView(R.id.texttest) TextView texttest;
    @BindView(R.id.navi_id) TextView navi_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        setFragment();

        //회원정보 받아오는부분
        user=mAuth.getCurrentUser();

        //서버정보 가져오기
        texttest.setText(user.getEmail());
        mDatabase = mFirebaseDatabase.getReference("users");
        mDatabase.orderByChild("email").equalTo(texttest.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    nick = (String) childDataSnapshot.child("nickName").getValue();
                }
                su = new NickName(nick);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // 어뎁터 설정
        mViewPager1.setAdapter(mPagerAdapter1);
        tabLayout.setupWithViewPager(mViewPager1);

        //검색 창
        imageView.setOnClickListener(v -> {
            Intent intent2 = new Intent(MainActivity.this, SearchActivity.class);
            intent2.putExtra("nickName",nick);
            startActivity(intent2);
        });

        setToolbar();

        //todo sharedReference로 가져오기
        //네비게이션 아이디 부분 설정
        View view = navigationView.getHeaderView(0);
        navi_id.setText(nick);
        onNavigationListener();
    }


    // 2-1 네비게이션뷰 클릭
    private void onNavigationListener() {
        navigationView.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.first:
                    intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    drawerLayout1.closeDrawer(GravityCompat.START);
                    break;
                case R.id.second:
                    intent = new Intent(MainActivity.this, MypageActivity.class);
                    intent.putExtra("nickName",nick);
                    startActivity(intent);
                    finish();
                    drawerLayout1.closeDrawer(GravityCompat.START);
                    break;
                case R.id.third:
                    showToast("즐겨찾기 설정해요",false);
                    drawerLayout1.closeDrawer(GravityCompat.START);
                    break;
                case R.id.fourth:
                    intent = new Intent(MainActivity.this, MapyActivity.class);
                    intent.putExtra("nickName",nick);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.fifth:
                    intent = new Intent(MainActivity.this, Club_New_Activity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
            return false;
        });
    }

    // 1-1 툴바, 네비게이션 뷰
    private void setToolbar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout1, toolbar, 0, 0);
        drawerLayout1.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    // 프레그먼트 세팅
    void setFragment(){
        mPagerAdapter1 = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments1 = new Fragment[]{
                    new PopularFragment(),
                    new NewFragment(),
                    new NearFragment()
            };
            private final String[] mFragmentNames1 = new String[]{
                    "인기있는", "새로운", "근처에"
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
    }
}