package tsmcomp.question;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setContentView(R.layout.fragment);

            TextView mainText = (TextView)findViewById(R.id.mainText);
            mainText.setText(question.getString("mainText"));
        }
    }
}