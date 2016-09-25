package tsmcomp.question.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import tsmcomp.question.R;

/**
 * アンケを探す画面
 */
public class FindingActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


    }



    private class MyAdapter extends RecyclerView.Adapter{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

}
