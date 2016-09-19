package tsmcomp.question.answer;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBObject;


import tsmcomp.question.QuestionActivity;
import tsmcomp.question.R;
import tsmcomp.question.SerializedNCMBObject;
import tsmcomp.question.model.NCMBQuestion;
import tsmcomp.question.ui.AnswerFormFragment;

/**
 * 回答用アクティビティー
 * このアクティビティーは回答すべてを扱います
 *
 * @see AnswerFreeFormFragment 自由回答形式のフラグメント
 * @see AnswerOptionalFormFragment 選択回答形式のフラグメント
 * @see QuestionListFragment 質問一覧のフラグメント
 */
public class AnswerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        NCMB.initialize(this.getApplicationContext(), QuestionActivity.KEY1, QuestionActivity.KEY2);

        //  初期の質問リストにしておく
        final QuestionListFragment fragment = new QuestionListFragment(new QuestionListFragment.OnClickTitleListener() {
            @Override
            public void onClickTitle(NCMBObject obj) {
                //  タイトルを押したときの処理
                onClickTitleInQuestionListFragment(obj);
            }
        });
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, "list")
                .commit();

    }


    /**
     * QuestionListFragment内のタイトルが押されると呼ばれる
     * 事前条件：質問リストがひらいているとき
     * トリガー：質問タイトルが押されたとき
     * なにをする：自由/選択解答用フラグメントに切り替える
     * @param obj
     * クリックされた質問タイトルに紐づいているNCMBObjectのインスタンス
     */
    private void onClickTitleInQuestionListFragment(NCMBObject obj){
        Fragment fragment;
        NCMBQuestion ncmb;

        /*
          ここではじめてNCMBObjectをNCMBQuestionに変換する
         */
        ncmb = new NCMBQuestion(obj);

       /*
         押された質問が自由形式なのか、選択形式なのかで
         切り替えるフラグメントを指定する
        */
        if( !ncmb.hasOption() ){
            fragment = new AnswerFreeFormFragment(new AnswerFreeFormFragment.OnClickButtonListener() {
                @Override
                public void onClickSubmitButton(NCMBQuestion question, String answer) {
                    onClickSubmitButtonInFreeFormFragment(question, answer);
                }
            });
        }else{
            fragment = new AnswerOptionalFormFragment(new AnswerOptionalFormFragment.OnClickButtonListener() {
                @Override
                public void onClickSubmitButton(NCMBQuestion question, String answer) {
                    onClickSubmitButtonInOptionalFormFragment(question, answer);
                }
            });
        }

        //  bundle経由で渡す場合は
        //  Serializableインターフェースを実装したSerializedNCMBObjectを使うか
        //  Stringで渡して各フラグメントでDBに接続して取ってくるかのどちらか
        Bundle bundle = new Bundle();
        //bundle.putString("title",obj.getString("title"));
        bundle.putSerializable("obj", ncmb);
        fragment.setArguments(bundle);
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

        final QuestionListFragment fragment = new QuestionListFragment(new QuestionListFragment.OnClickTitleListener() {
            @Override
            public void onClickTitle(NCMBObject obj) {
                //  タイトルを押したときの処理
                onClickTitleInQuestionListFragment(obj);
            }
        });

        //  TOPに戻して
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, "form")
                .commit();

        //  ログを出す
        Toast.makeText(this, "解答が送信されました", Toast.LENGTH_SHORT).show();

        //  解答送信処理

    }


    /**
     * AnswerOptionalformFragment内の回答ボタンが押されると呼ばれる
     * 選択回答が取得可能
     */
    private void onClickSubmitButtonInOptionalFormFragment(NCMBQuestion question, String answer){
        hideKeyboard();

        final QuestionListFragment fragment = new QuestionListFragment(new QuestionListFragment.OnClickTitleListener() {
            @Override
            public void onClickTitle(NCMBObject obj) {
                //  タイトルを押したときの処理
                onClickTitleInQuestionListFragment(obj);
            }
        });

        //  TOPに戻して
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment, "form")
                .commit();

        //  ログを出す
        Toast.makeText(this, "解答が送信されました", Toast.LENGTH_SHORT).show();

        //  解答送信処理
    }




    /**
     * 各フラグメント内の所定の方法をトリガーとし、
     * 回答を送信する
     */
    private void post(String title){

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