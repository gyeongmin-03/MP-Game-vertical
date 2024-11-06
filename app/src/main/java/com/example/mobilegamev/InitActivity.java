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
    TextView initHigh; //최고 점수 textview


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_activity);

        //레이아웃 연결
        btnStartGame = findViewById(R.id.btn_start_game);
        initHigh = findViewById(R.id.initHigh);

        //내부DB 생성
        SharedPreferences pref = getSharedPreferences("save", Context.MODE_PRIVATE);
        
        //최고 점수 동기화
        initHigh.setText("최고 점수 : " + pref.getInt("highScore", 0));



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
    }
}
