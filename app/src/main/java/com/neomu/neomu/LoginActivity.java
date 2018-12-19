package com.neomu.neomu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//로그인 액티비티
public class LoginActivity extends AppCompatActivity {

    Button login_btn;
    TextView main_linkText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn=findViewById(R.id.login_btn);
        main_linkText = findViewById(R.id.main_linkText);
        main_linkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
