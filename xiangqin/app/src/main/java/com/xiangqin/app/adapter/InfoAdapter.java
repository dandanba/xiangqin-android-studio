package com.xiangqin.app.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangqin.app.R;
import com.xiangqin.app.event.ActionEvent;
import com.xiangqin.app.model.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by dandanba on 11/16/15.
 */
public class InfoAdapter extends BaseAdapter<UserDataHolder> {

    private OnRecyclerViewItemClickListener mItemClickListener;

    public InfoAdapter(Context context, User user, OnRecyclerViewItemClickListener onItemClickListener) {
        super(context);
        mItemClickListener = onItemClickListener;
        final UserDataHolder headerDataHolder = new UserDataHolder(0);
        headerDataHolder.setUser(user);
        mDatas.add(headerDataHolder);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        BaseViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
                v = View.inflate(mContext, R.layout.layout_user_header, null);
                v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                viewHolder = new HeaderViewHolder(v, mItemClickListener);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final UserDataHolder datHolder = mDatas.get(position);
        final int itemViewType = datHolder.getType();
        switch (itemViewType) {
            case 0:
                HeaderViewHolder userViewHolder = (HeaderViewHolder) holder;
                userViewHolder.bind(mContext, datHolder, position);
                break;
            default:
                break;
        }
    }

    public class HeaderViewHolder extends BaseViewHolder {
        @Bind(R.id.icon)
        SimpleDraweeView mIcon;
        @Bind(R.id.title)
        TextView mTitleText;


        @OnClick(R.id.icon)
        public void onIconClick(View view) {
            final User user = (User) view.getTag();
            final ActionEvent event = new ActionEvent("onIconClick");
            event.mData = user;
            EventBus.getDefault().post(event);

        }

        public HeaderViewHolder(View view, OnRecyclerViewItemClickListener onItemClickListener) {
            super(view, onItemClickListener);
            ButterKnife.bind(this, view);
        }

        public void bind(Context context, UserDataHolder datHolder, int position) {
            final User user = datHolder.getUser();
            mIcon.setImageURI(Uri.parse(user.getIcon()));
            mIcon.setTag(user);
            mTitleText.setText(user.getUsername());
        }
    }
}
