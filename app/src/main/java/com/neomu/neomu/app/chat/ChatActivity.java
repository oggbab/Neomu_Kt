package com.neomu.neomu.app.chat;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.neomu.neomu.R;
import com.neomu.neomu.app.club.Club_New_Activity;
import com.neomu.neomu.app.club.MainActivity;
import com.neomu.neomu.app.map.MapyActivity;
import com.neomu.neomu.app.mypage.MypageActivity;
import com.neomu.neomu.common.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends BaseActivity {

    @BindView(R.id.lv_chatView) ListView lv_chatView;
    @BindView(R.id.et_chatEdit) EditText et_chatEdit;
    @BindView(R.id.btn_chatSent) Button btn_chatSent;
    @BindView(R.id.et_chatTitle) EditText et_chatTitle;
    @BindView(R.id.toolbar_chat) Toolbar toolbar_chat;
    @BindView(R.id.tv_naviId) TextView tv_naviId;
    @BindView(R.id.drawer_chat) DrawerLayout drawer_chat;
    @BindView(R.id.nv_main) NavigationView nv_main;

    public String CHAT_NAME;
    public String USER_NAME;
    public String ADMIN_NAME;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        //todo intent가 아닌 SharedF 로 데이터 옮기는 걸로
        // 로그인 화면에서 받아온 채팅방 이름, 유저 이름 저장
        Intent intent = getIntent();
        CHAT_NAME = intent.getStringExtra("chatName");
        USER_NAME = intent.getStringExtra("userName");
        ADMIN_NAME = intent.getStringExtra("adminName");
        et_chatTitle.setText(CHAT_NAME);
//        CHAT_NAME = "2모임테스트중";


        // 채팅 방 입장
        openChat(CHAT_NAME);

        // 메시지 전송 버튼에 대한 클릭 리스너 지정
        btn_chatSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_chatEdit.getText().toString().equals(""))
                    return;

                ChatDTO chat = new ChatDTO(USER_NAME, et_chatEdit.getText().toString()); //ChatDTO를 이용하여 데이터를 묶는다.
                databaseReference.child("chat").child(CHAT_NAME).push().setValue(chat); // 데이터 푸쉬
                et_chatEdit.setText(""); //입력창 초기화

            }
        });

        // 1-1 툴바, 네비게이션 뷰

        setSupportActionBar(toolbar_chat);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_chat.setNavigationIcon(R.drawable.ic_menu_black_24dp);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer_chat, toolbar_chat, 0, 0);
        drawer_chat.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        // 2-1 네비게이션뷰 클릭

        View view = nv_main.getHeaderView(0);
        tv_naviId.setText(USER_NAME);

        nv_main.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.first:
                        Intent intent = new Intent(ChatActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        drawer_chat.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.second:
                        intent = new Intent(ChatActivity.this, MypageActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        drawer_chat.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.third:
                        //미구현 상태
                        showToast("즐겨찾기 설정해요",false);
                        drawer_chat.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.fourth:
                        intent = new Intent(ChatActivity.this, MapyActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.fifth:
                        intent = new Intent(ChatActivity.this, Club_New_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
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
        lv_chatView.setAdapter(adapter);

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


