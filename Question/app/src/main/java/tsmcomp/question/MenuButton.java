package tsmcomp.question;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by test on 2016/09/21.
 */
public class MenuButton extends LinearLayout {

    public MenuButton(Context context) {
        super(context);
        init();
    }

    public MenuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MenuButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初期化処理を行う
     * 自信をbutton_menu_flatで置き換える
     */
    private void init(){
        final View v = LayoutInflater.from(getContext()).inflate(R.layout.button_menu_flat, this);

    }



}
