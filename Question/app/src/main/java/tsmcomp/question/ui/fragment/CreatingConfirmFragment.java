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
 * アンケート作成確認画面
 */
public class CreatingConfirmFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        View v = View.inflate(getContext(), R.layout.fragment_creating_confirm, null);

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
