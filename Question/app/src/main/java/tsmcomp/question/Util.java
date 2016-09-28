package tsmcomp.question;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 簡易メソッド
 */
public class Util {

    /**
     * キーボードを隠す処理
     * @param context
     * @param v
     */
    public static void hideKeyboard(Context context, View v){
        v.requestFocus();
        InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * インターネット接続が有効かどうかを返す処理
     */
    public static boolean isConnectedInternet(){
        return false;
    }
}
