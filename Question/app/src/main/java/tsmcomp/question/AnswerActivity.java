package tsmcomp.question;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;



import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBObject;


import tsmcomp.question.ui.AnswerFormFragment;
import tsmcomp.question.ui.QuestionListFragment;

/**
 * @see QuestionListFragment
 * 初期＆メインフラグメント
 * 概要：質問一覧を表示しておくフラグメント
 * 処理：
 * タイトルがクリックされたらonClickTitleInQuestionListFragmentが呼ばれる
 *
 */
public class AnswerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        NCMB.initialize(this.getApplicationContext(), QuestionActivity.KEY1, QuestionActivity.KEY2);

        final QuestionListFragment fragment = new QuestionListFragment(new QuestionListFragment.OnClickTitleListener() {
            @Override
            public void onClickTitle(NCMBObject obj) {
                //  タイトルを押したときの処理
                onClickTitleInQuestionListFragment(obj);
            }
        });
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.answer_fragment, fragment, "list")
                .commit();
    }


    /**
     * QuestionListFragment内のタイトルが押されると呼ばれる
     * @param obj
     * クリックされた質問タイトルに紐づいているNCMBObjectのインスタンス
     */
    private void onClickTitleInQuestionListFragment(NCMBObject obj){
        final AnswerFormFragment fragment = new AnswerFormFragment();
        //  bundle経由で渡す場合は
        //  Serializableインターフェースを実装したSerializedNCMBObjectを使うか
        //  Stringで渡して各フラグメントでDBに接続して取ってくるかのどちらか
        Bundle bundle = new Bundle();
        //bundle.putString("title",obj.getString("title"));
        bundle.putSerializable("obj", new SerializedNCMBObject(obj,"question"));
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.answer_fragment, fragment, "form")
                .commit();
    }



}