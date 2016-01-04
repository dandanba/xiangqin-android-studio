package com.xiangqin.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangqin.app.R;
import com.xiangqin.app.activity.IntentGenerator;
import com.xiangqin.app.activity.UserEditActivity;
import com.xiangqin.app.event.ActionEvent;
import com.xiangqin.app.model.User;
import com.xiangqin.app.utils.DisplayUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by dandanba on 11/16/15.
 */
public class MyAdapter extends BaseAdapter<MyDataHolder> implements OnRecyclerViewItemClickListener {

    private OnRecyclerViewItemClickListener mItemClickListener;

    private User mUser;

    public MyAdapter(Context context, User user) {
        super(context);
        MyDataHolder itemDataHolder;

        mDatas.add(new MyDataHolder(1));

        itemDataHolder = new MyDataHolder(2);
        mDatas.add(itemDataHolder);

        mDatas.add(new MyDataHolder(1));

        itemDataHolder = new MyDataHolder(0);
        itemDataHolder.setTitle("新手礼包");
        itemDataHolder.setText("");

        mDatas.add(itemDataHolder);

        itemDataHolder = new MyDataHolder(0);
        itemDataHolder.setTitle("有奖励未领取");
        itemDataHolder.setText("");

        mDatas.add(itemDataHolder);

        itemDataHolder = new MyDataHolder(0);
        itemDataHolder.setTitle("红娘一对一服务");
        itemDataHolder.setText("");

        mDatas.add(itemDataHolder);

        mItemClickListener = this;
        mUser = user;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        BaseViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
                v = View.inflate(mContext, R.layout.layout_settings_item, null);
                v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, DisplayUtils.dpToPxInt(mContext, 60)));
                viewHolder = new SettingsViewHolder(v, mItemClickListener);
                break;
            case 1:
                v = View.inflate(mContext, R.layout.layout_divider_item, null);
                v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, DisplayUtils.dpToPxInt(mContext, 20)));
                viewHolder = new DividerViewHolder(v, mItemClickListener);
                break;
            case 2:
                v = View.inflate(mContext, R.layout.layout_my_item, null);
                v.setBackgroundColor(Color.WHITE);
                v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                viewHolder = new MyViewHolder(v, mItemClickListener);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final MyDataHolder dataHolder = mDatas.get(position);
        final int itemViewType = dataHolder.getType();
        switch (itemViewType) {
            case 0:
                SettingsViewHolder settingsViewHolder = (SettingsViewHolder) holder;
                settingsViewHolder.bind(mContext, dataHolder, position);
                break;
            case 1:
                DividerViewHolder dividerViewHolder = (DividerViewHolder) holder;
                dividerViewHolder.bind(mContext, dataHolder, position);
                break;
            case 2:
                MyViewHolder myViewHolder = (MyViewHolder) holder;
                myViewHolder.bind(mContext, dataHolder, position);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
    }

    public class SettingsViewHolder extends BaseViewHolder {
        @Bind(R.id.title)
        TextView mTitleText;
        @Bind(R.id.text)
        TextView mTextText;

        public SettingsViewHolder(View view, OnRecyclerViewItemClickListener onItemClickListener) {
            super(view, onItemClickListener);
            ButterKnife.bind(this, view);
        }

        public void bind(Context context, MyDataHolder datHolder, int position) {
            mTitleText.setText(datHolder.getTitle());
            mTextText.setText(datHolder.getText());
        }
    }

    public class DividerViewHolder extends BaseViewHolder {

        public DividerViewHolder(View view, OnRecyclerViewItemClickListener onItemClickListener) {
            super(view, onItemClickListener);
        }

        public void bind(Context context, MyDataHolder datHolder, int position) {

        }
    }

    public class MyViewHolder extends BaseViewHolder {
        @Bind(R.id.icon)
        SimpleDraweeView mIcon;
        @Bind(R.id.title)
        TextView mTitleText;
        @Bind(R.id.text)
        TextView mTextText;
        @Bind(R.id.info)
        TextView mInfoText;


        @OnClick(R.id.icon)
        public void onHeadClick(View view) {
            EventBus.getDefault().post(new ActionEvent("header"));
        }

        @OnClick(R.id.eidt_button)
        public void onEditButtonClick(View view) {
            mContext.startActivity(IntentGenerator.genSimpleActivityIntent(mContext, UserEditActivity.class).putExtra("update_user", true));
        }

        public MyViewHolder(View view, OnRecyclerViewItemClickListener onItemClickListener) {
            super(view, onItemClickListener);
            ButterKnife.bind(this, view);
        }

        public void bind(Context context, MyDataHolder datHolder, int position) {
            final User user = mUser;
            mIcon.setImageURI(Uri.parse(user.getIconUrl()));
            mTitleText.setText(user.getUsername());
            mTextText.setText(String.format("%1$s %2$s", user.getBirthday(), user.getArea()));
            mInfoText.setText(String.format("%1$scm %2$s", user.getHeight(), user.getEarning()));
        }
    }
}
