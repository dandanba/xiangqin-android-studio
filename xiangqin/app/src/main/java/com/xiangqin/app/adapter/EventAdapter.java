package com.xiangqin.app.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangqin.app.R;
import com.xiangqin.app.model.Event;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by dandanba on 11/16/15.
 */
public class EventAdapter extends BaseAdapter<EventDataHolder> {

    private OnRecyclerViewItemClickListener mItemClickListener;

    public EventAdapter(Context context, OnRecyclerViewItemClickListener onItemClickListener) {
        super(context);
        mItemClickListener = onItemClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        BaseViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
                v = View.inflate(mContext, R.layout.layout_event_item, null);
                v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                viewHolder = new EventViewHolder(v, mItemClickListener);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final EventDataHolder datHolder = mDatas.get(position);
        final int itemViewType = datHolder.getType();
        switch (itemViewType) {
            case 0:
                EventViewHolder userViewHolder = (EventViewHolder) holder;
                userViewHolder.bind(mContext, datHolder, position);
                break;
            default:
                break;
        }
    }

    public class EventViewHolder extends BaseViewHolder {
        @Bind(R.id.icon)
        SimpleDraweeView mIcon;

        public EventViewHolder(View view, OnRecyclerViewItemClickListener onItemClickListener) {
            super(view, onItemClickListener);
            ButterKnife.bind(this, view);
        }

        public void bind(Context context, EventDataHolder datHolder, int position) {
            final Event user = datHolder.getEvent();
            mIcon.setImageURI(Uri.parse(user.getImageUrl()));
        }
    }
}
