package tsmcomp.question;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import tsmcomp.question.answer.AnswerActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button bt_quest = (Button) findViewById(R.id.bt_question);
        bt_quest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, QuestionActivity.class);
                startActivity(intent);
            }
        });

        Button bt_ans = (Button) findViewById(R.id.bt_answer);
        bt_ans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, AnswerActivity.class);
                startActivity(intent);
            }
        });
    }
}
