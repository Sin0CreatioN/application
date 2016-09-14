package tsmcomp.question;

import com.nifty.cloud.mb.core.NCMBObject;

import java.io.Serializable;

/**
 * NCMBObjectを拡張し、Fragment間で受け渡しを可能にしたもの
 * Created by dell_user on 2016/09/15.
 */
public class SerializedNCMBObject extends NCMBObject implements Serializable{

    private NCMBObject ref;

    public SerializedNCMBObject(String className) {
        super(className);
    }

    public SerializedNCMBObject(NCMBObject obj, String className){
        super(className);
        this.ref = obj;
    }

    public NCMBObject getNCMBObject(){
        return ref;
    }

}
