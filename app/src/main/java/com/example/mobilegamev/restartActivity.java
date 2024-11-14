package com.example.mobilegamev;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.*;

public class restartActivity extends AppCompatActivity {

    Button btnStartGame, btnRank; //게임 다시 시작 버튼
    TextView currentScore, highScore, tvNew; //직전 게임 점수, 최고 점수, 최고점수갱신(new) 텍스트뷰
    EditText nickname;
    View dialogView;
    private FirebaseFirestore db;

    List<dbData> dataList = new ArrayList<dbData>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restart_activity);

        //레이아웃 연결
        btnStartGame = findViewById(R.id.btn_start_game);
        btnRank = findViewById(R.id.btn_rank);
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


        //파이어베이스 db
        db = FirebaseFirestore.getInstance();
        db.collection("top100").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                dbData data = new dbData();
                                data.name = document.getId();
                                data.score = Integer.parseInt(document.getString("score"));
                                dataList.add(data);
                            }


                            sort(dataList);
                            for (int i = 0; i < dataList.size(); i++){
                                if(cScore > dataList.get(i).score){

                                    dialogView = (View) View.inflate(restartActivity.this, R.layout.dialog, null);
                                    AlertDialog.Builder dlg = new AlertDialog.Builder(restartActivity.this);
                                    dlg.setTitle("랭킹에 등록하시겠습니까?");
                                    dlg.setView(dialogView);
                                    dlg.setPositiveButton("입력",
                                            new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    nickname = dialogView.findViewById(R.id.nickname);
                                                    String nname;

                                                    if(nickname.getText().toString().isEmpty()){
                                                        nname = "익명" + (int)(Math.random()*100000);
                                                    }else {
                                                        nname = nickname.getText().toString();
                                                    }

                                                    dbData myData = new dbData();
                                                    myData.name = nname;  //닉네임 입력
                                                    myData.score = cScore;
                                                    dataList.add(dataList.size(), myData);


                                                    HashMap<String, String> hashMap = new HashMap<String, String>();
                                                    hashMap.put("score", myData.score+"");
                                                    db.collection("top100").document(myData.name).set(hashMap);

                                                    if(dataList.size() > 100){
                                                        String name = dataList.get(0).name;
                                                        dataList.remove(0);
                                                        db.collection("top100").document(name).delete();
                                                    }
                                                }
                                            });
                                    dlg.setNegativeButton("취소", null);
                                    dlg.show();

                                    break;
                                }
                            }

                        }
                    }
                });




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


        btnRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db.collection("top100").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        dbData data = new dbData();
                                        data.name = document.getId();
                                        data.score = Integer.parseInt(document.getString("score"));
                                        dataList.add(data);
                                    }

                                }
                            }
                        });

            }
        });

    }

    public void sort(List<dbData> fdata) {
        for (int i = fdata.size() - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                // 오름차순 정렬 조건: 앞의 수가 뒤의 수보다 크면 swap
                if (fdata.get(j).score > fdata.get(j + 1).score) {
                    dbData temp = fdata.get(j);
                    fdata.set(j, fdata.get(j + 1));
                    fdata.set(j + 1, temp);
                }
            }
        }
    }
}


class dbData {
    String name;
    int score;
}