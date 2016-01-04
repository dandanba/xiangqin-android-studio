package com.xiangqin.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.leancloud.im.guide.AVImClientManager;
import com.leancloud.im.guide.Constants;
import com.leancloud.im.guide.activity.AVSingleChatActivity;
import com.xiangqin.app.R;
import com.xiangqin.app.model.Notification;
import com.xiangqin.app.model.User;
import com.xiangqin.app.utils.ToastUtils;
import com.xiangqin.app.widget.RelativeTimeTextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        @Bind(R.id.time)
        RelativeTimeTextView mTime;

        @OnClick(R.id.text)
        public void onTextButtonClick(View view) {
            final TextView textView = (TextView) view;
            final String text = textView.getText().toString();
            final Notification notification = (Notification) view.getTag();
            final AVQuery mUserQuery = AVQuery.getQuery(User.class);
            switch (text) {
                case "打招呼":
                    mUserQuery.whereEqualTo("username", notification.getUser());
                    mUserQuery.findInBackground(new FindCallback<User>() {
                        @Override
                        public void done(List<User> list, AVException e) {
                            if (e != null) {
                                ToastUtils.showToast(mContext, e.toString());
                                return;
                            }

                            final User targetUser = list.get(0);
                            final User user = User.getCurrentUser(User.class);
                            targetUser.sayHello(user);

                            Notification notification = new Notification();
                            notification.setMessage("打招呼");
                            notification.setUser(user.getUsername());
                            notification.setTargetUser(targetUser.getUsername());
                            notification.saveInBackground();
                        }
                    });

                    break;
                case "私聊":
                    mUserQuery.whereEqualTo("username", notification.getUser());
                    mUserQuery.findInBackground(new FindCallback<User>() {
                        @Override
                        public void done(List<User> list, AVException e) {
                            if (e != null) {
                                ToastUtils.showToast(mContext, e.toString());
                                return;
                            }

                            final User targetUser = list.get(0);
                            openClient(targetUser);
                        }
                    });

                    break;
                default:
                    break;
            }
        }

        public UserViewHolder(View view, OnRecyclerViewItemClickListener onItemClickListener) {
            super(view, onItemClickListener);
            ButterKnife.bind(this, view);
        }

        public void bind(Context context, NotificationDataHolder datHolder, int position) {
            final Notification notification = datHolder.getNotification();
            mTitleText.setText(notification.getUser() +
                            (notification.getMessage().equals("打招呼") ? " 向您打了声招呼" :
                                    notification.getMessage().equals("私聊") ? " 想和您私聊" : "")
            );
            mTime.setReferenceTime(notification.getCreatedAt().getTime());
            mTextText.setText(notification.getMessage());
            mTextText.setTag(notification);
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
}
