package com.example.mobilegamev;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    ImageView iv; //다트 이미지
    FrameLayout frameLayout; //다트를 다루는 frameLayout
    GestureDetector gestureDetector; //다트 움직임 이벤트 처리
    Handler handler;




    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.fLayout);

        iv = new ImageView(this);
        iv.setRotation(135);
        iv.setScaleX(0.1f);
        iv.setScaleY(0.1f);
        iv.setImageResource(R.drawable.dart);
        frameLayout.addView(iv);

        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });





        handler = new Handler();
        gestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(@NonNull MotionEvent e) {return false;}

            @Override
            public void onShowPress(@NonNull MotionEvent e) {}

            @Override
            public boolean onSingleTapUp(@NonNull MotionEvent e) {return false;}

            @Override
            public boolean onScroll(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float distanceX, float distanceY) {
                iv.setX(e2.getX()- (float) iv.getWidth() /2);
                iv.setY(e2.getY()- (float) iv.getHeight() /2);
                return false;
            }

            @Override
            public void onLongPress(@NonNull MotionEvent e) {}

            @Override
            public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
                Log.d("onFling", velocityX + " " + velocityY);
                dartAccel((int) velocityY);

                return false;
            }
        });
    }

    public Dart dartAccel(int vY){

        Runnable act = new Runnable() {
            int vy = Math.abs(vY);

            @Override
            public void run() {
                if(vy > 7000) iv.setY(iv.getY()-15);
                else if(vy > 5500) iv.setY(iv.getY()-12);
                else if(vy > 4000) iv.setY(iv.getY()-9);
                else if(vy > 2500) iv.setY(iv.getY()-7);
                else if(vy > 1000) iv.setY(iv.getY()-5);
                else iv.setY(iv.getY()-3);

                vy -= 100;

                if(vy <= 0) handler.removeCallbacks(this);
                else handler.postDelayed(this, 10);;
            }
        };

        act.run();

        Dart d = new Dart();
        d.x = iv.getX();
        d.y = iv.getY();

        return d;
    }
}

class Dart {
    float x;
    float y;
}