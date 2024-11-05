package com.example.mobilegamev;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InitActivity extends AppCompatActivity {

    private Button btnStartGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init_activity);

        // 버튼 찾기
        btnStartGame = findViewById(R.id.btn_start_game);

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
