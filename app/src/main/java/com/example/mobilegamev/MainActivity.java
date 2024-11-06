package com.example.mobilegamev;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    LinearLayout targetView; //다트를 다루는 frameLayout
    GestureDetector gestureDetector; //다트 움직임 이벤트 처리
    Handler handler;
    TextView tvScore, tvTime;

    ImageView b0, b1, b2, b3, b4, b5, b6, b7, b8;

    BalloonLocate bl0, bl1, bl2, bl3, bl4, bl5, bl6, bl7, bl8;

    BalloonLocate balloonArr[] = {bl0, bl1, bl2, bl3, bl4, bl5, bl6, bl7, bl8};

    int balloonCount = 9;
    int score = 0;

    SoundPool soundPool;
    int sound;

    MediaPlayer mp;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        frameLayout = findViewById(R.id.fLayout);
        targetView = findViewById(R.id.targetView);

        for (int i = 0; i< balloonArr.length; i++){
            balloonArr[i] = new BalloonLocate();
        }
        targetView.post(new Runnable() {
            @Override
            public void run() {
                initLocation();
            }
        });

        balloonArr[0].iv = (b0 = findViewById(R.id.balloon0));
        balloonArr[1].iv = (b1 = findViewById(R.id.balloon1));
        balloonArr[2].iv = (b2 = findViewById(R.id.balloon2));
        balloonArr[3].iv = (b3 = findViewById(R.id.balloon3));
        balloonArr[4].iv = (b4 = findViewById(R.id.balloon4));
        balloonArr[5].iv = (b5 = findViewById(R.id.balloon5));
        balloonArr[6].iv = (b6 = findViewById(R.id.balloon6));
        balloonArr[7].iv = (b7 = findViewById(R.id.balloon7));
        balloonArr[8].iv = (b8 = findViewById(R.id.balloon8));

        tvScore = findViewById(R.id.tvScore);
        tvTime = findViewById(R.id.tvTime);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        sound = soundPool.load(this,R.raw.ballon,1);

        mp = MediaPlayer.create(this, R.raw.zosim);
        mp.start();

        new CountDownTimer(60*1000, 1000) {
            @Override
            public void onTick(long s) {
                int sec = (int) s/1000;
                tvTime.setText("남은 시간 : "+ sec);
            }

            @Override
            public void onFinish() {
                mp.stop();
                mp.release();

                Intent intent = new Intent(MainActivity.this, restartActivity.class);
                intent.putExtra("score", score);
                startActivity(intent);
                finish();
            }
        }.start();

        handler = new Handler();

        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);

                return true;
            }
        });



        gestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            ImageView iv; //다트 이미지


            @Override
            public boolean onDown(@NonNull MotionEvent e) {
                iv = genImageView(e);
                frameLayout.addView(iv);

                if(e.getAction() == MotionEvent.ACTION_UP){
                    frameLayout.removeView(iv);
                }

                return true;
            }

            @Override
            public void onShowPress(@NonNull MotionEvent e) {
                if(e.getAction() == MotionEvent.ACTION_UP){
                    frameLayout.removeView(iv);
                }

            }

            @Override
            public boolean onSingleTapUp(@NonNull MotionEvent e) {
                frameLayout.removeView(iv);

                return true;
            }

            @Override
            public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
                if(e2.getY() <= (float) frameLayout.getHeight()/2){
                    iv.setY((float) frameLayout.getHeight() /2);
                }
                else {
                iv.setY(e2.getY()-50);
                }

                iv.setX(e2.getX()-50);

                return true;
            }

            @Override
            public void onLongPress(@NonNull MotionEvent e) {
                frameLayout.removeView(iv);
            }

            @Override
            public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
                dartAccel(iv, (int) velocityY);

                return true;
            }


        });
    }

    public void initLocation(){
        float targetX = targetView.getX();
        float targetY = targetView.getY();
        float size = b1.getWidth();
        for (int i = 0; i< balloonArr.length; i++){
            balloonArr[i].setLocate(targetX, targetY, i, size);
        }
    }

    public ImageView genImageView(MotionEvent e){
        ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.dart);
        iv.setRotation(135);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
        iv.setLayoutParams(params);
        iv.setX(e.getX()-50);
        iv.setY(e.getY()-50);
        iv.setColorFilter(Color.parseColor("#ff424242"));

        return iv;
    }

    //최대속도 : 21000
    //최소 : 160정도
    public void dartAccel(ImageView iv ,int vY){

        Runnable act = new Runnable() {
            int vy = Math.abs(vY);

            @Override
            public void run() {
                iv.setY(iv.getY() - (float) vy/1000*2);
                vy -= 100;

                if(vy <= 0) {

                    for (int i = 0; i< balloonArr.length; i++){
                        if(iv.getX()+50 >= balloonArr[i].sx
                                && iv.getX()+50 <= balloonArr[i].dx
                                && iv.getY() >= balloonArr[i].sy
                                && iv.getY() <= balloonArr[i].dy){

                            if(balloonArr[i].visible){
                                balloonArr[i].setInvisible();
                                balloonCount--;
                                score++;

                                soundPool.play(sound,1,1,0,0,(float)1.2);

                                tvScore.setText("현재 점수 : "+score);
                            }

                            if(balloonCount == 0){
                                for (int j = 0; j< balloonArr.length; j++){
                                    balloonArr[j].setVisible();
                                    balloonCount = 9;
                                }
                                frameLayout.removeAllViews();
                            }
                        }
                    }

                    frameLayout.removeView(iv);

                    handler.removeCallbacks(this);
                }
                else {
                    handler.postDelayed(this, 10);
                }
            }
        };


        act.run();
    }
}

class BalloonLocate{
    float sx; //시작x
    float dx; //끝x
    float sy; //시작y
    float dy; //끝y
    ImageView iv;
    Boolean visible = true;

    void setLocate(float x, float y, int num, float size){
        int nx = num % 3;
        int ny = num / 3;
        sx = x + 20 + 10 + (10+10+(int)size) * nx;
        dx = x + 20 + 10 + (int)size + (10+10+(int)size) * nx;
        sy = y + 20 + 10 + (10+10+(int)size) * ny;
        dy = y + 20 + 10 + (int)size + (10+10+(int)size) * ny;
    }

    void setInvisible(){
        iv.setVisibility(View.INVISIBLE);
        visible = false;
    }

    void setVisible(){
        iv.setVisibility(View.VISIBLE);
        visible = true;
    }
}