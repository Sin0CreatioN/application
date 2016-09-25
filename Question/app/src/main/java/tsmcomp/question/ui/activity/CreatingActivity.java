package tsmcomp.question.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import tsmcomp.question.R;
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

    ViewPager mViewPager;
    int mCurrentPageNumber;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_creating);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));

        mCurrentPageNumber = 0;
        setTitle("アンケを書く("+(mCurrentPageNumber+1)+"/3)");

        /*
        setTitle("アンケを書く(1/3)");
        Fragment fragment = new CreatingOptionallyFragment();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
                */

    }

    public void goToTheNextPage(){
        mCurrentPageNumber++;
        mViewPager.setCurrentItem(mCurrentPageNumber);
        setTitle("アンケを書く("+(mCurrentPageNumber+1)+"/3)");
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
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }


    }


}
