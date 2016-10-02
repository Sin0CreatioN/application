package tsmcomp.question.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nifty.cloud.mb.core.FindCallback;
import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import java.util.ArrayList;
import java.util.List;

import tsmcomp.question.QuestionActivity;
import tsmcomp.question.R;
import tsmcomp.question.model.NCMBAnswer;

/**
 * アンケート結果の詳細を表示する画面
 * 通知画面から遷移する可能性があるためActivityに昇格
 */
public class ResultDetailActivity extends AppCompatActivity{

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_result_detail);

        NCMB.initialize(getApplicationContext(), QuestionActivity.KEY1, QuestionActivity.KEY2);

        //  TODO:データベースからデータを引っ張るのと
        //  TODO:リアルタイムで解答があった場合更新する
        //  該当の回答を取得
        //  回答リストを作成
        ArrayList<NCMBAnswer> answers = new ArrayList<>();


        NCMBQuery query = new NCMBQuery("Answer");
        //QuestionIDは仮のIDです
        query.whereEqualTo("question_id", "5lyaMAAJjmYP6AdX");
        query.findInBackground(new FindCallback() {
            @Override
            public void done(List list, NCMBException e) {
                ArrayList<NCMBAnswer> answers = new ArrayList<>();
                for(int i = 0; i < list.size(); i++){
                    answers.add(new NCMBAnswer((NCMBObject) list.get(i)));
                }

                ListView listview = (ListView) findViewById(R.id.listView);
                MyAdapter adapter = new MyAdapter(ResultDetailActivity.this, android.R.layout.simple_list_item_1, answers);
                listview.setAdapter(adapter);
            }
        });
    }

    private class MyAdapter extends ArrayAdapter<NCMBAnswer> {

        public MyAdapter(Context context, int resource, List<NCMBAnswer> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(getContext(), R.layout.row_result_with_avatar, null);
            TextView primaryTextView = (TextView) convertView.findViewById(R.id.primaryText);
            TextView secondaryTextView = (TextView) convertView.findViewById(R.id.secondaryText);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.avatar);
            imageView.setImageResource(R.drawable.person);
            imageView.setBackgroundColor(Color.GREEN);
            primaryTextView.setText(getItem(position).getUserName());
            secondaryTextView.setText(getItem(position).getAnswerText());
            return convertView;
        }


    }



}
