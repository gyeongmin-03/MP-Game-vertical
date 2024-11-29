package kr.pknu.LeeGyeongmin202211734;

import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;

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

    FrameLayout backLock2, backLock3, dartLock2, dartLock3, balloonLock2, balloonLock3;
    View buy_dialog;
    Coin objCoin;
    SharedPreferences.Editor editor;
    SharedPreferences pref;
    TextView shopCoin;

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

        objCoin = Coin.getInstance();

        shopCoin = findViewById(R.id.shopCoin);
        shopCoin.setText("현재 코인 : "+ objCoin.getCoin());

        btnClose = findViewById(R.id.btnClose);

        backgroundShop = findViewById(R.id.backgroundShop);
        dartShop = findViewById(R.id.dartShop);
        balloonShop = findViewById(R.id.balloonShop);

        backLock2 = findViewById(R.id.backLock2);
        backLock3 = findViewById(R.id.backLock3);
        dartLock2 = findViewById(R.id.dartLock2);
        dartLock3 = findViewById(R.id.dartLock3);
        balloonLock2 = findViewById(R.id.balloonLock2);
        balloonLock3 = findViewById(R.id.balloonLock3);


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


        pref = getSharedPreferences("save", Context.MODE_PRIVATE);
        editor = pref.edit();
        selectBack = pref.getInt("back", 1);
        selectDart = pref.getInt("dart", 1);
        selectBalloon = pref.getInt("balloon", 1);
        if(!pref.getBoolean("backLock2", true)){
            backLock2.setVisibility(View.INVISIBLE);
        }
        if(!pref.getBoolean("backLock3", true)){
            backLock3.setVisibility(View.INVISIBLE);
        }
        if(!pref.getBoolean("dartLock2", true)){
            dartLock2.setVisibility(View.INVISIBLE);
        }
        if(!pref.getBoolean("dartLock3", true)){
            dartLock3.setVisibility(View.INVISIBLE);
        }
        if(!pref.getBoolean("balloonLock2", true)){
            balloonLock2.setVisibility(View.INVISIBLE);
        }
        if(!pref.getBoolean("balloonLock3", true)){
            balloonLock3.setVisibility(View.INVISIBLE);
        }


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

            backgroundShop.setBackground(getDrawable(R.drawable.game_background2));

        }else if(selectBack == 3){
            backCheck3.setVisibility(View.VISIBLE);
            backCheck2.setVisibility(View.INVISIBLE);
            backCheck1.setVisibility(View.INVISIBLE);
            backgroundShop.setBackground(getDrawable(R.drawable.game_background3));

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

                backgroundShop.setBackground(getDrawable(R.drawable.game_background2));
            }
        });

        back3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backCheck3.setVisibility(View.VISIBLE);
                backCheck2.setVisibility(View.INVISIBLE);
                backCheck1.setVisibility(View.INVISIBLE);
                selectBack = 3;

                backgroundShop.setBackground(getDrawable(R.drawable.game_background3));
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

        backLock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy_dialog = View.inflate(ShopActivity.this, R.layout.buy_dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(ShopActivity.this);
                dlg.setTitle("구매하시겠습니까?");
                dlg.setView(buy_dialog);
                dlg.setPositiveButton("구매",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int c = objCoin.getCoin();
                                if(c < 50){
                                    Toast.makeText(ShopActivity.this, "코인이 부족합니다.", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    objCoin.addCoin(-50);
                                    c = objCoin.getCoin();
                                    backLock2.setVisibility(View.INVISIBLE);
                                    shopCoin.setText("현재 코인 : "+ c);

                                    editor.putInt("coin", c);
                                    editor.putBoolean("backLock2", false);
                                    editor.apply();
                                }
                            }
                        });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        backLock3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy_dialog = View.inflate(ShopActivity.this, R.layout.buy_dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(ShopActivity.this);
                dlg.setTitle("구매하시겠습니까?");
                dlg.setView(buy_dialog);
                dlg.setPositiveButton("구매",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int c = objCoin.getCoin();
                                if(c < 100){
                                    Toast.makeText(ShopActivity.this, "코인이 부족합니다.", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    objCoin.addCoin(-100);
                                    c = objCoin.getCoin();
                                    backLock3.setVisibility(View.INVISIBLE);
                                    shopCoin.setText("현재 코인 : "+ c);

                                    editor.putInt("coin", c);
                                    editor.putBoolean("backLock3", false);
                                    editor.apply();
                                }
                            }
                        });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        dartLock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy_dialog = View.inflate(ShopActivity.this, R.layout.buy_dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(ShopActivity.this);
                dlg.setTitle("구매하시겠습니까?");
                dlg.setView(buy_dialog);
                dlg.setPositiveButton("구매",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int c = objCoin.getCoin();
                                if(c < 50){
                                    Toast.makeText(ShopActivity.this, "코인이 부족합니다.", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    objCoin.addCoin(-50);
                                    c = objCoin.getCoin();
                                    dartLock2.setVisibility(View.INVISIBLE);
                                    shopCoin.setText("현재 코인 : " + c);

                                    editor.putInt("coin", c);
                                    editor.putBoolean("dartLock2", false);
                                    editor.apply();
                                }
                            }
                        });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        dartLock3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy_dialog = View.inflate(ShopActivity.this, R.layout.buy_dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(ShopActivity.this);
                dlg.setTitle("구매하시겠습니까?");
                dlg.setView(buy_dialog);
                dlg.setPositiveButton("구매",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int c = objCoin.getCoin();
                                if(c < 100){
                                    Toast.makeText(ShopActivity.this, "코인이 부족합니다.", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    objCoin.addCoin(-100);
                                    c = objCoin.getCoin();
                                    dartLock3.setVisibility(View.INVISIBLE);
                                    shopCoin.setText("현재 코인 : " + c);

                                    editor.putInt("coin", c);
                                    editor.putBoolean("dartLock3", false);
                                    editor.apply();
                                }
                            }
                        });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        balloonLock2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy_dialog = View.inflate(ShopActivity.this, R.layout.buy_dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(ShopActivity.this);
                dlg.setTitle("구매하시겠습니까?");
                dlg.setView(buy_dialog);
                dlg.setPositiveButton("구매",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int c = objCoin.getCoin();
                                if(c < 50){
                                    Toast.makeText(ShopActivity.this, "코인이 부족합니다.", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    objCoin.addCoin(-50);
                                    c = objCoin.getCoin();
                                    balloonLock2.setVisibility(View.INVISIBLE);
                                    shopCoin.setText("현재 코인 : " + c);

                                    editor.putInt("coin", c);
                                    editor.putBoolean("balloonLock2", false);
                                    editor.apply();
                                }
                            }
                        });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        balloonLock3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buy_dialog = View.inflate(ShopActivity.this, R.layout.buy_dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(ShopActivity.this);
                dlg.setTitle("구매하시겠습니까?");
                dlg.setView(buy_dialog);
                dlg.setPositiveButton("구매",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int c = objCoin.getCoin();
                                if(c < 100){
                                    Toast.makeText(ShopActivity.this, "코인이 부족합니다.", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    objCoin.addCoin(-100);
                                    c = objCoin.getCoin();
                                    balloonLock3.setVisibility(View.INVISIBLE);
                                    shopCoin.setText("현재 코인 : " + c);

                                    editor.putInt("coin", c);
                                    editor.putBoolean("balloonLock3", false);
                                    editor.apply();
                                }
                            }
                        });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

    }
}
