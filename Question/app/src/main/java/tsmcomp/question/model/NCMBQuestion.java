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
        return false;
    }

    /**
     * 選択回答を取得する
     * @return
     */
    @Nullable
    public ArrayList<NCMBOption> getOptions(){
       return null;
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
