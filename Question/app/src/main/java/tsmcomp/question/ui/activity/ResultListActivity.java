package tsmcomp.question.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nifty.cloud.mb.core.FindCallback;
import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import java.util.ArrayList;
import java.util.List;

import tsmcomp.question.QuestionActivity;
import tsmcomp.question.R;
import tsmcomp.question.common.util.FallExecutor;
import tsmcomp.question.common.viewholder.MaterialCardAvatarWithTextViewHolder;
import tsmcomp.question.model.NCMBAnswer;
import tsmcomp.question.model.NCMBQuestion;

/**
 * 結果一覧を見るためのアクティビティー
 */
public class ResultListActivity extends AppCompatActivity {

    //  自分で投稿したアンケ一覧
    ArrayList<NCMBQuestion> questions;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_result_list);

        NCMB.initialize(getApplicationContext(), QuestionActivity.KEY1, QuestionActivity.KEY2);

        //  本来はアンケート一覧を取得
        //  現在は作ってます


        final NCMBQuery query = new NCMBQuery("Questions");
        query.whereEqualTo("user_id", "XEvURohmqH02R8nA");
        query.findInBackground(new FindCallback() {
            @Override
            public void done(List list, NCMBException e) {
                if(list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        questions = new ArrayList<>();
                        questions.add(new NCMBQuestion((NCMBObject) list.get(i)));
                    }
                }else {
                    questions = new ArrayList<>();
                    questions.add(new NCMBQuestion("Nothing"));
                }

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                MyAdapter myAdapter = new MyAdapter();
                recyclerView.setLayoutManager(new LinearLayoutManager(ResultListActivity.this));
                recyclerView.setAdapter(myAdapter);
            }
        });

/*
        questions = new ArrayList<>();
        questions.add(new NCMBQuestion("好きな食べ物は？"));
        questions.add(new NCMBQuestion("好きなお菓子は？"));
        questions.add(new NCMBQuestion("好きな芸能人は？"));
        questions.add(new NCMBQuestion("好きな〇×は？"));
*/



    }


    /**
     * ListViewのかわりにRecyclerViewというものを使う
     *
     * @see MaterialCardAvatarWithTextViewHolder
     */
    private class MyAdapter extends RecyclerView.Adapter<MaterialCardAvatarWithTextViewHolder> {

        public MyAdapter() {
            super();
        }

        @Override
        public MaterialCardAvatarWithTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MaterialCardAvatarWithTextViewHolder(LayoutInflater.from(ResultListActivity.this)
                    .inflate(R.layout.card_material_avatar_with_text, parent, false));
        }

        @Override
        public void onBindViewHolder(MaterialCardAvatarWithTextViewHolder holder, int position) {
            //  ここで値をセットする
            //  とりあえずランダムでセット
            NCMBQuestion question = questions.get(position);
            holder.mPrimaryTextView.setText(question.getTitle());
            if (position < 2) {
                holder.mSecondaryTextView.setText("2件の回答がありました");
                holder.mAvatarImageView.setImageResource(R.drawable.play_circle_filled_white_192x192);
            } else {
                holder.mSecondaryTextView.setText("3件の回答がありました");
                //holder.mAvatarImageView.setBackgroundColor();
                holder.mAvatarImageView.setImageResource(R.drawable.pause_circle_fill_white_192x192);
            }
        }

        @Override
        public int getItemCount() {
            return questions.size();
        }


    }
}