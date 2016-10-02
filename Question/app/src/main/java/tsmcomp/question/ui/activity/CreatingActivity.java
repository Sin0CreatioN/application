package tsmcomp.question.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import tsmcomp.question.R;
import tsmcomp.question.model.NCMBQuestion;
import tsmcomp.question.ui.fragment.CreatingConfirmFragment;
import tsmcomp.question.ui.fragment.CreatingOptionallyFragment;
import tsmcomp.question.ui.fragment.CreatingTitleFragment;

/**
 * アンケを書く画面
 *
 * 1.タイトル決定
 * 2.回答形式設定
 * 3.期間・対象などを設定
 * 4.投稿確認画面（ダイアログを出してTOPへ戻る）
 */
public class CreatingActivity extends AppCompatActivity{

    private static int MAX_PAGE = 3;

    ViewPager mViewPager;
    int mCurrentPageNumber;
    NCMBQuestion mQuestion;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_creating);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));

        mCurrentPageNumber = 0;
        updateActionBarTitle();

        mQuestion = new NCMBQuestion();
    }

    /**
     * 次のページへ遷移する処理
     * 次のページがない場合はTOPへ戻る
     */
    public void goToTheNextPage(){
        if( MAX_PAGE-1 <= mCurrentPageNumber ){
            //  現在のページが最大数に到達したら
            //  TODO:アンケートを投稿する処理を追加
            //  投稿してから結果を返す
            setResult(RESULT_OK);
            finish();
            return;
        }
        mCurrentPageNumber++;
        mViewPager.setCurrentItem(mCurrentPageNumber);
        updateActionBarTitle();
    }


    /**
     * アンケを投稿する処理
     */
    private void post(){

    }

    /**
     * アンケのタイトルをセットする
     */
    public void setQuestionTitle(String title){
        mQuestion.setTitle(title);
    }

    /**
     * 選択肢をセットする
     */
    public void setQuestionOptions(ArrayList<String> options){

    }

    /**
     * タイトル更新
     */
    private void updateActionBarTitle(){
        setTitle(String.format("%s(%d/%d)",getResources()
                .getString(R.string.create_header), mCurrentPageNumber+1, MAX_PAGE));
    }


    /**
     * アンケの整合性チェック
     */
    private boolean isQuestionComplete(){
        return false;
    }


    private class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new CreatingTitleFragment();
                case 1:
                    return new CreatingOptionallyFragment();
                case 2:
                    return new CreatingConfirmFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return MAX_PAGE;
        }


    }


}
