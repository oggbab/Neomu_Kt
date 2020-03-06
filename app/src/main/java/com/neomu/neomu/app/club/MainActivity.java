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
import com.neomu.neomu.R;
import com.neomu.neomu.app.map.MapyActivity;
import com.neomu.neomu.app.models.User;
import com.neomu.neomu.app.mypage.MypageActivity;
import com.neomu.neomu.app.search.SearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    Intent intent;
    FirebaseAuth mFirebaseAuth;
    FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabase;
    FirebaseUser mFirebaseUser;
    String nickName;
    FragmentPagerAdapter mPagerAdapter;

    @BindView(R.id.nv_main) NavigationView nv_main;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.draw_main) DrawerLayout draw_main;
    @BindView(R.id.vp_container) ViewPager vp_container;
    @BindView(R.id.tab_main) TabLayout tab_main;
    @BindView(R.id.iv_rightSearch) ImageView imageView;
    @BindView(R.id.tv_test) TextView tv_test;
    //연결된게 있어 대기
    @BindView(R.id.tv_naviId) TextView navi_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        setFragment();

        //회원정보 받아오는부분
        mFirebaseUser=mFirebaseAuth.getCurrentUser();

        //서버정보 가져오기
        tv_test.setText(mFirebaseUser.getEmail());
        mDatabase = mFirebaseDatabase.getReference("users");
        mDatabase.orderByChild("email").equalTo(tv_test.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    nickName = (String) childDataSnapshot.child("nickName").getValue();
                }
                new User().setNickName(nickName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        // 어뎁터 설정
        vp_container.setAdapter(mPagerAdapter);
        tab_main.setupWithViewPager(vp_container);

        //검색 창
        imageView.setOnClickListener(v -> {
            Intent intent_toSearch = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent_toSearch);
        });

        setToolbar();

        //todo sharedReference로 가져오기
        //네비게이션 아이디 부분 설정
        View view = nv_main.getHeaderView(0);
        navi_id.setText(nickName);
        onNavigationListener();
    }


    // 2-1 네비게이션뷰 클릭
    private void onNavigationListener() {
        nv_main.setNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.first:
                    intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                    draw_main.closeDrawer(GravityCompat.START);
                    break;
                case R.id.second:
                    intent = new Intent(MainActivity.this, MypageActivity.class);
                    startActivity(intent);
                    finish();
                    draw_main.closeDrawer(GravityCompat.START);
                    break;
                case R.id.third:
                    showToast("즐겨찾기 설정해요",false);
                    draw_main.closeDrawer(GravityCompat.START);
                    break;
                case R.id.fourth:
                    intent = new Intent(MainActivity.this, MapyActivity.class);
                    onNewIntent(intent);
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
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_menu_black_24dp));
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, draw_main, toolbar, 0, 0);
        draw_main.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    //todo 프레그먼트 구조변경
    // 프레그먼트 세팅
    void setFragment(){
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[]{
                    new PopularFragment(),
                    new NewFragment(),
                    new NearFragment()
            };
            private final String[] mFragmentNames = new String[]{
                    "인기있는", "새로운", "근처에"
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
    }
}