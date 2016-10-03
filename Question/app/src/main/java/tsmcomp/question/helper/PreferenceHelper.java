package tsmcomp.question.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Preferenceにアクセスするためのクラス
 */

public class PreferenceHelper {

    Context mContext;
    SharedPreferences mPref;

    public PreferenceHelper(Context context){
        mContext = context;
        mPref = getSharedPref();
    }

    /**
     * 最終ログイン日を取得
     * @return
     */
    public String getLastedLoginDate(){ return mPref.getString("",null); }

    /**
     * 今日のアンケを最後に回答した日を取得
     * @return
     */
    public Date loadLastedDateOfTodaysQuestionUserAnswered(){
        try{
            String str = mPref.getString("last_date_of_todays_question_user_answerd",null);
            return reConvertSimpleDate(str);
        }catch (Exception e){
            //  e.printStackTrace();
        }
        return new Date(0); //1970年で初期化
    }

    /**
     * 今日のアンケを最後に回答した日を書き込む
     */
    public void saveLastDateOfTodaysQuestionUserAnswered(Date date){
        mPref.edit().putString("last_date_of_todays_question_user_answerd", convertSimpleDate(date)).commit();
    }

    /**
     * 今日のアンケを示すID
     * @return
     */
    public String loadTodaysQuestionId(){
        return mPref.getString("todays_question_id", null);
    }

    public void saveTodaysQuestionId(String str){
        mPref.edit().putString("todays_question_id", str).commit();
    }


    private SharedPreferences getSharedPref(){
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    private String convertSimpleDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(date);
    }

    private Date reConvertSimpleDate(String dateString) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.parse(dateString);
    }
}
