package com.xiangqin.app.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangqin.app.R;
import com.xiangqin.app.model.Notification;
import com.xiangqin.app.model.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dandanba on 11/16/15.
 */
public class UserAdapter extends BaseAdapter<UserDataHolder> {

    private OnRecyclerViewItemClickListener mItemClickListener;

    public UserAdapter(Context context, OnRecyclerViewItemClickListener onItemClickListener) {
        super(context);
        mItemClickListener = onItemClickListener;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        BaseViewHolder viewHolder = null;
        switch (viewType) {
            case 0:
                v = View.inflate(mContext, R.layout.layout_user_item, null);
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
        @Bind(R.id.title)
        TextView mTitleText;
        @Bind(R.id.text)
        TextView mTextText;
        @Bind(R.id.info)
        TextView mInfoText;
        @Bind(R.id.say_hello_button)
        View mSayHelloButton;


        @OnClick(R.id.say_hello_button)
        public void onSayHelloButtonClick(View view) {
            final User targetUser = (User) view.getTag();
            final User user = User.getCurrentUser(User.class);
            targetUser.sayHello(user);


            Notification notification = new Notification();
            notification.setMessage("打招呼");
            notification.setUser(user.getUsername());
            notification.setTargetUser(targetUser.getUsername());
            notification.saveInBackground();


        }

        public UserViewHolder(View view, OnRecyclerViewItemClickListener onItemClickListener) {
            super(view, onItemClickListener);
            ButterKnife.bind(this, view);
        }

        public void bind(Context context, UserDataHolder datHolder, int position) {
            final User user = datHolder.getUser();
            mIcon.setImageURI(Uri.parse(user.getIconUrl()));
            mTitleText.setText(user.getUsername());
            mTextText.setText(String.format("%1$s %2$s", user.getBirthday(), user.getArea()));
            mInfoText.setText(String.format("%1$scm %2$s", user.getHeight(), user.getEarning()));
            mSayHelloButton.setTag(user);
        }
    }
}
