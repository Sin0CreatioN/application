package tsmcomp.question.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tsmcomp.question.R;
import tsmcomp.question.model.NCMBOption;
import tsmcomp.question.model.NCMBQuestion;

/**
 * Created by test on 2016/09/19.
 */
public class DetailOptionallyFragment extends Fragment {

    private OnClickButtonListener listener;
    private RadioGroup radioGroup;
    private String selectedText;

    public DetailOptionallyFragment(OnClickButtonListener l) {
        super();
        this.listener = l;
    }

    public DetailOptionallyFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View v = inflater.inflate(R.layout.fragment_detail_optionally_form, null);
        ListView listView = (ListView) v.findViewById(R.id.listView);
        Button button = (Button) v.findViewById(R.id.button);
        TextView textView = (TextView) v.findViewById(R.id.text);

        //  解答を取得してくる
        final NCMBQuestion question = (NCMBQuestion) getArguments().getSerializable("obj");
        final ArrayList<NCMBOption> options = question.getOptions();

        //  viewに配置していく
        radioGroup = new RadioGroup(getActivity());
        listView.setAdapter(new OptionArrayAdapter(getContext(),android.R.layout.simple_list_item_1,options));
        textView.setText(question.getTitle());

        //  現在選択されているテキストをActivityに返す
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedText == null) {
                    Toast.makeText(getActivity(), "解答を選択してください", Toast.LENGTH_SHORT).show();
                } else {
                    listener.onClickSubmitButton(question, selectedText);
                }
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
            //  RadioButtonをCheckBoxに変更
            View v = View.inflate(getContext(), R.layout.row_answer_optional_form, null);
            final CheckBox checkBox  = (CheckBox) v.findViewById(R.id.checkBox);
            TextView textView = (TextView) v.findViewById(R.id.textView);

            textView.setText(getItem(position).toString());
            //radioGroup.addView(radio);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedText = checkBox.getText().toString();
                }
            });

            return v;


            /*
            RadioButton radioButton = new RadioButton(getActivity());
            radioButton.setText(getItem(position).toString());
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getActivity(), "Clickされた", Toast.LENGTH_SHORT).show();
                    selectedText = ((RadioButton)v).getText().toString();
                }
            });
            radioGroup.addView(radioButton);

            //TextView textView = (TextView)View.inflate(getContext(), android.R.layout.simple_list_item_1, null);
            //textView.setText();
            //return textView;
            //return super.getView(position, convertView, parent);
            return radioButton;
            */
        }

    }

}