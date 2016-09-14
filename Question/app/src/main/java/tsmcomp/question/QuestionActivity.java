package tsmcomp.question;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nifty.cloud.mb.core.DoneCallback;
import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;

public class QuestionActivity extends AppCompatActivity {
    int ansType;
    LinearLayout layout;
    OptionalAnswerFragment fragment;

    public final static int ANSWER_TYPE_MULTILINE = 1;
    public final static int ANSWER_TYPE_OPTIONAL = 2;

    public final String EMPTY_MAINTEXT = "質問文を入力してください";
    public final String EMPTY_NUMBER = "回答数を入力してください";
    public final String EMPTY_EXPIRATION = "回答期限を入力してください";
    public final String EMPTY_TITLETEXT = "タイトルを入力してください";
    public final String UNSELECTED_ANSWERTYPE = "回答形式を選択してください";
    public final String EMPTY_OPTION = "入力されていない選択肢があります";

    public final static String KEY1 = "bf2a6b7503b09f27b9c57da08827113fa7a910259ed2f9b9f913f6b339a4e167";
    public final static String KEY2 = "3727a22c0104bd2b8c99cbd659a996cdd2ea6b5a354205bf7db3c06612cf3eb8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        NCMB.initialize(this.getApplicationContext(), KEY1, KEY2);

        ansType = -1;

        RadioGroup radio = (RadioGroup) findViewById(R.id.radioGroup);
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                onCheckedChangedRadio(checkedId);
            }
        });

        layout = (LinearLayout) findViewById(R.id.layout);

        Button btPost = (Button) findViewById(R.id.post);
        btPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPostButton();
            }
        });
    }

    private void onCheckedChangedRadio(int radioId) {
        if (fragment == null) {
            fragment = new OptionalAnswerFragment(this);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment, fragment, "" + radioId)
                    .commit();
        }

        switch (radioId) {
            case R.id.boxtype:
                ansType = ANSWER_TYPE_OPTIONAL;
                getSupportFragmentManager().beginTransaction()
                        .show(fragment)
                        .commit();
                break;
            case R.id.texttype:
                ansType = ANSWER_TYPE_MULTILINE;
                getSupportFragmentManager().beginTransaction()
                        .hide(fragment)
                        .commit();
                break;
        }
    }


    private void onClickPostButton() {

        try {
            String title = getTitleText();
            String mainText = getMainText();
            checkAnswerType();
            String[] options = fragment.getOptions() ;
            if(ansType == ANSWER_TYPE_OPTIONAL) options = fragment.getOptions();
            int maxNumberOfAnswer = getNumberOfAnswer();
            int expirationOfAnswer = getExpirationOfAnswer();

            final NCMBObject question = new NCMBObject("Questions");
            question.put("title", title);
            question.put("mainText", mainText);
            question.put("expirationOfAnswer", expirationOfAnswer);
            question.put("maxNumberOfAnswer", maxNumberOfAnswer);
            question.put("answerPossible", true);
            question.put("answertype", ansType);
            if (ansType == ANSWER_TYPE_OPTIONAL) {
                question.put("optionsNum", options.length );
                for(int i = 0; i < options.length; i++){
                    question.put("option" + i, options[i]);
                }
            }

            AlertDialog.Builder dialog = new AlertDialog.Builder(QuestionActivity.this);
            dialog.setTitle("確認");
            dialog.setMessage("質問を投稿しますか？");
            dialog.setPositiveButton("投稿", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    question.saveInBackground(new DoneCallback() {
                        @Override
                        public void done(NCMBException e) {

                        }
                    });
                    Intent intent = new Intent(QuestionActivity.this, MenuActivity.class);
                    startActivity(intent);
                }
            });
            dialog.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            dialog.create().show();

        } catch (Exception e) {
            TextView alert = (TextView) findViewById(R.id.alertText);
            alert.setText(e.getMessage());
        }
    }

    private EditText getEditText(int id) {
        return (EditText) findViewById(id);
    }

    private String getEditTextString(int id) {
        return getEditText(id).getText().toString();
    }

    private int getNumberOfAnswer() throws Exception {
        String str = getEditTextString(R.id.ans_num);
        if (str.isEmpty()) throw new Exception(EMPTY_NUMBER);
        return Integer.parseInt(str);
    }

    private String getMainText() throws Exception {
        String str = getEditTextString(R.id.question);
        if (str.isEmpty()) throw new Exception(EMPTY_MAINTEXT);
        return str;
    }

    private String getTitleText() throws Exception {
        String str = getEditTextString(R.id.title);
        if (str.isEmpty()) throw new Exception(EMPTY_TITLETEXT);
        return str;
    }

    private int getExpirationOfAnswer() throws Exception {
        String str = getEditTextString(R.id.ans_lim);
        if (str.isEmpty()) throw new Exception(EMPTY_EXPIRATION);
        return Integer.parseInt(str);
    }

    private void checkAnswerType() throws Exception {
        if (ansType != ANSWER_TYPE_MULTILINE && ansType != ANSWER_TYPE_OPTIONAL)
            throw new Exception(UNSELECTED_ANSWERTYPE);
    }


    private class OptionalAnswerFragment extends Fragment {
        Context context;

        public OptionalAnswerFragment(Context in_context) {
            context = in_context;
        }


        public String[] getOptions() throws Exception{
            LinearLayout layout = (LinearLayout) this.getView();
            String[] str = new String[layout.getChildCount() - 1];
            for (int i = 0; i < str.length; i++) {
                str[i] = layout.getChildAt(i + 1).toString();
                if(str[i].isEmpty()) throw new Exception(EMPTY_OPTION);
            }

            return str;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_optional_answer, container, false);

            final LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.frame);
            rootView.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout.addView(new InputOption(context));
                }
            });
            layout.addView(new InputOption(context));

            return rootView;
        }


        private class InputOption extends LinearLayout implements View.OnClickListener {
            Button bt_remove;
            EditText text;

            int MP = ViewGroup.LayoutParams.MATCH_PARENT;
            int WC = ViewGroup.LayoutParams.WRAP_CONTENT;

            public InputOption(Context context) {
                super(context);
                this.setOrientation(HORIZONTAL);

                bt_remove = new Button(context);
                text = new EditText(context);

                bt_remove.setText("-");
                bt_remove.setTextSize(24);
                bt_remove.setLayoutParams(new ActionBar.LayoutParams(128, WC));
                text.setLayoutParams(new ActionBar.LayoutParams(MP, WC));

                bt_remove.setOnClickListener(this);

                this.addView(bt_remove);
                this.addView(text);
            }

            @Override
            public void onClick(View v) {
                System.out.println(getRootView().toString());
                ViewGroup vg = (ViewGroup) getParent();
                vg.removeView(this);
            }

            @Override
            public String toString() {
                return text.getText().toString();
            }
        }
    }

}