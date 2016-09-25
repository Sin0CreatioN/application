package tsmcomp.question.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.nifty.cloud.mb.core.NCMBObject;

import tsmcomp.question.R;
import tsmcomp.question.answer.QuestionListFragment;
import tsmcomp.question.model.NCMBQuestion;
import tsmcomp.question.ui.fragment.DetailFreelyFragment;
import tsmcomp.question.ui.fragment.DetailOptionallyFragment;

/**
 * アンケート詳細画面＆回答可能
 * 今日のアンケまたはアンケを探すから遷移
 */
public class DetailActivity extends AppCompatActivity{

    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_detail);


        Fragment fragment = null;

        /*
          ここではじめてNCMBObjectをNCMBQuestionに変換す
         */
        NCMBQuestion ncmb = (NCMBQuestion) getIntent().getExtras().getSerializable("obj");


        /*
         押された質問が自由形式なのか、選択形式なのかで
         切り替えるフラグメントを指定する
        */

        if( !ncmb.hasOption() ){
            fragment = new DetailFreelyFragment(new DetailFreelyFragment.OnClickButtonListener() {
                @Override
                public void onClickSubmitButton(NCMBQuestion question, String answer) {
                    onClickSubmitButtonInFreeFormFragment(question, answer);
                }
            });
        }else{
            fragment = new DetailOptionallyFragment(new DetailOptionallyFragment.OnClickButtonListener() {
                @Override
                public void onClickSubmitButton(NCMBQuestion question, String answer) {
                    onClickSubmitButtonInOptionalFormFragment(question, answer);
                }
            });
        }

        //  bundle経由で渡す場合は
        //  Serializableインターフェースを実装したSerializedNCMBObjectを使うか
        //  Stringで渡して各フラグメントでDBに接続して取ってくるかのどちらか
        fragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, "form")
                .commit();


    }


    /**
     * AnswerFreeFormFragment内の回答ボタンが押されると呼ばれる
     * 事前条件：自由回答フラグメントがひらいているとき
     * トリガー：送信ぼたんが押されたとき
     * なにをする：解答を送信し、質問リストに戻る
     * 自由回答形式の回答が取得可能
     */
    private void onClickSubmitButtonInFreeFormFragment(NCMBQuestion question,String answer){
        // ソフトキーボードを非表示にする
        hideKeyboard();

        //  ログを出す
        Toast.makeText(this, "解答が送信されました", Toast.LENGTH_SHORT).show();

        //  解答送信処理

        finish();
    }


    /**
     * AnswerOptionalformFragment内の回答ボタンが押されると呼ばれる
     * 選択回答が取得可能
     */
    private void onClickSubmitButtonInOptionalFormFragment(NCMBQuestion question, String answer){
        hideKeyboard();

        Toast.makeText(this, "解答が送信されました", Toast.LENGTH_SHORT).show();

        //  解答送信処理

        finish();
    }



    /**
     * キーボードを消す
     */
    private void hideKeyboard(){
        View v = findViewById(R.id.container);
        v.requestFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }


}
