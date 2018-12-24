package com.neomu.neomu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_framelayout, new MainFragment()).commit();

        // 1-1 툴바, 네비게이션 뷰
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // 2-1 네비게이션뷰 클릭
        NavigationView navigationView = findViewById(R.id.main_navigationview);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.first:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_framelayout, new MainFragment()).commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.second:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_framelayout, new MypageFragment()).commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.third:
                        Toast.makeText(getApplicationContext(), "지도 띄울게요", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.forth:
                        Toast.makeText(getApplicationContext(), "옵션 설정해요", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
                return false;
            }
        });
    }
}