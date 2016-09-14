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
import android.widget.TextView;

import com.nifty.cloud.mb.core.DoneCallback;
import com.nifty.cloud.mb.core.FindCallback;
import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import java.util.List;


public class AnswerActivity extends AppCompatActivity {
    NCMBObject question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        NCMB.initialize(this.getApplicationContext(), QuestionActivity.KEY1, QuestionActivity.KEY2);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.answer_layout, new QuestionListFragment(), "list")
                .commit();
    }


    private class QuestionListFragment extends ListFragment {
        String title[];
        List<NCMBObject> ncmbObjects;
        ArrayAdapter<String> adapter;

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);


            NCMBQuery<NCMBObject> query = new NCMBQuery<>("Questions");
            query.whereEqualTo("answerPossible", true);
            query.addOrderByDescending("createDate");
            query.findInBackground(new FindCallback<NCMBObject>() {
                @Override
                public void done(List<NCMBObject> list, NCMBException e) {
                    if (list.size() == 0) {
                        title = new String[1];
                        title[0] = "質問がありません";
                    } else {
                        ncmbObjects = list;
                        title = new String[list.size()];
                        for (int i = 0; i < title.length; i++) {
                            title[i] = list.get(i).getString("title");
                        }
                    }
                    adapter = new ArrayAdapter<String>(getActivity(), R.layout.list, title);
                    setListAdapter(adapter);
                }
            });

            getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    question = ncmbObjects.get(position);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.answer_layout, new AnswerFormFragment(), "form")
                            .commit();
                }
            });

        }
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