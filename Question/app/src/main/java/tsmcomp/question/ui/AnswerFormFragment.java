package tsmcomp.question.ui;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nifty.cloud.mb.core.NCMBObject;

import tsmcomp.question.MenuActivity;
import tsmcomp.question.QuestionActivity;
import tsmcomp.question.R;
import tsmcomp.question.SerializedNCMBObject;

/**
 * Created by dell_user on 2016/09/15.
 */
public class AnswerFormFragment extends Fragment {
    RadioGroup radio;

    public final String EMPTY_MAINTEXT = "質問文を入力してください";
    public final String EMPTY_NUMBER = "回答数を入力してください";
    public final String EMPTY_EXPIRATION = "回答期限を入力してください";
    public final String EMPTY_TITLETEXT = "タイトルを入力してください";
    public final String UNSELECTED_ANSWERTYPE = "回答形式を選択してください";
    public final String EMPTY_OPTION = "入力されていない選択肢があります";

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle){
        View v = inflater.inflate(R.layout.fragment_answer,null);

        //  questionを取り出す処理
        SerializedNCMBObject object = (SerializedNCMBObject) getArguments().getSerializable("obj");
        NCMBObject question = object.getNCMBObject();

        //  questionに紐づいた解答候補を取り出す
        


        return v;
    }



    /**
     * TODO:onActivityCreatedだとアプリを落とした時に呼ばれないんじゃない？
     * @param savedInstanceState
     */
    /*
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        setContentView(R.layout.fragment_answer_form);

        //  NCMBObjectを取り出す処理

        NCMBObject question = null;

        //  取り出したNCMBObjectに対して処理を行う
        switch(question.getInt("answertype")){
            case QuestionActivity.ANSWER_TYPE_OPTIONAL:
                break;
            default:
                break;
        }




        TextView mainText = (TextView)findViewById(R.id.mainText);
        mainText.setText(question.getString("mainText"));

        if( == ){
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
    }*/

}
