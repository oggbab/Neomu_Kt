package com.neomu.neomu.app.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.neomu.neomu.BuildConfig;
import com.neomu.neomu.R;

//스플래시(대기) 액티비티
public class SpalshActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        relativeLayout = findViewById(R.id.rel_splash);
        firebaseRemoteControll();
    }

    //파이어베이스 콘솔에서 속성 제어할 수 있게 설정
    private void firebaseRemoteControll() {
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
        relativeLayout.setBackgroundColor(Color.parseColor(Splash_background));

        if (caps) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(Splash_message).setPositiveButton(getResources().getString(Integer.parseInt("text_splash_btn_text")), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.create().show();
        } else {
            //caps가 false일경우 액티비티 이동
            Intent intent = new Intent(SpalshActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
