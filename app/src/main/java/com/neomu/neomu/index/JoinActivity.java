package com.neomu.neomu.index;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.neomu.neomu.club.MainActivity;
import com.neomu.neomu.R;
import com.neomu.neomu.models.User;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {

    Button main_join_btn;
    EditText nickName, email, pw, pw2;
    String mail, password, nickname;
    NumberPicker picker_year, picker_month;
    Toast toast;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        main_join_btn = findViewById(R.id.main_join_btn);
        main_join_btn.setOnClickListener(this);

        nickName = findViewById(R.id.main_join_nickName);
        email = findViewById(R.id.main_join_email);
        pw = findViewById(R.id.main_join_pw);
        pw2 = findViewById(R.id.main_join_pw2);


        picker_year = findViewById(R.id.picker_year);
        picker_month = findViewById(R.id.picker_month);

        picker_year.setMinValue(1990);
        picker_year.setMaxValue(2010);

        picker_month.setMinValue(1);
        picker_month.setMaxValue(12);


    }

    @Override
    public void onClick(View v) {

        String pass1 = pw.getText().toString();
        String pass2 = pw2.getText().toString();
        nickname = nickName.getText().toString();
        mail = email.getText().toString();

        //유효성 검사

        if (nickname.equals("") || mail.equals("") || pass1.equals("") || pass2.equals("")) {
            Toast.makeText(this, "공백란을 모두 채워주세요", Toast.LENGTH_LONG).show();

            //이메일 유효성검사
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            Toast.makeText(JoinActivity.this, "올바른 메일주소 형식이 아닙니다..", Toast.LENGTH_SHORT).show();

            //비밀번호 길이 체크
        } else if ((TextUtils.isEmpty(pass1) || pw.length() < 6) && (TextUtils.isEmpty(pass2) || pw2.length() < 6)) {
            Toast.makeText(this, "비밀번호는 최소 6글자를 채워주세요", Toast.LENGTH_LONG).show();

            //비밀번호 일치 확인
        } else if (!pass1.equals(pass2)) {
            Toast.makeText(this, "비밀번호 불일치!", Toast.LENGTH_LONG).show();

        } else {
            signUp();
        }

    }


    // 회원가입

    private void signUp() {

        String password = pw2.getText().toString();
        String email2 = email.getText().toString();

        mAuth.createUserWithEmailAndPassword(email2, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(JoinActivity.this, "이미 존재하는 계정입니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());


            //성별 값
            RadioGroup rg = (RadioGroup) findViewById(R.id.join_gender); // 라디오그룹 객체 맵핑
            RadioButton selectedRdo = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
            String gender = selectedRdo.getText().toString(); // 해당 라디오버튼 객체의 값 가져오기

            //생년월일 값
            String birth = String.valueOf(picker_year.getValue()) + "년 " + String.valueOf(picker_month.getValue()) + "월";


            // Write new user
            writeNewUser(user.getUid(), nickname, user.getEmail(), gender, birth);



        // Go to MainActivity
        startActivity(new Intent(JoinActivity.this, MainActivity.class));
        finish();
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    // [START basic_write]
    private void writeNewUser(String userId, String name, String email, String gender, String birth) {
        User user = new User(name, email, gender, birth);
        mDatabase.child("users").child(userId).setValue(user);
    }
    // [END basic_write]

}
