package tsmcomp.question.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import tsmcomp.question.R;
import tsmcomp.question.ui.activity.CreatingActivity;

/**
 * Created by test on 2016/09/25.
 */
public class CreatingTitleFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View v = View.inflate(getContext(), R.layout.fragment_creating_title, null);

        //  TODO:リスナーで返す形に変更する
        //  TODO:バックボタンをつける
        //  TODO:入力項目判定をつける
        Button b = (Button) v.findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CreatingActivity)getActivity()).goToTheNextPage();
            }
        });

        return v;
    }

}
