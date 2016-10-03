package tsmcomp.question.helper;

import com.nifty.cloud.mb.core.NCMBQuery;

/**
 * Created by test on 2016/10/02.
 */
public class QueryHelper {

    public static NCMBQuery createQueryQuestion(){ return new NCMBQuery("Questions");}

    public static NCMBQuery findByObjectId(String objectId){
        NCMBQuery query = createQueryQuestion();
        query.whereEqualTo("objectId", objectId);
        return query;
    }


}
