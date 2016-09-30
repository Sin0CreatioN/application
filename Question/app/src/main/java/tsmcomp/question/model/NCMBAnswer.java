package tsmcomp.question.model;

import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBObject;

/**
 * 回答
 */
public class NCMBAnswer {
    String text;
    String name;

    public NCMBAnswer(NCMBObject obj){
        text = obj.getString("text");
        name = obj.getString("user_name");
    }

    public NCMBAnswer(String text, String name){
        this.text = text;
        this.name = name;
    }

    public String getUserName(){
        return this.name;
    }

    public String getAnswerText(){
        return this.text;
    }

}
