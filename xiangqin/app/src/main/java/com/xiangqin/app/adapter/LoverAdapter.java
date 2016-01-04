package com.xiangqin.app.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangqin.app.R;
import com.xiangqin.app.model.User;
import com.xiangqin.app.utils.DisplayUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dandanba on 11/16/15.
 */
public class LoverAdapter extends BaseAdapter<UserDataHolder> {

    private OnRecyclerViewItemClickListener mItemClickListener;

    public LoverAdapter(Context context, OnRecyclerViewItemClickListener onItemClickListener) {
        super(context);
        mItemClickListener = onItemClickListener;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        BaseViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
                v = View.inflate(mContext, R.layout.layout_lover_item, null);
                v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, DisplayUtils.dpToPxInt(mContext, 220)));
                viewHolder = new UserViewHolder(v, mItemClickListener);
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
                UserViewHolder userViewHolder = (UserViewHolder) holder;
                userViewHolder.bind(mContext, datHolder, position);
                break;
            default:
                break;
        }
    }

    public class UserViewHolder extends BaseViewHolder {
        @Bind(R.id.icon)
        SimpleDraweeView mIcon;

        public UserViewHolder(View view, OnRecyclerViewItemClickListener onItemClickListener) {
            super(view, onItemClickListener);
            ButterKnife.bind(this, view);
        }

        public void bind(Context context, UserDataHolder datHolder, int position) {
            final User user = datHolder.getUser();
            mIcon.setImageURI(Uri.parse(user.getIconUrl()));
        }
    }
}
