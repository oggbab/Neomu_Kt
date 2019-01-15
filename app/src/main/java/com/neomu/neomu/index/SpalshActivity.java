package com.neomu.neomu.index;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.neomu.neomu.BuildConfig;
import com.neomu.neomu.R;
import com.neomu.neomu.map.MapsActivity;

//스플래시(대기) 액티비티
public class SpalshActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        linearLayout = findViewById(R.id.splash);

        //파이어베이스 콘솔에서 속성 제어할 수 있게 설정
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.default_config);

        mFirebaseRemoteConfig.fetch(0)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mFirebaseRemoteConfig.activateFetched();
                        }
                        displayMessage();
                    }
                });


    }

    void displayMessage() {

        //콘솔 리모트컨픽 설정 가져오기
        String Splash_background = mFirebaseRemoteConfig.getString("Splash_background");
        boolean caps = mFirebaseRemoteConfig.getBoolean("Splash_message_caps");
        String Splash_message = mFirebaseRemoteConfig.getString("Splash_message");

        linearLayout.setBackgroundColor(Color.parseColor(Splash_background));

        if (caps) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(Splash_message).setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //그냥 종료하는걸로 일단
//                    finish();

                }
            });
            builder.create().show();
        } else {
            //caps가 false일경우 액티비티 이동
//            startActivity(new Intent(SpalshActivity.this, LoginActivity.class));
            startActivity(new Intent(SpalshActivity.this, LoginActivity.class));
        }
    }
}
