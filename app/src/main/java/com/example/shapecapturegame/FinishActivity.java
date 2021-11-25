package com.example.shapecapturegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinishActivity extends AppCompatActivity {

    private TextView totalScore;
    private TextView textViewHighestScore;
    private Button buttonRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        totalScore = findViewById(R.id.TotalScore);
        textViewHighestScore = findViewById(R.id.HighestScore);
        buttonRepeat = findViewById(R.id.buttonRepeat);

        int score = getIntent().getIntExtra("score",0);
        totalScore.setText(String.valueOf(score));

        SharedPreferences sp = getSharedPreferences("EndScore", Context.MODE_PRIVATE);
        int highestScore = sp.getInt("highestScore",0);

        if(score > highestScore){
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt("highestScore",score);
            editor.commit();

            textViewHighestScore.setText(String.valueOf(score));
        }
        else{
            textViewHighestScore.setText(String.valueOf(highestScore));
        }

    }

    public void repeatGame(View view){
        startActivity(new Intent(FinishActivity.this, MainActivity.class));
        finish();
    }
}