package com.neomu.neomu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Club_New_Activity extends AppCompatActivity {

    EditText map;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club__new_);

        map = findViewById(R.id.club_new_map_editText);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Club_New_Activity.this, MapsActivity.class);
                startActivityForResult(intent,1000);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case 1000:
                    map.setText(data.getStringExtra("result"));
                    break;
            }
        }
    }

}
