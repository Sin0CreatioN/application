package tsmcomp.question.model;

import android.support.annotation.Nullable;

import com.nifty.cloud.mb.core.NCMBObject;

import java.io.Serializable;
import java.lang.reflect.Array;
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

    String title;
    String objectId;

    /**
     * Serializableにするためにはプリミティブ型(String,intなど)にしておく必要があるので
     * ここで値をコピーします
     * @param obj
     */
    public NCMBQuestion(NCMBObject obj){
        title = obj.getString("title");
        objectId = obj.getString("objectId");


    }

    /**
     * これは非推奨
     * @deprecated
     */
    public NCMBQuestion(String title){
        this.title = title;
    }


    public NCMBQuestion(){

    }



    public void setTitle(String title){ this.title = title; }
    public String getTitle(){
        return title;
    }
    public String getObjectId(){ return this.objectId; }

    /**
     * QuestionTitleを返すようにしておく
     * @return
     */
    @Override
    public String toString(){
        return "";
    }




}
