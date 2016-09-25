package tsmcomp.question.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
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
import tsmcomp.question.ui.activity.CreatingActivity;

/**
 * 問題作成時
 * 選択回答形式ならば利用するフラグメント
 */
public class CreatingOptionallyFragment extends Fragment {

    MyAdapter myAdapter;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View view = View.inflate(getContext(), R.layout.fragment_creating_optionally_form, null);
        listView = (ListView) view.findViewById(R.id.listView);

        ArrayList<String> string = new ArrayList<>();
        string.add("efd");
        string.add("bcd");

        myAdapter = new MyAdapter(getContext(), android.R.layout.simple_list_item_1, string);
        listView.setAdapter(myAdapter);

        final TextView addButton = (TextView) view.findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddButton();
            }
        });

        //  MEXTボタンの処理
        Button b = (Button) view.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CreatingActivity)getActivity()).goToTheNextPage();
            }
        });

        //  TODO:NEXTに変更


        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
    }


    /**
     * 選択肢を追加ボタンが押されたときに実行される
     * 選択肢を追加する
     */
    private void onClickAddButton(){
        myAdapter.add("ssss");
        myAdapter.notifyDataSetChanged();
        listView.invalidateViews();
        Log.d("TEST","add button");
    }



    private class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(Context context, int resource,  List objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(getContext(), R.layout.row_content_creating_optionally_form, null);
            TextView tv = (TextView) convertView.findViewById(R.id.textView);
            tv.setText(getItem(position));
            return convertView;
        }


    }






}
