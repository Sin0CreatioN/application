package tsmcomp.question.helper;

import com.nifty.cloud.mb.core.DoneCallback;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.Single;
import tsmcomp.question.model.NCMBQuestion;

/**
 * 非同期タスクを記述する
 */
public class RxHelper {

    /**
     *  findを行う
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
     * countを行う
     */
    public static Observable<Integer> countQuery(NCMBQuery query){
        return Observable.create(f-> {
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
    public static Single<NCMBObject> saveObject(NCMBObject object){
        return Single.create(f->{
            //  書き込むタスクを記述する
            object.saveInBackground(e->{
                if(e!=null) f.onError(e);
                else{
                    f.onSuccess(object);
                }
            });
            //  結果をonNextで返してやる
        });
    }


}
