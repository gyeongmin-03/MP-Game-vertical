package com.example.mobilegamev;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;



public class InitActivity extends AppCompatActivity {

    Button btnStartGame; //게임 시작 버튼
    Button btnOpenShop; //상점 이동 버튼
    TextView initHigh; //최고 점수 textview
    TextView initCoin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_activity);

        //레이아웃 연결
        btnStartGame = findViewById(R.id.btnStartGame);
        btnOpenShop = findViewById(R.id.btnOpenShop);
        initHigh = findViewById(R.id.initHigh);
        initCoin = findViewById(R.id.initCoin);

        //내부DB 생성
        SharedPreferences pref = getSharedPreferences("save", Context.MODE_PRIVATE);
        
        //최고 점수 동기화
        initHigh.setText("최고 점수 : " + pref.getInt("highScore", 0));

        //코인 동기화
        Coin coin = Coin.getInstance();
        coin.setCoin(pref.getInt("coin", 0));
        initCoin.setText("코인 : " + coin.getCoin());


        // 버튼 클릭 이벤트
        //MainActivity(게임)으로 넘어감
        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InitActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //상점으로 이동
        btnOpenShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InitActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });


    }
}
