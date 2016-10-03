package tsmcomp.question.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import tsmcomp.question.R;
import tsmcomp.question.helper.RxHelper;
import tsmcomp.question.model.NCMBQuestion;
import tsmcomp.question.query.OptionQueryCreator;
import tsmcomp.question.ui.fragment.DetailFreelyFragment;
import tsmcomp.question.ui.fragment.DetailOptionallyFragment;

/**
 * アンケート詳細画面＆回答画面
 * 今日のアンケまたはアンケを探すから遷移
 * TODO:開くのに重いのでダイアログを表示しておいてほしい
 */
public class DetailActivity extends AppCompatActivity{


    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_detail);

        //  ここではじめてNCMBObjectをNCMBQuestionに変換する
        NCMBQuestion ncmbQuestion = (NCMBQuestion) getIntent().getExtras().getSerializable("obj");

        //  バックグラウンドで実行する
        RxHelper.countQuery(OptionQueryCreator.findByQuestionId(ncmbQuestion.getObjectId()))
                .map(count->(count==0)?
                        new DetailFreelyFragment((q,ans)->{onClickSubmitButtonInFreeFormFragment(q, ans);}):
                        new DetailOptionallyFragment((q,ans)->{onClickSubmitButtonInOptionalFormFragment(q, ans);}))
                .subscribe(fragment->{
                        //  bundle経由で渡す場合は
                        //  Serializableインターフェースを実装したSerializedNCMBObjectを使うか
                        //  Stringで渡して各フラグメントでDBに接続して取ってくるかのどちらか
                        fragment.setArguments(getIntent().getExtras());
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, fragment, "form")
                                .commit();
                },e->{
                    e.printStackTrace();
                });

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
        //Toast.makeText(this, "解答が送信されました", Toast.LENGTH_SHORT).show();

        //  解答送信処理
        setResult(RESULT_OK);
        finish();
    }


    /**
     * AnswerOptionalformFragment内の回答ボタンが押されると呼ばれる
     * 選択回答が取得可能
     */
    private void onClickSubmitButtonInOptionalFormFragment(NCMBQuestion question, String answer){
        hideKeyboard();

        //Toast.makeText(this, "解答が送信されました", Toast.LENGTH_SHORT).show();

        //  解答送信処理
        setResult(RESULT_OK);
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
