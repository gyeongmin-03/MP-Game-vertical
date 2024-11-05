package com.example.mobilegamev;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class restartActivity extends AppCompatActivity {

    Button btnStartGame;
    TextView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restart_activity);

        Intent intent = getIntent();

        // 버튼 찾기
        btnStartGame = findViewById(R.id.btn_start_game);
        score = findViewById(R.id.currentScore);

        // 버튼 클릭 이벤트 처리
        btnStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // MainActivity로 이동
                Intent intent = new Intent(restartActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        score.setText(Integer.toString(intent.getIntExtra("score", 0)));
    }
}
