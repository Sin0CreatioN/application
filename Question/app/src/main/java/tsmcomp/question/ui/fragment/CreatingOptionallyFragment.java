package tsmcomp.question.ui.fragment;

import android.content.Context;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tsmcomp.question.R;
import tsmcomp.question.common.MaterialCardAvatarWithTextViewHolder;
import tsmcomp.question.common.MaterialCardDenseAvatarWithTextAndIconViewHolder;
import tsmcomp.question.common.MaterialCardRadioWithTextAndIconViewHolder;
import tsmcomp.question.common.MaterialCardSingleRadioWithEditAndIconViewHolder;
import tsmcomp.question.common.MaterialCardSingleRadioWithTextAndIconViewHolder;
import tsmcomp.question.model.NCMBQuestion;
import tsmcomp.question.ui.activity.CreatingActivity;

/**
 * 問題作成時
 * 選択回答形式ならば利用するフラグメント
 */
public class CreatingOptionallyFragment extends Fragment {

    MyAdapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        final View view = View.inflate(getContext(), R.layout.fragment_creating_optionally_form, null);
        final Button nextButton = (Button) view.findViewById(R.id.button);
        final Button skipButton = (Button) view.findViewById(R.id.button2);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        final CreatingActivity creatingActivity = (CreatingActivity) getActivity();

        myAdapter = new MyAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myAdapter);

        //  NEXTボタンの処理
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  作った選択肢をセットする
                ArrayList<String> options = getOptions(recyclerView);
                creatingActivity.setQuestionOptions(options);
                //  次のページへ飛ばす
                creatingActivity.goToTheNextPage();
            }
        });

        //  SKIPボタンの処理
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  特に何もせず次のページへ飛ばす
                creatingActivity.goToTheNextPage();
            }
        });



        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
    }



    /**
     *  RecyclerViewから選択肢を取得する
     */
    private ArrayList<String> getOptions(RecyclerView recyclerView){
        ArrayList<String> options = new ArrayList<>();
        for(int i=0; i<recyclerView.getLayoutManager().getItemCount(); i++) {
            View v = recyclerView.getLayoutManager().findViewByPosition(i);
            String optionTitle = ((EditText)v.findViewById(R.id.editText)).getText().toString();
            options.add(optionTitle);
        }
        return options;
    }


    /**
     * ListViewのかわりにRecyclerViewというものを使う
     *
     * @see MaterialCardAvatarWithTextViewHolder
     */
    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int VIEW_TYPE_TEXT = 1;
        private static final int VIEW_TYPE_ADD = 2;
        private static final int VIEW_TYPE_OPEN_TEMPLATE = 3;
        private ArrayList<Integer> viewTypes;

        public MyAdapter() {
            super();
            viewTypes = new ArrayList<>();
            viewTypes.add(VIEW_TYPE_TEXT);
            viewTypes.add(VIEW_TYPE_ADD);
            viewTypes.add(VIEW_TYPE_OPEN_TEMPLATE);
        }

        @Override
        public int getItemViewType(int position) {
            return viewTypes.get(position);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View v = null;
            switch(viewType){
                case VIEW_TYPE_TEXT:
                    v = inflater.inflate(R.layout.card_material_single_radio_with_edit_and_icon, parent, false);
                    return new MaterialCardSingleRadioWithEditAndIconViewHolder(v);
                case VIEW_TYPE_ADD:
                    v = inflater.inflate(R.layout.card_material_single_radio_with_text_and_icon, parent, false);
                    return new MaterialCardSingleRadioWithTextAndIconViewHolder(v);
                case VIEW_TYPE_OPEN_TEMPLATE:
                    v = inflater.inflate(R.layout.card_material_single_radio_with_text_and_icon, parent, false);
                    return new MaterialCardSingleRadioWithTextAndIconViewHolder(v);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            switch(holder.getItemViewType()){
                case VIEW_TYPE_TEXT:
                    //Log.d("TEST",holder.getClass().toString());
                    MaterialCardSingleRadioWithEditAndIconViewHolder holder1 =
                            (MaterialCardSingleRadioWithEditAndIconViewHolder) holder;
                    holder1.mIconImageView.setImageResource(android.R.drawable.ic_menu_delete);
                    holder1.mIconImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            removeOption(position);
                        }
                    });
                    break;
                case VIEW_TYPE_ADD:
                    MaterialCardSingleRadioWithTextAndIconViewHolder holder2 =
                            (MaterialCardSingleRadioWithTextAndIconViewHolder) holder;
                    holder2.mPrimaryTextView.setText(Html.fromHtml("<u>選択肢を追加</u>"));
                    holder2.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            addOption();
                        }
                    });
                    holder2.mRadioButton.setVisibility(View.GONE);
                    break;
                case VIEW_TYPE_OPEN_TEMPLATE:
                    MaterialCardSingleRadioWithTextAndIconViewHolder holder3 =
                            (MaterialCardSingleRadioWithTextAndIconViewHolder) holder;
                    holder3.itemView.setVisibility(View.GONE);  //  この機能は封印しておく

                    break;


            }
        }

        @Override
        public int getItemCount() {
            return viewTypes.size();
        }


        /**
         * 選択肢を追加する
         */
        private void addOption(){
            int index = viewTypes.size()-2;
            viewTypes.add(index, VIEW_TYPE_TEXT);
            notifyItemInserted(index);
        }

        /**
         * 選択肢を削除する
         */
        private void removeOption(int index){
            //  削除が終わる前に削除するとバグるような気がする
            viewTypes.remove(index);
            notifyItemRemoved(index);
        }

    }






}
