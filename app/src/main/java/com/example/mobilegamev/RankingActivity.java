package com.example.mobilegamev;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RankingActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private List<dbData> dataList = new ArrayList<dbData>();
    LinearLayout rankingLayout;
    TextView tvBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking);

        rankingLayout = findViewById(R.id.ranking_layout);
        tvBack = findViewById(R.id.tvBack);

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
                                LinearLayout subLayout = new LinearLayout(RankingActivity.this);
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                subLayout.setLayoutParams(params);


                                LinearLayout.LayoutParams numParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2.5f);
                                LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 2.0f);
                                TextView tvNum = new TextView(RankingActivity.this);
                                TextView tvName = new TextView(RankingActivity.this);
                                TextView tvScore = new TextView(RankingActivity.this);

                                tvNum.setText((i+1)+"위");
                                tvName.setText(dataList.get(i).name);
                                tvScore.setText(dataList.get(i).score+"점");

                                tvNum.setLayoutParams(numParams);
                                tvName.setLayoutParams(tvParams);
                                tvScore.setLayoutParams(tvParams);

                                if(i%2 == 0){
                                    tvNum.setBackgroundColor(Color.parseColor("#ff01DFD7"));
                                    tvName.setBackgroundColor(Color.parseColor("#ff01DFD7"));
                                    tvScore.setBackgroundColor(Color.parseColor("#ff01DFD7"));
                                }
                                else {
                                    tvNum.setBackgroundColor(Color.CYAN);
                                    tvName.setBackgroundColor(Color.CYAN);
                                    tvScore.setBackgroundColor(Color.CYAN);
                                }

                                tvNum.setTextSize(20);
                                tvName.setTextSize(20);
                                tvScore.setTextSize(20);

                                tvNum.setPadding(20, 0, 0, 0);
                                tvName.setPadding(20, 0, 0, 0);
                                tvScore.setPadding(0, 0, 20, 0);

                                tvScore.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

                                tvName.setEllipsize(TextUtils.TruncateAt.END);
                                tvName.setMaxLines(1);

                                subLayout.addView(tvNum);
                                subLayout.addView(tvName);
                                subLayout.addView(tvScore);

                                rankingLayout.addView(subLayout);
                            }

                        }
                    }
                });

        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }


    public void sort(List<dbData> fdata) {
        for(int i = fdata.size() - 1; i > 0; i--) {
            for(int j = 0; j < i; j++) {
                if(fdata.get(j).score < fdata.get(j+1).score) {
                    dbData temp = fdata.get(j);
                    fdata.set(j, fdata.get(j + 1));
                    fdata.set(j + 1, temp);
                }
            }
        }
    }
}
