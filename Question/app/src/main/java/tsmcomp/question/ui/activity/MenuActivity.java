package tsmcomp.question.ui.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBObject;
import com.nifty.cloud.mb.core.NCMBQuery;

import java.util.Date;
import java.util.Random;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.FuncN;
import tsmcomp.question.QuestionActivity;
import tsmcomp.question.R;
import tsmcomp.question.common.util.FallExecutor;
import tsmcomp.question.helper.RxHelper;
import tsmcomp.question.model.NCMBQuestion;

/**
 * TODO：今日のアンケートを取得するタイミングを変更するか否か
 * 今日のアンケートをおしてから取得するとタイムラグがある
 * ○起動と同時に取得しておけばラグがない
 * ×使わない場合に通信料が無駄になる
 * ×APIのリクエスト数が増える
 * 設定で変更できるようにしておくと良いかも
 *
 */
public class MenuActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_CREATING_ACTIVITY = 1;
    private static final int REQUEST_CODE_TODAY_QUESTION = 2;
    public static final int RESULT_CODE_NO_INTERNET_CONNECTION =10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        NCMB.initialize(this.getApplicationContext(), QuestionActivity.KEY1, QuestionActivity.KEY2);

        fetchQuestionRandomly();
    }


    /**
     * 重要
     * RXJava+Java8 を使った美しい非同期処理
     * RXJavaは非同期処理（終わるまで待つ）用ライブラリ
     * Java8はラムダ式（new Interface(){@Override...}を簡単化するためのもの
     */
    private void fetchQuestionRandomly(){
        final Random random = new Random();
        RxHelper.fetchAvailableQuestionCount()
                .map(count->random.nextInt(count))
                .subscribe(randomValue->{
                    //  アンケの件数からランダムに番号を決定
                    //  番号を指定しアンケを取得する
                    NCMBQuery query = new NCMBQuery("Questions");
                    query.setSkip(0);
                    query.setLimit(1);
                    RxHelper.findQuery(query)
                            .map(result->result.get(0))
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .subscribe(f->{
                                //  ここで値を吐き出す
                                Log.d("TAG",f.toString());
                            });
                });
    }




    /**
     * 画面のボタンが押されたときに呼ばれる
     * @param v
     */
    public void onClickView(View v){
        Intent intent = null;
        NCMBQuestion ncmb = new NCMBQuestion(createNCMBObject("OptionalQuestion"));
        switch(v.getId()){
            case R.id.menu_first_item:
                //  今日のアンケ
                intent = new Intent(this, DetailActivity.class);
                intent.putExtra("obj",ncmb);
                startActivity(intent);
                break;
            case R.id.menu_second_item:
                //  アンケ一覧画面
                intent = new Intent(this, FindingActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_third_item:
                //  アンケを投稿する
                intent = new Intent(this, CreatingActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CREATING_ACTIVITY);
                break;
            case R.id.menu_fourth_item:
                //  結果画面
                intent = new Intent(this, ResultListActivity.class);
                startActivity(intent);
                break;
            default:
                return;
        }


    }

    /**
     * ほかの画面へ遷移したとき結果を返してもらう
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case REQUEST_CODE_CREATING_ACTIVITY:
                if( resultCode == RESULT_OK ){
                    //  投稿に成功した場合
                    openSuccessDialog("投稿に成功しました");
                }
                break;
        }
    }


    /**
     * 成功した場合のダイアログを表示する
     */
    private void openSuccessDialog(String title){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setPositiveButton("OK", null)
                .show();
    }

    /**
     * 失敗した場合のダイアログを表示する
     */
    private void openFailedDialog(String title){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setPositiveButton("OK", null)
                .setNegativeButton("Retry", null)
                .show();
    }


    private static NCMBObject createNCMBObject(String title){
        NCMBObject obj = new NCMBObject("question");
        obj.put("title", title);
        return obj;
    }



}
