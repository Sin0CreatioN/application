package tsmcomp.question.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import tsmcomp.question.R;

/**
 * Googleガイドラインに沿ったMaterialなカードでアバターと2テキストのやつです
 * @link card_material_avatar_with_text
 */
public class MaterialCardRadioWithTextAndIconViewHolder extends RecyclerView.ViewHolder{

    public TextView mPrimaryTextView;
    public TextView mSecondaryTextView;
    public ImageView mIconImageView;
    public RadioButton mRadioButton;

    public MaterialCardRadioWithTextAndIconViewHolder(View itemView) {
        super(itemView);
        mIconImageView = (ImageView) itemView.findViewById(R.id.icon);
        mPrimaryTextView = (TextView) itemView.findViewById(R.id.primaryText);
        mSecondaryTextView = (TextView) itemView.findViewById(R.id.secondaryText);
        mRadioButton = (RadioButton) itemView.findViewById(R.id.radioButton);
    }


}
