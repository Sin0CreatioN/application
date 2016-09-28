package tsmcomp.question.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nifty.cloud.mb.core.FindCallback;
import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;
import com.rey.material.widget.ProgressView;

import java.util.ArrayList;
import java.util.List;

import tsmcomp.question.QuestionActivity;
import tsmcomp.question.R;
import tsmcomp.question.common.MaterialCardAvatarWithTextViewHolder;
import tsmcomp.question.model.NCMBQuestion;

/**
 * アンケを探す画面
 */
public class FindingActivity extends AppCompatActivity{

    ArrayList<NCMBQuestion> questions;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.activity_finding);

        NCMB.initialize(getApplicationContext(), QuestionActivity.KEY1, QuestionActivity.KEY2);

        //  アンケート一覧を取得
        questions = new ArrayList<>();
        NCMBQuery query = new NCMBQuery("Questions");
        query.addOrderByDescending("createDate");
        query.setLimit(3);

        query.findInBackground(new FindCallback() {
            @Override
            public void done(List list, NCMBException e) {
                for(int i = 0; i < list.size(); i++){
                    questions.add(new NCMBQuestion((NCMBObject) list.get(i)));
                }
                //  結果を貼る
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                final MyAdapter myAdapter = new MyAdapter();
                recyclerView.setLayoutManager(new LinearLayoutManager(FindingActivity.this));
                recyclerView.setAdapter(myAdapter);
                //  Circularを消す
                final ProgressView progressView = (ProgressView) findViewById(R.id.progressView);
                progressView.setVisibility(View.GONE);
            }
        });

/*
        questions.add(new NCMBQuestion("好きな食べ物は？"));
        questions.add(new NCMBQuestion("好きなお菓子は？"));
        questions.add(new NCMBQuestion("好きな芸能人は？"));
        questions.add(new NCMBQuestion("好きな〇×は？"));
*/





    }



    /**
     * ListViewのかわりにRecyclerViewというものを使う
     * @see MaterialCardAvatarWithTextViewHolder
     */
    private class MyAdapter extends RecyclerView.Adapter<MaterialCardAvatarWithTextViewHolder>{

        public MyAdapter(){
            super();
        }

        @Override
        public MaterialCardAvatarWithTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MaterialCardAvatarWithTextViewHolder(LayoutInflater.from(FindingActivity.this)
                    .inflate(R.layout.card_material_avatar_with_text, parent, false));
        }

        @Override
        public void onBindViewHolder(MaterialCardAvatarWithTextViewHolder holder, int position) {
            //  ここで値をセットする
            //  とりあえずランダムでセット
            NCMBQuestion question = questions.get(position);
            holder.mPrimaryTextView.setText(question.getTitle());
            if( position%2==0 ){
                holder.mSecondaryTextView.setText("2016/09/09 12:00まで回答できます");
                holder.mAvatarImageView.setImageResource(R.drawable.account_circle);
            }else{
                holder.mSecondaryTextView.setText("2016/09/09 12:00に回答しました");
                holder.mAvatarImageView.setBackgroundColor(Color.GREEN);
                holder.mAvatarImageView.setImageResource(R.drawable.check_circle);
            }
        }

        @Override
        public int getItemCount() {
            return questions.size();
        }



    }

}
