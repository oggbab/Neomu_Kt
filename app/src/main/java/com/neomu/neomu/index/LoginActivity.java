package com.neomu.neomu.index;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DatabaseReference;
import com.neomu.neomu.club.Club_New_Activity;
import com.neomu.neomu.club.MainActivity;
import com.neomu.neomu.R;

//로그인 액티비티
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;

    Button login_btn;
    TextView main_linkText;
    EditText login_id, login_pw;

    //테스트버튼
    TextView test_btn;
    TextView test_map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //서브 테스트용 버튼
        test_btn = findViewById(R.id.test_btn);
        test_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,Club_New_Activity.class));
            }
        });

        test_map = findViewById(R.id.test_map);
        test_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });


        login_id = findViewById(R.id.login_id);
        login_pw = findViewById(R.id.login_pw);

        login_btn = findViewById(R.id.login_btn);
        main_linkText = findViewById(R.id.main_linkText);
        main_linkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, AuthActivity.class));
            }
        });
        login_btn.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.login_btn:

//                final String id = login_id.getText().toString().trim();
                final String id = login_id.getText().toString();
                final String pw = login_pw.getText().toString();


                if (id != null || pw != null) {

                    firebaseAuth.signInWithEmailAndPassword(id, pw)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();

                                    } else {
                                        Toast.makeText(LoginActivity.this, "계정과 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(LoginActivity.this, "모두 입력해주세요", Toast.LENGTH_LONG).show();
                }

        }
    }

}