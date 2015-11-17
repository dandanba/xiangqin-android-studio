package com.xiangqin.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangqin.app.R;

/**
 * Created by dandanba on 11/16/15.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public View mItemView;
    private OnRecyclerViewItemClickListener mItemClickListener;

    public BaseViewHolder(View itemView, OnRecyclerViewItemClickListener itemClickListener) {
        super(itemView);
        mItemView = itemView;
        mItemClickListener = itemClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(mItemView, getPosition());
        }
    }

    public void setRoundAsCircle(SimpleDraweeView image) {
        final GenericDraweeHierarchy hierarchy = image.getHierarchy();
        final RoundingParams roundingParams = new RoundingParams();
        roundingParams.setBorder(R.color.gray, 2.0f);
        roundingParams.setRoundAsCircle(true);
        hierarchy.setRoundingParams(roundingParams);
    }
}
