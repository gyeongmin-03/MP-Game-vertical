package com.example.mobilegamev;

import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
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

    FrameLayout frameLayout; //다트가 생성 되는 layout
    LinearLayout targetView; //풍선의 틀
    GestureDetector gestureDetector; //다트 움직임 이벤트 처리
    Handler handler; //다트 움직임 애니메이션을 위한 핸들러
    TextView tvScore, tvTime; //현재 점수, 남은 시간
    ImageView b0, b1, b2, b3, b4, b5, b6, b7, b8; //풍선
    BalloonLocate bl0, bl1, bl2, bl3, bl4, bl5, bl6, bl7, bl8; //풍선 위치 및 visible 상태
    BalloonLocate balloonArr[] = {bl0, bl1, bl2, bl3, bl4, bl5, bl6, bl7, bl8};

    int balloonCount = 9; //풍선 최대 갯수
    int score = 0; //현재 점수

    SoundPool soundPool; //풍선 터질 때 효과음 표현 객체
    int sound; //풍선 터질 때 효과음 

    MediaPlayer mp; //bgm 표현


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //레이아웃의 연결
        frameLayout = findViewById(R.id.fLayout);
        targetView = findViewById(R.id.targetView);

        //BalloonLocate 객체 초기화
        for (int i = 0; i< balloonArr.length; i++){
            balloonArr[i] = new BalloonLocate();
        }

        //targetView가 생성되면, 디바이스 화면에서의 위치 기반으로 풍선 위치 결정 
        targetView.post(new Runnable() {
            @Override
            public void run() {
                initLocation();
            }
        });

        //BalloonLocate 객체에 해당되는 ImageView를 연결 
        balloonArr[0].iv = (b0 = findViewById(R.id.balloon0));
        balloonArr[1].iv = (b1 = findViewById(R.id.balloon1));
        balloonArr[2].iv = (b2 = findViewById(R.id.balloon2));
        balloonArr[3].iv = (b3 = findViewById(R.id.balloon3));
        balloonArr[4].iv = (b4 = findViewById(R.id.balloon4));
        balloonArr[5].iv = (b5 = findViewById(R.id.balloon5));
        balloonArr[6].iv = (b6 = findViewById(R.id.balloon6));
        balloonArr[7].iv = (b7 = findViewById(R.id.balloon7));
        balloonArr[8].iv = (b8 = findViewById(R.id.balloon8));

        //텍스트뷰 연결
        tvScore = findViewById(R.id.tvScore);
        tvTime = findViewById(R.id.tvTime);

        //풍선 효과음 생성
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        sound = soundPool.load(this,R.raw.ballon,1);

        //bgm 실행
        mp = MediaPlayer.create(this, R.raw.zosim);
        mp.start();

        //남은시간 타이머 실행 객체
        new CountDownTimer(60*1000, 1000) {
            @Override
            public void onTick(long s) {
                int sec = (int) s/1000;
                tvTime.setText("남은 시간 : "+ sec);
            }

            @Override
            public void onFinish() {
                //bgm 중지 및 메모리 제거
                mp.stop();
                mp.release();

                //restartActivity에 점수와 함께 전달
                Intent intent = new Intent(MainActivity.this, restartActivity.class);
                intent.putExtra("score", score);
                startActivity(intent);
                finish();
            }
        }.start();

        //핸들러 초기화
        handler = new Handler();

        //다트 생성 및 움직임을 위한 터치리스너
        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);

                return true;
            }
        });


        //다트의 움직임을 위한 제스터Detector
        gestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            ImageView iv; //다트 이미지

            //터치 했을 때
            @Override
            public boolean onDown(@NonNull MotionEvent e) {
                iv = genImageView(e); //다트이미지 생성 및 속성 초기화
                frameLayout.addView(iv); //frameLayout에 추가
                
                if(e.getAction() == MotionEvent.ACTION_UP){
                    frameLayout.removeView(iv);
                }

                return true;
            }

            //짧은 시간 누른 상태일 때
            @Override
            public void onShowPress(@NonNull MotionEvent e) {
                if(e.getAction() == MotionEvent.ACTION_UP){
                    frameLayout.removeView(iv);
                }

            }

            //한 손가락으로 눌렀다 땠을 떄
            @Override
            public boolean onSingleTapUp(@NonNull MotionEvent e) {
                frameLayout.removeView(iv);

                return true;
            }

            //스크롤을 할 때 발생되는 메서드
            @Override
            public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
                //다트이미지가 손가락 따라오기
                if(e2.getY() <= (float) frameLayout.getHeight()/2){
                    iv.setY((float) frameLayout.getHeight()/2);
                }
                else {
                    iv.setY(e2.getY()-50);
                }

                iv.setX(e2.getX()-50);

                return true;
            }

            //길게 눌렀을 때
            @Override
            public void onLongPress(@NonNull MotionEvent e) {
                frameLayout.removeView(iv);
            }

            //눌렀다가 가속을 주어 튕기면서 손을 떼었을 때
            @Override
            public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
                dartAccel(iv, (int) velocityY); //가속 애니메이션 처리

                return true;
            }


        });
    }

    //풍선의 위치 좌표 설정
    public void initLocation(){
        float targetX = targetView.getX();
        float targetY = targetView.getY();
        float size = b1.getWidth();
        for (int i = 0; i< balloonArr.length; i++){
            balloonArr[i].setLocate(targetX, targetY, i, size);
        }
    }

    //다트이미지 생성 및 속성 초기화
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

    //속도vY : 160 ~ 21000
    public void dartAccel(ImageView iv ,int vY){

        Runnable act = new Runnable() {

            //왼쪽위가 (0,0)이기 때문에, 아래에서 위로 올라가는 행위는 음수값을 가진다. 때문에 -1을 곱하여 양수로 전환한다.
            int vy = vY * (-1);

            @Override
            public void run() {
                iv.setY(iv.getY() - (float) vy/1000*2); //다트의 좌표를 속도에 비례하여 위로 이동
                vy -= 100; //속도 감속

                //속도가 음수가 되었을 때
                if(vy <= 0) {
                    //다트가 각 풍선의 좌표 범위 내에 들어갔는지 검사
                    for (int i = 0; i< balloonArr.length; i++){
                        //들어갔으며
                        if(iv.getX()+50 >= balloonArr[i].sx
                                && iv.getX()+50 <= balloonArr[i].dx
                                && iv.getY() >= balloonArr[i].sy
                                && iv.getY() <= balloonArr[i].dy){

                            //풍선이 터지지않은 상태(visible)하면
                            if(balloonArr[i].visible){
                                balloonArr[i].setInvisible(); //풍선 터트리기(invisible)
                                balloonCount--; //현재 남은 풍선 갯수 감소
                                score++; //점수 추가

                                soundPool.play(sound,1,1,0,0,(float)1.2); //풍선 터트리기 효과음

                                tvScore.setText("현재 점수 : "+score); //점수 textView 갱신
                            } //if(balloonArr[i].visible)

                            //만약 풍선이 모두 터졌으면
                            if(balloonCount == 0){
                                //모든 풍선을 다시 생성
                                for (int j = 0; j< balloonArr.length; j++){
                                    balloonArr[j].setVisible();
                                    balloonCount = 9;
                                }
                                //화면에 배치된 모든 다트 제거
                                frameLayout.removeAllViews();
                            } //if(balloonCount == 0)

                        }//if(iv.getX()+50 >= balloonArr[i].sx ...)

                    } //for(int i = 0; i< balloonArr.length; i++)

                    frameLayout.removeView(iv); //속도가 음수(정지)이므로 다트가 멈춘상태 -> 제거

                    handler.removeCallbacks(this); //다트 애니메이션 중단

                } //if(vy <= 0)
                else {
                    handler.postDelayed(this, 10); //다트 애니메이션 계속
                } //else

            } //run()
        }; //Runnable act

        act.run();
    }
}

//풍선 위치 및 터짐 유무 관리
class BalloonLocate{
    float sx; //시작x
    float dx; //끝x
    float sy; //시작y
    float dy; //끝y
    ImageView iv; //풍선
    Boolean visible = true; //풍선 생존 상태

    //풍선 위치좌표 설정
    void setLocate(float x, float y, int num, float size){
        int nx = num % 3; //풍선 x index
        int ny = num / 3; //풍선 y index
        sx = x + 20 + 10 + (10+10+(int)size) * nx;
        dx = x + 20 + 10 + (int)size + (10+10+(int)size) * nx;
        sy = y + 20 + 10 + (10+10+(int)size) * ny;
        dy = y + 20 + 10 + (int)size + (10+10+(int)size) * ny;
    }

    //풍선 터짐
    void setInvisible(){
        iv.setVisibility(View.INVISIBLE);
        visible = false;
    }

    //풍선 생성
    void setVisible(){
        iv.setVisibility(View.VISIBLE);
        visible = true;
    }
}