package tsmcomp.question.answer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.nifty.cloud.mb.core.NCMBObject;

import java.util.ArrayList;
import java.util.List;

import tsmcomp.question.R;
import tsmcomp.question.model.NCMBAnswer;
import tsmcomp.question.model.NCMBOption;
import tsmcomp.question.model.NCMBQuestion;

/**
 * Created by test on 2016/09/19.
 */
public class AnswerOptionalFormFragment extends Fragment {

    private OnClickButtonListener listener;


    public AnswerOptionalFormFragment(OnClickButtonListener l) {
        super();
        this.listener = l;
    }

    public AnswerOptionalFormFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View v = inflater.inflate(R.layout.fragment_answer_optional_form, null);
        ListView listView = (ListView) v.findViewById(R.id.listView);
        Button button = (Button) v.findViewById(R.id.button);
        TextView textView = (TextView) v.findViewById(R.id.text);

        //  解答を取得してくる
        final NCMBQuestion question = (NCMBQuestion) getArguments().getSerializable("obj");
        final ArrayList<NCMBOption> options = question.getOptions();

        //  viewに配置していく
        listView.setAdapter(new OptionArrayAdapter(getContext(),android.R.layout.simple_list_item_1,options));
        textView.setText(question.getTitle());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickSubmitButton(question, "test");
            }
        });


        return v;
    }


    public interface OnClickButtonListener {
        void onClickSubmitButton(NCMBQuestion question, String str);
    }

    private class OptionArrayAdapter extends ArrayAdapter<NCMBOption>{

        public OptionArrayAdapter(Context context, int resource,  List<NCMBOption> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView)View.inflate(getContext(), android.R.layout.simple_list_item_1, null);
            textView.setText(getItem(position).toString());
            return textView;
            //return super.getView(position, convertView, parent);
        }

    }

}