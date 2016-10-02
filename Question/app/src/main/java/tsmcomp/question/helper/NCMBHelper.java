package tsmcomp.question.helper;

import com.nifty.cloud.mb.core.NCMBObject;

/**
 * Created by test on 2016/09/16.
 */
public class NCMBHelper {

    /**
     * 強制的に自由回答形式の問題を作成するメソッド
     */
    public static void createQuestion(String title){
        NCMBObject obj = new NCMBObject("question");
        obj.put("title", title);
    }


    /**
     * 強制的に選択回答形式の問題を作成するメソッド
     */
    public static void createQuestion(String title, String[] options){




    }


}
