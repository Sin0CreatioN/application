package tsmcomp.question.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nifty.cloud.mb.core.FindCallback;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import java.util.ArrayList;
import java.util.List;

import tsmcomp.question.R;

/**
 * 質問一覧を表示するためのフラグメント
 * クリックされたらコールバックを行う
 * Created by dell_user on 2016/09/14.
 */
public class QuestionListFragment extends Fragment {

    ArrayList<String> questionTitle;
    List<NCMBObject> ncmbObjects;
    ArrayAdapter<String> adapter;
    ListView listView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //  ListViewを初期設定する
        
        questionTitle = new ArrayList<>();
        questionTitle.add("質問がありません");
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, questionTitle);




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
