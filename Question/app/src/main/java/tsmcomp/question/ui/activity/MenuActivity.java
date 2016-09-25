package tsmcomp.question.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.view.View;

import com.nifty.cloud.mb.core.NCMBObject;

import tsmcomp.question.QuestionActivity;
import tsmcomp.question.R;
import tsmcomp.question.answer.AnswerActivity;
import tsmcomp.question.model.NCMBQuestion;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }

    public void onClickView(View v){
        Intent intent = null;
        NCMBQuestion ncmb = new NCMBQuestion(createNCMBObject("OptionalQuestion"));
        switch(v.getId()){
            case R.id.menu_first_item:
                //  今日のアンケ
                intent = new Intent(this, DetailActivity.class);
                intent.putExtra("obj",ncmb);
                startActivity(intent);
                break;
            case R.id.menu_second_item:
                //  アンケ一覧画面
                intent = new Intent(this, FindingActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_third_item:
                //  アンケを投稿する
                intent = new Intent(this, CreatingActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_fourth_item:


                break;
            default:
                return;
        }


    }

    private static NCMBObject createNCMBObject(String title){
        NCMBObject obj = new NCMBObject("question");
        obj.put("title", title);
        return obj;
    }
}
