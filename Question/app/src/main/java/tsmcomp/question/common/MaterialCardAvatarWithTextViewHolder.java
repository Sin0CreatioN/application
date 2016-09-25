package tsmcomp.question.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import tsmcomp.question.R;

/**
 * Googleガイドラインに沿ったMaterialなカードでアバターと2テキストのやつです
 * @link card_material_avatar_with_text
 */
public class MaterialCardAvatarWithTextViewHolder extends RecyclerView.ViewHolder{

    public TextView mPrimaryTextView;
    public TextView mSecondaryTextView;
    public ImageView mAvatarImageView;

    public MaterialCardAvatarWithTextViewHolder(View itemView) {
        super(itemView);
        mAvatarImageView = (ImageView) itemView.findViewById(R.id.avatar);
        mPrimaryTextView = (TextView) itemView.findViewById(R.id.primaryText);
        mSecondaryTextView = (TextView) itemView.findViewById(R.id.secondaryText);

    }


}
