package com.xiangqin.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.facebook.drawee.view.SimpleDraweeView;
import com.leancloud.im.guide.AVImClientManager;
import com.leancloud.im.guide.Constants;
import com.leancloud.im.guide.activity.AVSingleChatActivity;
import com.xiangqin.app.R;
import com.xiangqin.app.model.Notification;
import com.xiangqin.app.model.User;
import com.xiangqin.app.utils.DisplayUtils;
import com.xiangqin.app.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dandanba on 11/16/15.
 */
public class InfoAdapter extends BaseAdapter<UserDataHolder> {

    private OnRecyclerViewItemClickListener mItemClickListener;
    private User mUser;

    public InfoAdapter(Context context, User user, OnRecyclerViewItemClickListener onItemClickListener) {
        super(context);
        mItemClickListener = onItemClickListener;
        final UserDataHolder headerDataHolder = new UserDataHolder(0);
        mUser = user;
        mDatas.add(headerDataHolder);

        UserDataHolder itemDataHolder;

        mDatas.add(new UserDataHolder(2));

        itemDataHolder = new UserDataHolder(1);
        itemDataHolder.setTag("nickname");
        mDatas.add(itemDataHolder);

        itemDataHolder = new UserDataHolder(1);
        itemDataHolder.setTag("birthday");
        mDatas.add(itemDataHolder);

        itemDataHolder = new UserDataHolder(1);
        itemDataHolder.setTag("sex");
        mDatas.add(itemDataHolder);

        itemDataHolder = new UserDataHolder(1);
        itemDataHolder.setTag("earning");
        mDatas.add(itemDataHolder);

        itemDataHolder = new UserDataHolder(1);
        itemDataHolder.setTag("education");
        mDatas.add(itemDataHolder);

        itemDataHolder = new UserDataHolder(1);
        itemDataHolder.setTag("height");
        mDatas.add(itemDataHolder);

        itemDataHolder = new UserDataHolder(1);
        itemDataHolder.setTag("state");
        mDatas.add(itemDataHolder);

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
            case 1:
                v = View.inflate(mContext, R.layout.layout_title_text_arrow, null);
                v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                viewHolder = new ItemViewHolder(v, mItemClickListener);
                break;
            case 2:
                v = View.inflate(mContext, R.layout.layout_divider_item, null);
                v.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, DisplayUtils.dpToPxInt(mContext, 20)));
                viewHolder = new DividerViewHolder(v, mItemClickListener);
                break;
            default:
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        final UserDataHolder dataHolder = mDatas.get(position);
        final int itemViewType = dataHolder.getType();
        switch (itemViewType) {
            case 0:
                HeaderViewHolder userViewHolder = (HeaderViewHolder) holder;
                userViewHolder.bind(mContext, dataHolder, position);
                break;
            case 1:
                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                itemViewHolder.bind(mContext, dataHolder, position);
                break;
            case 2:
                DividerViewHolder dividerViewHolder = (DividerViewHolder) holder;
                dividerViewHolder.bind(mContext, dataHolder, position);
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

        @OnClick(R.id.chat)
        public void onChatButtonClick(View view) {
            openClient(mUser);
        }

        public HeaderViewHolder(View view, OnRecyclerViewItemClickListener onItemClickListener) {
            super(view, onItemClickListener);
            ButterKnife.bind(this, view);
        }

        public void bind(Context context, UserDataHolder dataHolder, int position) {
            mIcon.setImageURI(Uri.parse(mUser.getIconUrl()));
            mTitleText.setText(mUser.getNickname());
        }

        private void openClient(final User targetUser) {
            final User user = User.getCurrentUser(User.class);
            AVImClientManager.getInstance().open(user.getUsername(), new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {
                    if (filterException(e)) {
                        Notification notification = new Notification();
                        notification.setMessage("私聊");
                        notification.setUser(user.getUsername());
                        notification.setTargetUser(targetUser.getUsername());
                        notification.saveInBackground();

                        Intent intent = new Intent(mContext, AVSingleChatActivity.class);
                        intent.putExtra(Constants.MEMBER_ID, targetUser.getUsername());
                        intent.putExtra(Constants.ACTIVITY_TITLE, targetUser.getUsername());
                        mContext.startActivity(intent);
                    }
                }
            });
        }

        protected boolean filterException(Exception e) {
            if (e != null) {
                e.printStackTrace();
                ToastUtils.showToast(mContext, e.getMessage());
                return false;
            } else {
                return true;
            }
        }
    }

    public class ItemViewHolder extends BaseViewHolder {
        @Bind(R.id.text_text)
        TextView mTextText;
        @Bind(R.id.title_text)
        TextView mTitleText;

        public ItemViewHolder(View view, OnRecyclerViewItemClickListener onItemClickListener) {
            super(view, onItemClickListener);
            ButterKnife.bind(this, view);
        }

        public void bind(Context context, UserDataHolder dataHolder, int position) {
            mTitleText.setText(getTitleByUser(dataHolder.getTag()));
            mTextText.setText(getTextByUser(dataHolder.getTag()));
        }
    }


    public class DividerViewHolder extends BaseViewHolder {

        public DividerViewHolder(View view, OnRecyclerViewItemClickListener onItemClickListener) {
            super(view, onItemClickListener);
        }

        public void bind(Context context, BaseDataHolder dataHolder, int position) {

        }
    }

    private String getTitleByUser(String tag) {
        switch (tag) {
            case "nickname":
                return "昵称:";
            case "birthday":
                return "生日:";
            case "sex":
                return "性别:";
            case "earning":
                return "月收入:";
            case "education":
                return "学历:";
            case "height":
                return "身高:";
            case "state":
                return "婚姻状况:";
            default:
                return "";
        }
    }

    private String getTextByUser(String tag) {
        switch (tag) {
            case "nickname":
                return mUser.getNickname();
            case "birthday":
                return mUser.getBirthday();
            case "sex":
                return mUser.getSex();
            case "earning":
                return mUser.getEarning();
            case "education":
                return mUser.getEducation();
            case "height":
                return mUser.getHeight();
            case "state":
                return mUser.getState();
            default:
                return "";
        }
    }


}
