package tsmcomp.question.ui.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
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
    private static final int REQUEST_CODE_CREATING_ACTIVITY = 1;
    private static final int REQUEST_CODE_TODAY_QUESTION = 2;


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
                startActivityForResult(intent, REQUEST_CODE_CREATING_ACTIVITY);
                break;
            case R.id.menu_fourth_item:
                //  結果画面
                intent = new Intent(this, ResultActivity.class);
                startActivity(intent);
                break;
            default:
                return;
        }


    }

    /**
     * ほかの画面へ遷移したとき結果を返してもらう
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUEST_CODE_CREATING_ACTIVITY:
                if( resultCode == RESULT_OK ){
                    //  投稿に成功した場合
                    openSuccessDialog("投稿に成功しました");
                }
                break;


        }
    }


    /**
     * 成功した場合のダイアログを表示する
     */
    private void openSuccessDialog(String title){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setPositiveButton("OK", null)
                .show();
    }





    private static NCMBObject createNCMBObject(String title){
        NCMBObject obj = new NCMBObject("question");
        obj.put("title", title);
        return obj;
    }



}
