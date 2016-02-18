package com.xiangqin.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.feedback.FeedbackAgent;
import com.xiangqin.app.R;
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
public class SettingsAdapter extends BaseAdapter<SettingsDataHolder> implements OnRecyclerViewItemClickListener {

    private OnRecyclerViewItemClickListener mItemClickListener;

    public SettingsAdapter(Context context, OnRecyclerViewItemClickListener onItemClickListener) {
        super(context);
        SettingsDataHolder itemDataHolder;

        mDatas.add(new SettingsDataHolder(1));
//
//        itemDataHolder = new SettingsDataHolder(0);
//        itemDataHolder.setTitle("推送设置");
//        itemDataHolder.setText("");
//
//        mDatas.add(itemDataHolder);
//
//        itemDataHolder = new SettingsDataHolder(0);
//        itemDataHolder.setTitle("账号保护");
//        itemDataHolder.setText("");
//
//        mDatas.add(itemDataHolder);
//
//
//        itemDataHolder = new SettingsDataHolder(0);
//        itemDataHolder.setTitle("更换手机");
//        itemDataHolder.setText("");
//
//        mDatas.add(itemDataHolder);
//
//        itemDataHolder = new SettingsDataHolder(0);
//        itemDataHolder.setTitle("修改密码");
//        itemDataHolder.setText("");
//
//        mDatas.add(itemDataHolder);
//
//        itemDataHolder = new SettingsDataHolder(0);
//        itemDataHolder.setTitle("清除缓存");
//        itemDataHolder.setText("");
//
//        mDatas.add(itemDataHolder);
//
//        itemDataHolder = new SettingsDataHolder(0);
//        itemDataHolder.setTitle("邀请好友使用");
//        itemDataHolder.setText("");
//
//        mDatas.add(itemDataHolder);
//
//        mDatas.add(new SettingsDataHolder(1));

        itemDataHolder = new SettingsDataHolder(0);
        itemDataHolder.setTitle("意见反馈");
        itemDataHolder.setText("");

        mDatas.add(itemDataHolder);
//
//        itemDataHolder = new SettingsDataHolder(0);
//        itemDataHolder.setTitle("关于");
//        itemDataHolder.setText("");

//        mDatas.add(itemDataHolder);

        itemDataHolder = new SettingsDataHolder(2);
        itemDataHolder.setTitle("退出登录");
        itemDataHolder.setText("");

        mDatas.add(itemDataHolder);

        mItemClickListener = this;
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
                v = View.inflate(mContext, R.layout.layout_button, null);
                v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                viewHolder = new ButtonViewHolder(v, mItemClickListener);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final SettingsDataHolder dataHolder = mDatas.get(position);
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
                ButtonViewHolder buttonViewHolder = (ButtonViewHolder) holder;
                buttonViewHolder.bind(mContext, dataHolder, position);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        final String title =
                mDatas.get(position).getTitle();
        switch (title) {
            case "意见反馈":
                FeedbackAgent agent = new FeedbackAgent(mContext);
                agent.startDefaultThreadActivity();
                break;

        }
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

        public void bind(Context context, SettingsDataHolder datHolder, int position) {
            mTitleText.setText(datHolder.getTitle());
            mTextText.setText(datHolder.getText());
        }
    }


    public class DividerViewHolder extends BaseViewHolder {

        public DividerViewHolder(View view, OnRecyclerViewItemClickListener onItemClickListener) {
            super(view, onItemClickListener);
        }

        public void bind(Context context, SettingsDataHolder datHolder, int position) {

        }
    }

    public class ButtonViewHolder extends BaseViewHolder {
        @Bind(R.id.button)
        TextView mTitleText;


        @OnClick(R.id.button)
        public void onButtonClick(View view) {
            final String title = (String) view.getTag();
            if (title.equals("退出登录")) {
                final User user = User.getCurrentUser(User.class);
                user.logOut();
                EventBus.getDefault().post(new ActionEvent("logout"));
            }
        }

        public ButtonViewHolder(View view, OnRecyclerViewItemClickListener onItemClickListener) {
            super(view, onItemClickListener);
            ButterKnife.bind(this, view);
        }

        public void bind(Context context, SettingsDataHolder datHolder, int position) {
            mTitleText.setText(datHolder.getTitle());
            mTitleText.setTag(datHolder.getTitle());
        }
    }
}
