package tsmcomp.question.answer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.nifty.cloud.mb.core.NCMB;

import java.util.ArrayList;

import tsmcomp.question.R;
import tsmcomp.question.model.NCMBOption;
import tsmcomp.question.model.NCMBQuestion;

/**
 * 自由回答形式の回答フォーム
 * Created by test on 2016/09/19.
 */
public class AnswerFreeFormFragment extends Fragment{

    private OnClickButtonListener listener;


    public AnswerFreeFormFragment(OnClickButtonListener l){
        super();
        this.listener = l;
    }

    public AnswerFreeFormFragment(){
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        final View v = inflater.inflate(R.layout.fragment_answer_free_form, null);
        final Button button = (Button) v.findViewById(R.id.button);
        final EditText editText = (EditText) v.findViewById(R.id.editText);
        TextView textView = (TextView) v.findViewById(R.id.text);

        //  問題を取得する
        final NCMBQuestion question = (NCMBQuestion) getArguments().getSerializable("obj");
        textView.setText(question.getTitle());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickSubmitButton(question, editText.getText().toString());
            }
        });

        return v;
    }


    public interface OnClickButtonListener{
        void onClickSubmitButton(NCMBQuestion question, String str);
    }

}
