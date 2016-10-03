package tsmcomp.question.query;

import com.nifty.cloud.mb.core.NCMBQuery;

/**
 * 選択肢のクエリを作成
 */
public class OptionQueryCreator {

    private static NCMBQuery empty(){ return new NCMBQuery("Option"); }
    public static NCMBQuery findAll(){ return empty(); }
    public static NCMBQuery findByQuestionId(String questionId){
        NCMBQuery query = empty();
        query.whereEqualTo("question_id", questionId);
        return query;
    }

}
