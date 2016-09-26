package tsmcomp.question.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import tsmcomp.question.R;

/**
 * Googleガイドラインに沿ったMaterialなカードでアバターと2テキストのやつです
 * @link card_material_avatar_with_text
 */
public class MaterialCardSingleRadioWithEditAndIconViewHolder extends RecyclerView.ViewHolder{

    public EditText mPrimaryEditTextView;
    public ImageView mIconImageView;
    public RadioButton mRadioButton;
    public View mRoot;

    public MaterialCardSingleRadioWithEditAndIconViewHolder(View itemView) {
        super(itemView);
        mIconImageView = (ImageView) itemView.findViewById(R.id.icon);
        mPrimaryEditTextView = (EditText) itemView.findViewById(R.id.primaryText);
        mRadioButton = (RadioButton) itemView.findViewById(R.id.radioButton);
        mRoot = itemView;
    }


}
