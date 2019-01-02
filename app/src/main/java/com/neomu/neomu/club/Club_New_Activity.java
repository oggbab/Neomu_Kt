package com.neomu.neomu.club;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPickerListener;
import com.neomu.neomu.R;
import com.neomu.neomu.models.Post;
import com.neomu.neomu.models.User;

import java.sql.Array;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class Club_New_Activity extends AppCompatActivity{

    EditText map;
    TextView et_location, price_text;
    Intent intent;
    Button mSubmitButton;
    SeekBar price;
    String category;
    ScrollableNumberPicker pricePicker;

    TextView club_new_date_text,club_new_time_text;
    Button club_new_date_btn,club_new_time_btn;

    EditText et_body, et_title;
    ImageView bg_img;
    public int number = 0;

    private CheckBox[] chkBoxs;
    private Integer[] chkBoxIds = {R.id.check_club_1, R.id.check_club_2, R.id.check_club_3, R.id.check_club_4, R.id.check_club_5
            , R.id.check_club_6, R.id.check_club_7, R.id.check_club_8};


    private static final String TAG = "Club_New_Activity";
    private static final String ERROR_MSG = "공백을 입력해주세요";

    private DatabaseReference mDatabase;

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club__new_);


        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        et_title = findViewById(R.id.club_title);
        et_location = findViewById(R.id.club_location);
        price_text = findViewById(R.id.club_new_price_text);
        et_body = findViewById(R.id.club_new_intro);
        mSubmitButton = findViewById(R.id.club_new_btn);

        club_new_date_text = findViewById(R.id.club_new_date_text);
        club_new_time_text = findViewById(R.id.club_new_time_text);

        pricePicker = findViewById(R.id.numberPicker);
/*        //체크박스
        chkBoxs = new CheckBox[chkBoxIds.length];
        for(int i = 0; i < chkBoxIds.length; i++) {
            chkBoxs[i] = (CheckBox) findViewById(chkBoxIds[i]);
            chkBoxs[i].setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) this);
        }*/

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
        //날짜시간 클릭 다이얼로그
        init();
        pricePicker.setListener(new ScrollableNumberPickerListener() {
            @Override
            public void onNumberPicked(int value) {
                price_text.setText(String.valueOf((value)/1000));
            }
        });

    }

    //다이얼로그 메소드
    private void init() {

        final Calendar cal = Calendar.getInstance();

        Log.e(TAG, cal.get(Calendar.YEAR) + "");
        Log.e(TAG, cal.get(Calendar.MONTH) + 1 + "");
        Log.e(TAG, cal.get(Calendar.DATE) + "");
        Log.e(TAG, cal.get(Calendar.HOUR_OF_DAY) + "");
        Log.e(TAG, cal.get(Calendar.MINUTE) + "");

        //날짜 다이얼로그
        findViewById(R.id.club_new_date_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(Club_New_Activity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        String msg = String.format("%d 년 %d 월 %d 일", year, month + 1, date);
                        club_new_date_text.setText(msg);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                dialog.getDatePicker().setMinDate(new Date().getTime());
                dialog.show();
            }

        });


        //시간 다이얼로그
        findViewById(R.id.club_new_time_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog dialog = new TimePickerDialog(Club_New_Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int min) {

                        String msg = String.format("%d 시 %d 분", hour, min);
                        club_new_time_text.setText(msg);
                    }
                }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true);  //마지막 boolean 값은 시간을 24시간으로 보일지 아닐지

                dialog.show();

            }
        });
    }


    public void printSelected() {
        price_text.setText(new StringBuilder().append(number));
    }

    private void submitPost() {
        final String title = et_title.getText().toString();
        final String body = et_body.getText().toString();
        final String location = et_location.getText().toString();
        final String price_result = price_text.getText().toString();
        final String category_result = "카테고리";
//        final String category_result = String.valueOf(chkBoxs[chkBoxs.length]);


        //공백일때 보여질 메세지 설정
        if (TextUtils.isEmpty(title)) {
            et_title.setError(ERROR_MSG);
            return;
        }

        if (TextUtils.isEmpty(body)) {
            et_body.setError(ERROR_MSG);
            return;
        }

        Toast.makeText(this, "포스팅중입니다...", Toast.LENGTH_SHORT).show();


        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get user value
                        User user = dataSnapshot.getValue(User.class);
                        writeNewPost(userId, user.nickName, title, body, price_result, category_result);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }

                });
        // [END single_value_read]
    }


    // 글쓰기
    private void writeNewPost(String userId, String nickName, String title, String body, String price_result, String category) {

        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(userId, nickName, title, body, price_result, category);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }

}
