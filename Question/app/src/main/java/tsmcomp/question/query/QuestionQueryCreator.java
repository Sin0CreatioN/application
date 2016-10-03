package tsmcomp.question.query;

import com.nifty.cloud.mb.core.NCMBQuery;

import java.util.Random;

/**
 * Questionに関するクエリはこれで作成すること
 */
public class QuestionQueryCreator {

    public static NCMBQuery empty(){
        return new NCMBQuery("Questions");
    }

    public static NCMBQuery findByObjectId(String id){
        NCMBQuery query = empty();
        query.whereEqualTo("objectId", id);
        return query;
    }

    public static NCMBQuery findAll(){
        return empty();
    }

    /**
     * 回答可能なクエスチョンだけを取得する
     * TODO:実装
     * @return
     */
    public static NCMBQuery findByAvailable(){
        //  とりあえず全部
        return empty();
    }

    /**
     * 1個ランダムに取ってくるクエリ
     * @param maxCount
     *  回答可能なアンケから[0,maxCount]の範囲でランダムに1件取ってきます
     *  通常はデータベースの最大値を選択してください
     */
    public static NCMBQuery findAvailableByRandomPick(int maxCount){
        NCMBQuery query = findByAvailable();
        Random random = new Random();
        int rand = random.nextInt(maxCount);
        query.setSkip(rand);
        query.setLimit(1);
        return query;
    }

}
