package tsmcomp.question.model;

import android.support.annotation.Nullable;

import com.nifty.cloud.mb.core.NCMBObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by test on 2016/09/19.
 */
public class NCMBQuestion implements Serializable{

    NCMBObject ref;

    public NCMBQuestion(NCMBObject obj){
        this.ref = obj;
    }


    /**
     * 選択回答であるか確認する
     * @return
     */
    public boolean hasOption(){
        //  一時的な処理なので変更してくれ
        if(getTitle().contains("Normal")) return false;
        return true;
    }

    /**
     * 選択回答を取得する
     * @return
     */
    @Nullable
    public ArrayList<NCMBOption> getOptions(){
       //   一時的な処理なので変更してくれ
        ArrayList<NCMBOption> options = new ArrayList<>();
        options.add(new NCMBOption("Option1"));
        options.add(new NCMBOption("Option2"));
        options.add(new NCMBOption("Option3"));
        options.add(new NCMBOption("Option4"));
        return options;
    }

    /**
     * 問題のタイトルを返す
     */
    public String getTitle(){
        return ref.getString("title");
    }

    /**
     * QuestionTitleを返すようにしておく
     * @return
     */
    @Override
    public String toString(){
        return "";
    }
}
