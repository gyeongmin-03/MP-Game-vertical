package com.example.mobilegamev;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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

        new CountDownTimer(60*1000, 1000) {
            @Override
            public void onTick(long s) {
                int sec = (int) s/1000;
                tvTime.setText("남은 시간 : "+ sec);
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(MainActivity.this, restartActivity.class);
                intent.putExtra("score", score);
                startActivity(intent);
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
//                    iv = null;
                }

                return true;
            }

            @Override
            public void onShowPress(@NonNull MotionEvent e) {
                if(e.getAction() == MotionEvent.ACTION_UP){
                    frameLayout.removeView(iv);
//                    iv = null;
                }

            }

            @Override
            public boolean onSingleTapUp(@NonNull MotionEvent e) {
                frameLayout.removeView(iv);
//                iv = null;

                return true;
            }

            @Override
            public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
//                if(e2.getY() <= (float) frameLayout.getHeight()/2){
//                    iv.setY((float) frameLayout.getHeight() /2);
//                }
//                else {
//                iv.setY(e2.getY()-50);
//                }
                iv.setY(e2.getY()-50);

                iv.setX(e2.getX()-50);

                return true;
            }

            @Override
            public void onLongPress(@NonNull MotionEvent e) {
                frameLayout.removeView(iv);
//                iv = null;
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
        for (int i = 0; i< balloonArr.length; i++){
            balloonArr[i].setLocate(targetX, targetY, i);
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

    void setLocate(float x, float y, int num){
        int nx = num % 3;
        int ny = num / 3;
        sx = x + 20 + 10 + (10+10+270) * nx;
        dx = x + 20 + 10 + 270 + (10+10+270) * nx;
        sy = y + 20 + 10 + (10+10+270) * ny;
        dy = y + 20 + 10 + 270 + (10+10+270) * ny;
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


/**
 * 프레임 x = getX, y = getY
 *
 * 벌룬1 : x+30~300, y+30~300
 * 벌룬2 : x+320~590, y+30~300
 * 벌룬3 : x+610~880, y+30~300
 *
 * 벌룬4 : x+30~300, y+3f20~590
 * 벌룬5 : x+320~590, y+320~590
 * 벌룬6 : x+610~880, y+320~590
 *
 * 벌룬7 : x+30~300, y+610~880
 * 벌룬8 : x+320~590, y+610~880
 * 벌룬9 : x+610~880, y+610~880
 * */