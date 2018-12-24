package com.neomu.neomu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JoinDetailActivity extends AppCompatActivity {

    Intent intent;
    NumberPicker picker_year, picker_month;
    Button main_join_detail_btn;
//    Chip chip;
    String chip_result;

    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_detail);

        database = FirebaseDatabase.getInstance();
        intent = getIntent();

        picker_year = findViewById(R.id.picker_year);
        picker_month = findViewById(R.id.picker_month);

        picker_year.setMinValue(1990);
        picker_year.setMaxValue(2010);

        picker_month.setMinValue(1);
        picker_month.setMaxValue(12);

        main_join_detail_btn = findViewById(R.id.main_join_detail_btn);

/*        ChipGroup chipGroup = findViewById(R.id.chip_group);

        chipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {

                chip = chipGroup.findViewById(i);
                chip_result=(String)chip.getChipText();
                Toast.makeText(JoinDetailActivity.this, "Chip is " + chip_result, Toast.LENGTH_SHORT).show();
            }
        });*/




        main_join_detail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String birth = String.valueOf(picker_year.getValue())+"년 "+ String.valueOf(picker_month.getValue())+"월";
//                if(chip_result!=null||birth!=null) {

                    intent = getIntent();
                    myRef = database.getReference("nickName");
                    myRef.setValue(intent.getExtras().getString("nickName"));
                    myRef = database.getReference("email");
                    myRef.setValue(intent.getExtras().getString("email"));
                    myRef = database.getReference("gender");
                    myRef.setValue(intent.getExtras().getString("gender"));
                    myRef = database.getReference("category");
                    myRef.setValue(chip_result);
                    myRef = database.getReference("birth");
                    myRef.setValue(birth);
/*

                }else{
                    Toast.makeText(JoinDetailActivity.this,"모두 선택해주세요", Toast.LENGTH_SHORT).show();
                }
*/

            }
        });


    }
}