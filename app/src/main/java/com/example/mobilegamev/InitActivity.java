package com.example.mobilegamev;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class InitActivity extends AppCompatActivity {

    Button btnStartGame;
    TextView initHigh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_activity);

        // 버튼 찾기
        btnStartGame = findViewById(R.id.btn_start_game);
        initHigh = findViewById(R.id.initHigh);


        SharedPreferences pref = getSharedPreferences("save", Context.MODE_PRIVATE);
        initHigh.setText("최고 점수 : " + pref.getInt("highScore", 0));



        // 버튼 클릭 이벤트 처리
        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // MainActivity로 이동
                Intent intent = new Intent(InitActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
