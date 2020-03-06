package com.neomu.neomu.app.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.neomu.neomu.R;
import com.neomu.neomu.app.club.MainActivity;
import com.neomu.neomu.app.main.util.JoinValidation;
import com.neomu.neomu.app.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JoinActivity extends BaseActivity  {

    @BindView(R.id.tv_join_nickName) EditText nickName;
    @BindView(R.id.tv_join_email) EditText email;
    @BindView(R.id.tv_join_pw) EditText pw;
    @BindView(R.id.tv_join_pw2) EditText pw2;
    @BindView(R.id.picker_year) NumberPicker picker_year;
    @BindView(R.id.picker_month) NumberPicker picker_month;
    @BindView(R.id.rg_join_gender) RadioGroup rg;
    @BindView(R.id.rb_male) RadioButton rBtn_male;

    RadioButton selectedRdo;
    String gender;
    String birth;
    public final String MSG_INVALID_PW_MATCH = "이미 존재하는 계정입니다.";
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        ButterKnife.bind(this);
    }

    //아이디 유무 체크
    public void checkAlreadyJoined(){
        //이미 가입한 계정인지 체크
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), pw.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            onAuthSuccess(task.getResult().getUser());
                        } else {
                            Toast.makeText(JoinActivity.this, MSG_INVALID_PW_MATCH,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void onAuthSuccess(FirebaseUser user) {
        selectedRdo = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
        gender = selectedRdo.getText().toString();
        pickerSetting();

        Log.e("jj", ">>>> gender : " + gender);
        // Write new user
        writeNewUser(user.getUid(), nickName.getText().toString(), user.getEmail(), gender, birth);

        Intent intent = new Intent(JoinActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void writeNewUser(String userId, String name, String email, String gender, String birth) {
        User user = new User(name, email, gender, birth);
        mDatabase.child("users").child(userId).setValue(user);
    }

    private void pickerSetting() {
        //생년월일 값
        birth = (picker_year.getValue()+ "년 " + picker_month.getValue() + "월");
        picker_year.setMinValue(1990);
        picker_year.setMaxValue(2010);
        picker_month.setMinValue(1);
        picker_month.setMaxValue(12);
    }

    @OnClick (R.id.btn_join)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_join:
                //유효성 검사
                //static 제거
                new JoinValidation().isValidation(email, nickName, pw, pw2, new JoinValidation.ValidationListener() {
                    @Override
                    public void onInvalid(View view, String msg) {
                        Toast.makeText(JoinActivity.this, msg, Toast.LENGTH_LONG).show();
                        view.requestFocus();
                    }

                    @Override
                    public void onSuccess() {
                        checkAlreadyJoined();
                    }
                });
                break;
        }
    }
}