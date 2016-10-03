package tsmcomp.question.model;


import com.nifty.cloud.mb.core.NCMBObject;

/**
 * Created by test on 2016/09/19.
 */
public class NCMBOption {
    String text;

    public NCMBOption(String text){
        this.text = text;
    }

    public NCMBOption(NCMBObject obj){
        this.text = obj.getString("text");
    }

    @Override
    public String toString(){
        return text;
    }
}
