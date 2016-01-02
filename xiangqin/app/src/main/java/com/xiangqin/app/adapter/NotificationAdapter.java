package com.xiangqin.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiangqin.app.R;
import com.xiangqin.app.model.Notification;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dandanba on 11/16/15.
 */
public class NotificationAdapter extends BaseAdapter<NotificationDataHolder> {

    private OnRecyclerViewItemClickListener mItemClickListener;

    public NotificationAdapter(Context context, OnRecyclerViewItemClickListener onItemClickListener) {
        super(context);
        mItemClickListener = onItemClickListener;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        BaseViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
                v = View.inflate(mContext, R.layout.layout_notification_item, null);
                v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                viewHolder = new UserViewHolder(v, mItemClickListener);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final NotificationDataHolder datHolder = mDatas.get(position);
        final int itemViewType = datHolder.getType();
        switch (itemViewType) {
            case 0:
                UserViewHolder userViewHolder = (UserViewHolder) holder;
                userViewHolder.bind(mContext, datHolder, position);
                break;
            default:
                break;
        }
    }

    public class UserViewHolder extends BaseViewHolder {
        @Bind(R.id.title)
        TextView mTitleText;
        @Bind(R.id.text)
        TextView mTextText;
        @Bind(R.id.info)
        TextView mInfoText;

        public UserViewHolder(View view, OnRecyclerViewItemClickListener onItemClickListener) {
            super(view, onItemClickListener);
            ButterKnife.bind(this, view);
        }

        public void bind(Context context, NotificationDataHolder datHolder, int position) {
            final Notification user = datHolder.getNotification();
            mTitleText.setText(user.getUser());
            mTextText.setText(user.getTargetUser());
            mInfoText.setText(user.getMessage());
        }
    }
}
