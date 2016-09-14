package tsmcomp.question.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
public class QuestionListFragment extends ListFragment {

    private static String TAG = "QuestionListFragment";
    OnClickTitleListener listener;

    public QuestionListFragment(OnClickTitleListener l){
        this.listener = l;
    }

    /**
     * Fragmentが作成されたときに呼ばれます
     * データベースに接続し、問題を取得する
     */
    @Override
    public void onStart(){
        super.onStart();

        //  ダウンロードを開始する
        NCMBQuery<NCMBObject> query = new NCMBQuery<>("Questions");
        query.whereEqualTo("answerPossible", true);
        query.findInBackground(new FindCallback<NCMBObject>() {
            @Override
            public void done(List<NCMBObject> list, NCMBException e) {
                onFind(list, e);
            }
        });
        //  クリックしたときのリスナーをセットしておく
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                //  AdapterにNCMBAdapterをセットしたので、この方法でNCMBObjectを持ってくることができる
                onClickTitle(adapterView.getAdapter().getItem(pos));
                //System.out.println( adapterView.getAdapter().getItem(pos).getClass() );
            }
        });

    }


    /**
     * 質問タイトルをクリックした場合に呼ばれる
     * @param object クリックされたオブジェクト
     */
    private void onClickTitle(Object object){
        //  NCMBオブジェクトがクリックされたときのみコールバックする
        if( object instanceof  NCMBObject ){
            listener.onClickTitle((NCMBObject)object);
        }
        //  質問がない場合はStringオブジェクトがクリックされる
        //  コールバックはしない
        //NCMBObject question = ncmbObjects.get(position);
        //listener.onClickTitle(question);
    }


    /**
     * DBからの更新処理が終わった時に呼ばれる
     * リストビューを同期する
     */
    private void onFind(List<NCMBObject> list, NCMBException e){
        //  エラーチェック
        if(e != null){
            e.printStackTrace();
            Toast.makeText(getContext(), "予期せぬエラー", Toast.LENGTH_SHORT);
            return;
        }
        if( list.size() == 0 ){
            Toast.makeText(getContext(), "質問の取得に失敗しました", Toast.LENGTH_SHORT);
            //  質問なしのアダプターをセットする
            setListAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, new String[]{"現在投稿されている質問がありません"}));
            return;
        }
        //  質問が見つかりました
        Log.d(TAG,"Finding was done.");

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, NCMBObjectList2StringList(list));
        //setListAdapter(adapter);
        setListAdapter(new NCMBObjectArrayAdapter(getContext(), android.R.layout.simple_list_item_1, list));
    }


    /**
     * フラグメント内の質問タイトルがクリックされた場合は
     * このフラグメント内で処理するのではなく、Activityで処理させる
     * そのためリスナーを作成して伝播させる
     */
    public interface OnClickTitleListener{
        void onClickTitle(NCMBObject obj);
    }


    /**
     * NCMBObjectList -> StringList
     * このメソッドはこのクラス内だけ使用可
     * @param objects
     * @return
     */
    private static ArrayList<String> NCMBObjectList2StringList(List<NCMBObject> objects){
        ArrayList<String> str = new ArrayList<>();
        for(NCMBObject obj : objects){
            str.add(obj.getString("title"));
        }
        return str;
    }


    /**
     * このメソッドはこのクラス内だけ使用可
     * アダプターを拡張して、NCMBObjectをそのまま表示できるようにした
     */
    private class NCMBObjectArrayAdapter extends ArrayAdapter<NCMBObject>{
        public NCMBObjectArrayAdapter(Context context, int resource, List<NCMBObject> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView)View.inflate(getContext(), android.R.layout.simple_list_item_1, null);
            textView.setText(getItem(position).getString("title"));
            return textView;
            //return super.getView(position, convertView, parent);
        }


    }



}
