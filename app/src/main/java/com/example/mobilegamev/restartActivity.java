package com.example.mobilegamev;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class restartActivity extends AppCompatActivity {

    Button btnStartGame; //게임 다시 시작 버튼
    TextView currentScore, highScore, tvNew; //직전 게임 점수, 최고 점수, 최고점수갱신(new) 텍스트뷰

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restart_activity);

        //레이아웃 연결
        btnStartGame = findViewById(R.id.btn_start_game);
        currentScore = findViewById(R.id.currentScore);
        highScore = findViewById(R.id.highScore);
        tvNew = findViewById(R.id.tvNew);

        //intent에서 직전 게임 점수 추출
        Intent intent = getIntent();
        int cScore = intent.getIntExtra("score", 0);
        currentScore.setText("점수 : " + cScore);

        //내부DB 생성
        SharedPreferences pref = getSharedPreferences("save", Context.MODE_PRIVATE);
        
        int hScore = pref.getInt("highScore", 0); //DB의 최고점수
        if(cScore > hScore){ //직전 게임 결과가 DB의 기록된 최고점수보다 높으면 갱신
            hScore = cScore;
            tvNew.setVisibility(View.VISIBLE);

            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("highScore", hScore);
            editor.apply();
        }
        highScore.setText("최고 점수 : " + hScore);

        //버튼 클릭 이벤트
        //MainActivity(게임)으로 넘어감
        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(restartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
