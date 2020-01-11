package com.example.ujian4_quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Tampilan extends AppCompatActivity {

    ImageView onClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampilan);
    }
    public void onClick(View v){
        Intent intent = new Intent(Tampilan.this,question_activity.class);
    }
}
