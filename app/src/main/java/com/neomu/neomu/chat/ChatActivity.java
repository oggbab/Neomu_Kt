package com.neomu.neomu.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.neomu.neomu.R;
import com.neomu.neomu.SaveSharedPreference;
import com.neomu.neomu.map.MapyActivity;
import com.neomu.neomu.mypage.MypageActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.neomu.neomu.club.Club_New_Activity;
import com.neomu.neomu.club.MainActivity;

public class ChatActivity extends AppCompatActivity {

    public String CHAT_NAME;
    public String USER_NAME;
    public String ADMIN_NAME;
    public TextView chat_title_txt, id_chat, text_chat;

    private ListView chat_view;
    private EditText chat_edit;
    private Button chat_send;
    TextView navi_id;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private static final String TAG = "ChatActivity";

    DrawerLayout drawerLayout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // 위젯 ID 참조
        chat_view = (ListView) findViewById(R.id.chat_view);
        chat_edit = (EditText) findViewById(R.id.chat_edit);
        chat_send = (Button) findViewById(R.id.chat_sent);
        chat_title_txt = findViewById(R.id.chat_title_txt);

        // 로그인 화면에서 받아온 채팅방 이름, 유저 이름 저장
        Intent intent = getIntent();
        CHAT_NAME = intent.getStringExtra("chatName");
        USER_NAME = intent.getStringExtra("userName");
        ADMIN_NAME = intent.getStringExtra("adminName");
        chat_title_txt.setText(CHAT_NAME);
//        CHAT_NAME = "2모임테스트중";


        // 채팅 방 입장
        openChat(CHAT_NAME);

        // 메시지 전송 버튼에 대한 클릭 리스너 지정
        chat_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chat_edit.getText().toString().equals(""))
                    return;

                ChatDTO chat = new ChatDTO(USER_NAME, chat_edit.getText().toString()); //ChatDTO를 이용하여 데이터를 묶는다.
                databaseReference.child("chat").child(CHAT_NAME).push().setValue(chat); // 데이터 푸쉬

                chat_edit.setText(""); //입력창 초기화

            }
        });


        // 1-1 툴바, 네비게이션 뷰
        Toolbar toolbar = findViewById(R.id.toolbar_chat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);

        drawerLayout1 = findViewById(R.id.drawer_layout3);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout1, toolbar, 0, 0);
        drawerLayout1.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        // 2-1 네비게이션뷰 클릭
        NavigationView navigationView = findViewById(R.id.main_navigationview);

        View view = navigationView.getHeaderView(0);
        navi_id = (TextView)view.findViewById(R.id.navi_id);
        navi_id.setText(USER_NAME);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.first:
                        Intent intent2 = new Intent(ChatActivity.this, MainActivity.class);
                        startActivity(intent2);
                        drawerLayout1.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.second:
                        intent2 = new Intent(ChatActivity.this, MypageActivity.class);
                        startActivity(intent2);
                        drawerLayout1.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.third:
                        Toast.makeText(getApplicationContext(), "즐겨찾기 설정해요", Toast.LENGTH_SHORT).show();
                        drawerLayout1.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.fourth:
                        intent2 = new Intent(ChatActivity.this, MapyActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.fifth:
                        intent2 = new Intent(ChatActivity.this, Club_New_Activity.class);
                        startActivity(intent2);
                        break;

                }
                return false;
            }
        });

    }


    private void addMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter) {
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
        adapter.add(chatDTO.getUserName() + " : " + chatDTO.getMessage());
    }

    private void removeMessage(DataSnapshot dataSnapshot, ArrayAdapter<String> adapter) {
        ChatDTO chatDTO = dataSnapshot.getValue(ChatDTO.class);
        adapter.remove(chatDTO.getUserName() + " : " + chatDTO.getMessage());
    }

    private void openChat(String chatName) {
        // 리스트 어댑터 생성 및 세팅
        final ArrayAdapter<String> adapter
                = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1);
        chat_view.setAdapter(adapter);

        // 데이터 받아오기 및 어댑터 데이터 추가 및 삭제 등..리스너 관리

        // 데이터베이스 읽기 #1. ValueEventListener
        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d("MainActivity", "ValueEventListener : " + snapshot.getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // 유저푸시
        databaseReference.child("chat").child(CHAT_NAME).child("users_list").push().setValue(USER_NAME);

        // 유저 이미 있는지 체크
        databaseReference.child("chat").child(CHAT_NAME).child("users_list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getValue().toString() == USER_NAME) {
                        Toast.makeText(ChatActivity.this, USER_NAME + " 기다리고 있었어요!", Toast.LENGTH_LONG).show();
                    } else if (snapshot.getValue().toString() == ADMIN_NAME) {
                        Toast.makeText(ChatActivity.this, "관리자님 안녕하세요", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ChatActivity.this, "문제가 있군", Toast.LENGTH_LONG).show();

            }
        });


        databaseReference.child("chat").child(chatName).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                addMessage(dataSnapshot, adapter);
                Log.e("LOG", "s:" + s);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                removeMessage(dataSnapshot, adapter);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    //이미 가입한 유저면 팝업창 안띄우게
    //PostDetailActivity에서 호출
    public String checkUser(final String check, String chatName) {

        final String[] checkResult = new String[1];

        // 유저푸시
        databaseReference.child("chat").child(chatName).child("users_list").push().setValue(check);

        // 유저 이미 있는지 체크
        databaseReference.child("chat").child(chatName).child("users_list").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getValue().toString() == check) {
                        checkResult[0] = "1";
                    } else {
                        checkResult[0] = "0";
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return checkResult[0];
    }

}


