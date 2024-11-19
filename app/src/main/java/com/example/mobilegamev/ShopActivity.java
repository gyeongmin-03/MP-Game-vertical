package com.example.mobilegamev;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

public class ShopActivity extends TabActivity {

    ImageView back1, back2, back3;
    ImageView dart1, dart2, dart3;
    ImageView balloon1, balloon2, balloon3;
    ImageView backCheck1, backCheck2, backCheck3;
    ImageView dartCheck1, dartCheck2, dartCheck3;
    ImageView balloonCheck1, balloonCheck2, balloonCheck3;
    TextView btnClose;

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
        }
        else if(selectBack == 2){
            backCheck2.setVisibility(View.VISIBLE);
            backCheck1.setVisibility(View.INVISIBLE);
            backCheck3.setVisibility(View.INVISIBLE);
        }else if(selectBack == 3){
            backCheck3.setVisibility(View.VISIBLE);
            backCheck2.setVisibility(View.INVISIBLE);
            backCheck1.setVisibility(View.INVISIBLE);
        }


        if(selectDart == 1){
            dartCheck1.setVisibility(View.VISIBLE);
            dartCheck2.setVisibility(View.INVISIBLE);
            dartCheck3.setVisibility(View.INVISIBLE);
        }
        else if(selectDart == 2){
            dartCheck2.setVisibility(View.VISIBLE);
            dartCheck1.setVisibility(View.INVISIBLE);
            dartCheck3.setVisibility(View.INVISIBLE);
        }else if(selectDart == 3){
            dartCheck3.setVisibility(View.VISIBLE);
            dartCheck2.setVisibility(View.INVISIBLE);
            dartCheck1.setVisibility(View.INVISIBLE);
        }



        if(selectBalloon == 1){
            balloonCheck1.setVisibility(View.VISIBLE);
            balloonCheck2.setVisibility(View.INVISIBLE);
            balloonCheck3.setVisibility(View.INVISIBLE);
        }
        else if(selectBalloon == 2){
            balloonCheck2.setVisibility(View.VISIBLE);
            balloonCheck1.setVisibility(View.INVISIBLE);
            balloonCheck3.setVisibility(View.INVISIBLE);
        }else if(selectBalloon == 3){
            balloonCheck3.setVisibility(View.VISIBLE);
            balloonCheck2.setVisibility(View.INVISIBLE);
            balloonCheck1.setVisibility(View.INVISIBLE);
        }



        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBack = 1;
            }
        });

        back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBack = 2;
            }
        });

        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBack = 3;
            }
        });


        dart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDart = 1;
            }
        });

        dart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDart = 2;
            }
        });

        dart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDart = 3;
            }
        });


        balloon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBalloon = 1;
            }
        });

        balloon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBalloon = 2;
            }
        });

        balloon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBalloon = 3;
            }
        });


        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("back", selectBack);
                editor.putInt("dart", selectDart);
                editor.putInt("balloon", selectBalloon);
                editor.apply();

                //첫화면으로 이동
                Intent intent = new Intent(ShopActivity.this, InitActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
