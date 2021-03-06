package com.neomu.neomu.app.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.neomu.neomu.R;
import com.neomu.neomu.app.club.MainActivity;
import com.neomu.neomu.app.main.util.JoinValidation;
import com.neomu.neomu.common.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//로그인 액티비티
public class LoginActivity extends BaseActivity{

    FirebaseAuth firebaseAuth;

    @BindView(R.id.login_id) EditText login_id;
    @BindView(R.id.login_pw) EditText login_pw;
    public final String MSG_SIGNIN_FAIL = "로그인 실패";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    //동일한 액티비티 재실행될경우(백버튼이든) 실행됨
    //startActivityForResult()나 finish() 사용할 경우 실행안됨
    //activity를 전체 로드하지않고 필요기능만 사용가능한 장점
    //
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String msg_newIntent = intent.getStringExtra("logTest");
        showToast(msg_newIntent,true);
    }

    @OnClick({R.id.login_btn, R.id.main_linkText})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                new JoinValidation().isValidation(login_id, null, login_pw, null, new JoinValidation.ValidationListener() {
                    @Override
                    public void onInvalid(View view, String msg) {
                    }

                    @Override
                    public void onSuccess() {
                        firebaseAuth.signInWithEmailAndPassword(login_id.getText().toString(), login_pw.getText().toString())
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                            finish();
                                        } else {
                                            showToast(MSG_SIGNIN_FAIL, true);
                                        }
                                    }
                                });
                    }
                });
                case R.id.main_linkText:
                    Intent intent = new Intent(LoginActivity.this, AuthActivity.class);
                    intent.putExtra("logTest","onNewIntent()실행됨");
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
        }
    }

}