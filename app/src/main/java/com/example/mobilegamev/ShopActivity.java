package com.example.mobilegamev;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class ShopActivity extends TabActivity {

    ImageView back1, back2, back3;
    ImageView dart1, dart2, dart3;
    ImageView balloon1, balloon2, balloon3;
    ImageView backCheck1, backCheck2, backCheck3;
    ImageView dartCheck1, dartCheck2, dartCheck3;
    ImageView balloonCheck1, balloonCheck2, balloonCheck3;
    TextView btnClose;

    FrameLayout backgroundShop, dartShop, balloonShop;


    int selectBack, selectDart, selectBalloon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity);

        TabHost tabHost = getTabHost();
        TabHost.TabSpec tabSpecBackground = tabHost.newTabSpec("BACKGROUND").setIndicator("배경");
        tabSpecBackground.setContent(R.id.backgroundShop);
        tabHost.addTab(tabSpecBackground);

        TabHost.TabSpec tabSpecDart = tabHost.newTabSpec("DART").setIndicator("다트");
        tabSpecDart.setContent(R.id.dartShop);
        tabHost.addTab(tabSpecDart);

        TabHost.TabSpec tabSpecBalloon = tabHost.newTabSpec("BALLOON").setIndicator("풍선");
        tabSpecBalloon.setContent(R.id.balloonShop);
        tabHost.addTab(tabSpecBalloon);

        tabHost.setCurrentTab(0);

        btnClose = findViewById(R.id.btnClose);

        backgroundShop = findViewById(R.id.backgroundShop);
        dartShop = findViewById(R.id.dartShop);
        balloonShop = findViewById(R.id.balloonShop);

        back1 = findViewById(R.id.back1);
        back2 = findViewById(R.id.back2);
        back3 = findViewById(R.id.back3);
        backCheck1 = findViewById(R.id.backCheck1);
        backCheck2 = findViewById(R.id.backCheck2);
        backCheck3 = findViewById(R.id.backCheck3);

        dart1 = findViewById(R.id.dart1);
        dart2 = findViewById(R.id.dart2);
        dart3 = findViewById(R.id.dart3);
        dartCheck1 = findViewById(R.id.dartCheck1);
        dartCheck2 = findViewById(R.id.dartCheck2);
        dartCheck3 = findViewById(R.id.dartCheck3);

        balloon1 = findViewById(R.id.balloon1);
        balloon2 = findViewById(R.id.balloon2);
        balloon3 = findViewById(R.id.balloon3);
        balloonCheck1 = findViewById(R.id.balloonCheck1);
        balloonCheck2 = findViewById(R.id.balloonCheck2);
        balloonCheck3 = findViewById(R.id.balloonCheck3);


        SharedPreferences pref = getSharedPreferences("save", Context.MODE_PRIVATE);
        selectBack = pref.getInt("back", 1);
        selectDart = pref.getInt("dart", 1);
        selectBalloon = pref.getInt("balloon", 1);

        if(selectBack == 1){
            backCheck1.setVisibility(View.VISIBLE);
            backCheck2.setVisibility(View.INVISIBLE);
            backCheck3.setVisibility(View.INVISIBLE);

            backgroundShop.setBackground(getDrawable(R.drawable.game_background1));
        }
        else if(selectBack == 2){
            backCheck2.setVisibility(View.VISIBLE);
            backCheck1.setVisibility(View.INVISIBLE);
            backCheck3.setVisibility(View.INVISIBLE);

            backgroundShop.setBackground(getDrawable(R.drawable.game_background3));

        }else if(selectBack == 3){
            backCheck3.setVisibility(View.VISIBLE);
            backCheck2.setVisibility(View.INVISIBLE);
            backCheck1.setVisibility(View.INVISIBLE);
            backgroundShop.setBackground(getDrawable(R.drawable.game_background2));

        }


        if(selectDart == 1){
            dartCheck1.setVisibility(View.VISIBLE);
            dartCheck2.setVisibility(View.INVISIBLE);
            dartCheck3.setVisibility(View.INVISIBLE);

            dartShop.setBackground(getDrawable(R.drawable.dart1));
        }
        else if(selectDart == 2){
            dartCheck2.setVisibility(View.VISIBLE);
            dartCheck1.setVisibility(View.INVISIBLE);
            dartCheck3.setVisibility(View.INVISIBLE);

            dartShop.setBackground(getDrawable(R.drawable.dart2));

        }else if(selectDart == 3){
            dartCheck3.setVisibility(View.VISIBLE);
            dartCheck2.setVisibility(View.INVISIBLE);
            dartCheck1.setVisibility(View.INVISIBLE);
            dartShop.setBackground(getDrawable(R.drawable.dart3));
        }



        if(selectBalloon == 1){
            balloonCheck1.setVisibility(View.VISIBLE);
            balloonCheck2.setVisibility(View.INVISIBLE);
            balloonCheck3.setVisibility(View.INVISIBLE);

            balloonShop.setBackground(getDrawable(R.drawable.balloon1));
        }
        else if(selectBalloon == 2){
            balloonCheck2.setVisibility(View.VISIBLE);
            balloonCheck1.setVisibility(View.INVISIBLE);
            balloonCheck3.setVisibility(View.INVISIBLE);
            balloonShop.setBackground(getDrawable(R.drawable.balloon2));

        }else if(selectBalloon == 3){
            balloonCheck3.setVisibility(View.VISIBLE);
            balloonCheck2.setVisibility(View.INVISIBLE);
            balloonCheck1.setVisibility(View.INVISIBLE);
            balloonShop.setBackground(getDrawable(R.drawable.balloon3));

        }



        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backCheck1.setVisibility(View.VISIBLE);
                backCheck2.setVisibility(View.INVISIBLE);
                backCheck3.setVisibility(View.INVISIBLE);
                selectBack = 1;

                backgroundShop.setBackground(getDrawable(R.drawable.game_background1));
            }
        });

        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backCheck2.setVisibility(View.VISIBLE);
                backCheck1.setVisibility(View.INVISIBLE);
                backCheck3.setVisibility(View.INVISIBLE);
                selectBack = 2;

                backgroundShop.setBackground(getDrawable(R.drawable.game_background3));
            }
        });

        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backCheck3.setVisibility(View.VISIBLE);
                backCheck2.setVisibility(View.INVISIBLE);
                backCheck1.setVisibility(View.INVISIBLE);
                selectBack = 3;

                backgroundShop.setBackground(getDrawable(R.drawable.game_background2));
            }
        });


        dart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dartCheck1.setVisibility(View.VISIBLE);
                dartCheck2.setVisibility(View.INVISIBLE);
                dartCheck3.setVisibility(View.INVISIBLE);
                selectDart = 1;
                dartShop.setBackground(getDrawable(R.drawable.dart1));

            }
        });

        dart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dartCheck2.setVisibility(View.VISIBLE);
                dartCheck1.setVisibility(View.INVISIBLE);
                dartCheck3.setVisibility(View.INVISIBLE);
                selectDart = 2;
                dartShop.setBackground(getDrawable(R.drawable.dart2));
            }
        });

        dart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dartCheck3.setVisibility(View.VISIBLE);
                dartCheck2.setVisibility(View.INVISIBLE);
                dartCheck1.setVisibility(View.INVISIBLE);
                selectDart = 3;
                dartShop.setBackground(getDrawable(R.drawable.dart3));
            }
        });


        balloon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                balloonCheck1.setVisibility(View.VISIBLE);
                balloonCheck2.setVisibility(View.INVISIBLE);
                balloonCheck3.setVisibility(View.INVISIBLE);
                selectBalloon = 1;
                balloonShop.setBackground(getDrawable(R.drawable.balloon1));
            }
        });

        balloon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                balloonCheck2.setVisibility(View.VISIBLE);
                balloonCheck1.setVisibility(View.INVISIBLE);
                balloonCheck3.setVisibility(View.INVISIBLE);
                selectBalloon = 2;
                balloonShop.setBackground(getDrawable(R.drawable.balloon2));
            }
        });

        balloon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                balloonCheck3.setVisibility(View.VISIBLE);
                balloonCheck2.setVisibility(View.INVISIBLE);
                balloonCheck1.setVisibility(View.INVISIBLE);
                selectBalloon = 3;
                balloonShop.setBackground(getDrawable(R.drawable.balloon3));
            }
        });


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //선택사항 저장
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("back", selectBack);
                editor.putInt("dart", selectDart);
                editor.putInt("balloon", selectBalloon);
                editor.apply();

                //저장사실 알림
                Toast.makeText(ShopActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();

                //첫화면으로 이동
                Intent intent = new Intent(ShopActivity.this, InitActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
