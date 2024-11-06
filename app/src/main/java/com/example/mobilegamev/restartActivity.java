package com.example.mobilegamev;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class restartActivity extends AppCompatActivity {

    Button btnStartGame;
    TextView currentScore, highScore, tvNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restart_activity);

        // 버튼 찾기
        btnStartGame = findViewById(R.id.btn_start_game);
        currentScore = findViewById(R.id.currentScore);
        highScore = findViewById(R.id.highScore);
        tvNew = findViewById(R.id.tvNew);

        //게임 점수 표시
        Intent intent = getIntent();
        int cScore = intent.getIntExtra("score", 0);
        currentScore.setText("점수 : " + cScore);

        //로컬 저장소 생성
        SharedPreferences pref = getSharedPreferences("save", Context.MODE_PRIVATE);

        //최고 점수 표시
        int hScore = pref.getInt("highScore", 0);
        if(cScore > hScore){
            hScore = cScore;
            tvNew.setVisibility(View.VISIBLE);

            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("highScore", hScore);
            editor.apply();
        }

        highScore.setText("최고 점수 : " + hScore);

        // 버튼 클릭 이벤트 처리
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
