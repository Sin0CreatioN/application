package tsmcomp.question.helper;

import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Single;

/**
 * 非同期タスクを記述する
 */
public class RxHelper {

    /**
     *  1件ランダムにアンケを取ってくる
     */
    public static Observable<List<NCMBObject>> findQuery(NCMBQuery query){
        return Observable.create(f->{
            query.findInBackground((list,e)->{
                if (e != null) f.onError(e);
                else if (list.size() == 0) f.onError(new Exception("empty list"));
                else {
                    f.onNext(list);
                }
            });
        });

    }

    /**
     * 回答できるアンケの数を取得する
     */
    public static Observable<Integer> fetchAvailableQuestionCount(){
        return Observable.create(f-> {
            NCMBQuery query = new NCMBQuery("Questions");
            query.whereGreaterThanOrEqualTo("createDate", new Date());
            query.countInBackground((i, e) -> {
                if (e != null) f.onError(e);
                else if (i == 0) f.onError(new Exception("empty list"));
                else {
                    f.onNext(i);
                }
            });
        });
    }


    /**
     * アンケを書き込む
     */
    public static Single<Integer> pushQuestion(){
        return Single.create(f->{
            //  書き込むタスクを記述する
            //  結果をonNextで返してやる
        });
    }


}
