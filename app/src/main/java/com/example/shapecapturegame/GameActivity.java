package com.example.shapecapturegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

    private ConstraintLayout cLayout;
    private TextView textViewScore;
    private TextView textViewPlay;
    private ImageView pacman;
    private ImageView shape1;
    private ImageView shape2;
    private ImageView shape3;
    private ImageView shape4;

    private int score = 0;

    //positions
    private int pacmanX;
    private int pacmanY;
    private int shape1X;
    private int shape1Y;
    private int shape2X;
    private int shape2Y;
    private int shape3X;
    private int shape3Y;
    private int shape4X;
    private int shape4Y;

    //controls
    private boolean touchControl = false;
    private boolean startControl = false;

    private Timer timer = new Timer();
    private Handler handler = new Handler();

    //dimensions - boyutlar
    private int screenWidth;
    private int screenHeight;
    private int pacmanWidht;
    private int pacmanHeight;

    //speeds
    private int pacmanSpeed;
    private int shape1Speed;
    private int shape2Speed;
    private int shape3Speed;
    private int shape4Speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        cLayout = findViewById(R.id.cLayout);
        textViewScore = findViewById(R.id.textViewScore);
        textViewPlay = findViewById(R.id.textViewPlay);
        pacman = findViewById(R.id.pacman);
        shape1 = findViewById(R.id.shape1);
        shape2 = findViewById(R.id.shape2);
        shape3 = findViewById(R.id.shape3);
        shape4 = findViewById(R.id.shape4);


        //cisimleri ekranın dışında tutma
        shape1.setX(-100);
        shape1.setY(-100);
        shape2.setX(-100);
        shape2.setY(-100);
        shape3.setX(-100);
        shape3.setY(-100);
        shape4.setX(-100);
        shape4.setY(-100);


        cLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(startControl){
                    if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                        Log.e("MotionEvent", "Ekrana dokunuldu");
                        touchControl = true;
                    }
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                        Log.e("MotionEvent", "Ekranı bıraktı");
                        touchControl = false;
                    }
                }
                else{
                    startControl = true;

                    textViewPlay.setVisibility(View.INVISIBLE);

                    pacmanX = (int) pacman.getX();
                    pacmanY = (int) pacman.getY();

                    pacmanWidht = pacman.getWidth();
                    pacmanHeight = pacman.getHeight();
                    screenWidth = cLayout.getWidth();
                    screenHeight = cLayout.getHeight();

                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    pacmanMove();
                                    shapeMove();
                                    actionControl();
                                }
                            });
                        }
                    },0,20);
                }
                return true;
            }
        });
    }

    public void pacmanMove(){

        //yuvarlama yapar (ekran yüksekliğinin 60'ta 1'i)
        pacmanSpeed = Math.round(screenHeight/60);
        if (touchControl){
            pacmanY-=pacmanSpeed;
        }
        else{
            pacmanY+=pacmanSpeed;
        }
        pacman.setY(pacmanY);

        if(pacmanY <= 0){
            pacmanY = 0;
        }
        //ana karakterin yüksekliğini ekran yüksekliğinden çıkarınca kalan fark
        //ekranın altından ana karakterin kaçmasını engelleyecek
        if(pacmanY >= screenHeight - pacmanHeight){
            pacmanY = screenHeight - pacmanHeight;
        }

        pacman.setY(pacmanY);
    }

    public void shapeMove(){

        //hızları ayarlama kısmı
        shape1Speed = Math.round(screenWidth/55);
        shape2Speed = Math.round(screenWidth/55);
        shape3Speed = Math.round(screenWidth/60); //   ex: 760/60 =13 hızı olur
        shape4Speed = Math.round(screenWidth/55);

        shape1X-=shape1Speed;

        if(shape1X < 0){
            shape1X = screenWidth + 40;
            //0 ile ekran yüksekliği arasında sayı üretecek
            shape1Y = (int) Math.floor(Math.random() * screenHeight);
        }
        shape1.setX(shape1X);
        shape1.setY(shape1Y);


        shape2X-=shape2Speed;

        if(shape2X < 0){
            shape2X = screenWidth + 40;
            //0 ile ekran yüksekliği arasında sayı üretecek
            shape2Y = (int) Math.floor(Math.random() * screenHeight);
        }
        shape2.setX(shape2X);
        shape2.setY(shape2Y);


        shape3X-=shape3Speed;

        if(shape3X < 0){
            shape3X = screenWidth + 40;
            //0 ile ekran yüksekliği arasında sayı üretecek
            shape3Y = (int) Math.floor(Math.random() * screenHeight);
        }
        shape3.setX(shape3X);
        shape3.setY(shape3Y);

        shape4X-=shape4Speed;

        if(shape4X < 0){
            shape4X = screenWidth + 40;
            //0 ile ekran yüksekliği arasında sayı üretecek
            shape4Y = (int) Math.floor(Math.random() * screenHeight);
        }
        shape4.setX(shape4X);
        shape4.setY(shape4Y);
    }

    public void actionControl(){
        //merkez noktasını bulma
        int shape1CentreX = shape1X + shape1.getWidth()/2;
        int shape1CentreY = shape1Y + shape1.getHeight()/2;

        if(0 <= shape1CentreX && shape1CentreX <= pacmanWidht
                && pacmanY <= shape1CentreY && shape1CentreY <= pacmanY + pacmanHeight){

            score += 20;
            shape1X = -10;
        }

        //merkez noktasını bulma
        int shape2CentreX = shape2X + shape2.getWidth()/2;
        int shape2CentreY = shape2Y + shape2.getHeight()/2;

        if(0 <= shape2CentreX && shape2CentreX <= pacmanWidht
                && pacmanY <= shape2CentreY && shape2CentreY <= pacmanY + pacmanHeight){

            score += 20;
            shape2X = -10;
        }

        //merkez noktasını bulma
        int shape3CentreX = shape3X + shape3.getWidth()/2;
        int shape3CentreY = shape3Y + shape3.getHeight()/2;

        if(0 <= shape3CentreX && shape3CentreX <= pacmanWidht
                && pacmanY <= shape3CentreY && shape3CentreY <= pacmanY + pacmanHeight){

            score += 40;
            shape3X = -10;
        }

        //merkez noktasını bulma
        int shape4CentreX = shape4X + shape4.getWidth()/2;
        int shape4CentreY = shape4Y + shape4.getHeight()/2;

        if(0 <= shape4CentreX && shape4CentreX <= pacmanWidht
                && pacmanY <= shape4CentreY && shape4CentreY <= pacmanY + pacmanHeight){

            shape4X = -10;

            //Timer durdur
            timer.cancel();
            timer = null;

            Intent intent = new Intent(GameActivity.this,FinishActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);
            finish();
        }
        textViewScore.setText(String.valueOf(score));
    }
}