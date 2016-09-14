package tsmcomp.question;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import android.widget.TextView;


import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBObject;


import tsmcomp.question.ui.AnswerFormFragment;
import tsmcomp.question.ui.QuestionListFragment;


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
                .replace(R.id.answer_layout, fragment, "list")
                .commit();
    }


    /**
     * QuestionListFragment内のタイトルが押されると呼ばれる
     */
    private void onClickTitleInQuestionListFragment(NCMBObject obj){
        final AnswerFormFragment fragment = new AnswerFormFragment();
        //  bundle経由で渡す場合は
        //  Serializableインターフェースを実装したSerializedNCMBObjectを使うか
        //  Stringで渡してフラグメントでDBに接続して取ってくるかのどちらか
        Bundle bundle = new Bundle();
        bundle.putString("title",obj.getString("title"));
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.answer_layout, fragment, "form")
                .commit();
    }



}