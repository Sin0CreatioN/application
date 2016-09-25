package tsmcomp.question.model;

import android.support.annotation.Nullable;

import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * QuestionはNCMBObjectをラップしたもの
 * 目的：
 * getString("title")ではtitleがマジックな文字列となり、
 * 予め共有してないとわからない。
 * getTitle()としておけばIDEの補完で取得ができるため便利
 *
 */
public class NCMBQuestion implements Serializable{

    int id;
    String title;
    ArrayList<NCMBOption> options;


    /**
     * Serializableにするためにはプリミティブ型(String,intなど)にしておく必要があるので
     * ここで値をコピーします
     * @param obj
     */
    public NCMBQuestion(NCMBObject obj){
        id = obj.getInt("id");
        title = obj.getString("title");

        options = new ArrayList<NCMBOption>();


    }

    /**
     * これは非推奨
     * @deprecated
     */
    public NCMBQuestion(String title){
        this.title = title;
    }




    /**
     * 選択回答であるか確認する
     * @return
     */
    public boolean hasOption(){
        //  一時的な処理なので変更してくれ
        if(title.contains("Normal")) return false;
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

    public String getTitle(){
        return title;
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
