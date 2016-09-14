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


import tsmcomp.question.ui.QuestionListFragment;


public class AnswerActivity extends AppCompatActivity {
    NCMBObject question;

    private final QuestionListFragment.OnClickTitleListener onClickTitleListener =
            new QuestionListFragment.OnClickTitleListener() {
                @Override
                public void onClickTitle(NCMBObject obj) {
                //	TODO:修正必要
		//	質問一覧でタイトルがクリックされると
		//	対応するNCMBObjectが帰ってくるので、フラグメントを置き換えるなりの処理が必要    
		question = ncmbObjects.get(position);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.answer_layout, new AnswerFormFragment(), "form")
                            .commit();
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        NCMB.initialize(this.getApplicationContext(), QuestionActivity.KEY1, QuestionActivity.KEY2);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.answer_layout, new QuestionListFragment(onClickTitleListener), "list")
                .commit();
    }



    private class AnswerFormFragment extends Fragment{
        RadioGroup radio;

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setContentView(R.layout.fragment_answer_form);

            TextView mainText = (TextView)findViewById(R.id.mainText);
            mainText.setText(question.getString("mainText"));

            if(question.getInt("answertype") == QuestionActivity.ANSWER_TYPE_OPTIONAL){
                LinearLayout layout = (LinearLayout) findViewById(R.id.answerForm);
                layout.removeViewAt(1);

                radio = (RadioGroup) findViewById(R.id.answerOption);
                for (int i = 0; i < question.getInt("optionsNum"); i++){
                    RadioButton button = new RadioButton(getActivity());
                    button.setText(question.getString("option" + i));
                    button.setId(i);
                    radio.addView(button);
                }
            }

            findViewById(R.id.postAnswer).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (question.getInt("answertype")){
                        case QuestionActivity.ANSWER_TYPE_MULTILINE:
                            EditText answer = (EditText) findViewById(R.id.answer);
                            question.put("answerText", answer.getText().toString());
                            break;
                        case QuestionActivity.ANSWER_TYPE_OPTIONAL:

                    }

                    AlertDialog.Builder dialog = new AlertDialog.Builder(AnswerActivity.this);
                    dialog.setTitle("確認");
                    dialog.setMessage("回答しますか？");
                    dialog.setPositiveButton("回答", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            question.saveInBackground(new DoneCallback() {
                                @Override
                                public void done(NCMBException e) {

                                }
                            });
                            Intent intent = new Intent(AnswerActivity.this, MenuActivity.class);
                            startActivity(intent);
                        }
                    });
                    dialog.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.create().show();
                }
            });
        }
    }
}