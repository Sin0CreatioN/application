package tsmcomp.question.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import tsmcomp.question.R;
import tsmcomp.question.common.MaterialCardAvatarWithTextViewHolder;
import tsmcomp.question.common.MaterialCardDenseAvatarWithTextAndIconViewHolder;
import tsmcomp.question.common.MaterialCardRadioWithTextAndIconViewHolder;
import tsmcomp.question.common.MaterialCardSingleRadioWithTextAndIconViewHolder;
import tsmcomp.question.model.NCMBQuestion;
import tsmcomp.question.ui.activity.CreatingActivity;

/**
 * 問題作成時
 * 選択回答形式ならば利用するフラグメント
 */
public class CreatingOptionallyFragment extends Fragment {

    ArrayList<String> options;
    MyAdapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = View.inflate(getContext(), R.layout.fragment_creating_optionally_form, null);

        options = new ArrayList<String>();
        options.add("abc");
        options.add("ddd");
        options.add("ddd");


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        myAdapter = new MyAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myAdapter);



        //  MEXTボタンの処理
        Button b = (Button) view.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CreatingActivity)getActivity()).goToTheNextPage();
            }
        });


        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
    }



    /**
     * ListViewのかわりにRecyclerViewというものを使う
     *
     * @see MaterialCardAvatarWithTextViewHolder
     */
    private class MyAdapter extends RecyclerView.Adapter<MaterialCardSingleRadioWithTextAndIconViewHolder> {

        private ArrayList<Object> mData;

        public MyAdapter() {
            super();
            mData = new ArrayList<>();
            mData.add("");
            mData.add("");
        }

        @Override
        public MaterialCardSingleRadioWithTextAndIconViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MaterialCardSingleRadioWithTextAndIconViewHolder(LayoutInflater.from(getContext())
                    .inflate(R.layout.card_material_single_radio_with_text_and_icon, parent, false));
        }

        @Override
        public void onBindViewHolder(MaterialCardSingleRadioWithTextAndIconViewHolder holder, int position) {

            //  ここで値をセットする
            //  とりあえずランダムでセット
            if(position==mData.size()){
                //  最後にaddボタンを追加
                holder.mPrimaryTextView.setText(Html.fromHtml("<u>選択肢を追加する</u>"));
                holder.mRadioButton.setEnabled(false);
                holder.mRoot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addOption();
                    }
                });
            }else{
                String text = "選択肢";
                holder.mPrimaryTextView.setText(text);
                holder.mIconImageView.setImageResource(android.R.drawable.ic_menu_delete);
            }
            

        }

        @Override
        public int getItemCount() {
            return mData.size()+1;
        }


        /**
         * 選択肢を追加する
         */
        private void addOption(){
            mData.add("any");
            notifyDataSetChanged();
        }

        /**
         * 選択肢を削除する
         */
        private void removeOption(int index){
            mData.remove(index);
            notifyDataSetChanged();

        }


    }






}
