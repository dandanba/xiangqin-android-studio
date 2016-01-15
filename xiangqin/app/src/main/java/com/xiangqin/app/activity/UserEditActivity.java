package com.xiangqin.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.SaveCallback;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xiangqin.app.Constants;
import com.xiangqin.app.R;
import com.xiangqin.app.XQApplication;
import com.xiangqin.app.dialog.DateFragmentDialog;
import com.xiangqin.app.dialog.EditFragmentDialog;
import com.xiangqin.app.dialog.SelectFragmentDialog;
import com.xiangqin.app.event.ActionEvent;
import com.xiangqin.app.model.User;
import com.xiangqin.app.utils.FileUtils;
import com.xiangqin.app.utils.ToastUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class UserEditActivity extends BaseActivity implements View.OnClickListener, EditFragmentDialog.OnEditChangedListener, DateFragmentDialog.OnDateChangedListener, SelectFragmentDialog.OnSelectChangedListener {
    @Bind(R.id.common_title_text)
    TextView mCommonTitleText;
    @Bind(R.id.top_layout)
    LinearLayout mTopLayout;
    @Bind(R.id.bottom_layout)
    LinearLayout mBottomLayout;
    @Bind(R.id.login_button)
    Button mNextButton;
    @Bind(R.id.icon)
    SimpleDraweeView mIcon;
    private static final int REQUEST_IMAGE = 1;

    private final static String[] SEX = new String[]{"男", "女"};
    private final static String[] EDUCATION = new String[]{"中专", "高中及以下", "大专", "大学本科", "硕士", "博士"};
    private final static String[] STATE = new String[]{"未婚", "离异", "丧偶"};
    private final static String[] EARNING = new String[]{"1000元以下", "1001-2000元", "2001-3000元", "3001-5000元", "5001-8000元", "8001-10000元", "10001-20000元", "20001-50000元"};
    private final static String[] HEIGHT = new String[]{"150", "151", "152", "153", "154", "155", "156", "157", "158", "159",
            "160", "161", "162", "163", "164", "165", "166", "167", "168", "169",
            "170", "171", "172", "173", "174", "175", "176", "177", "178", "179"};

    private final HashMap<String, View> mLayouts = new HashMap<>();
    private boolean mUpdateUser;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        mUpdateUser = getIntent().getBooleanExtra("update_user", false);
        mUser = mUpdateUser ? User.getCurrentUser(User.class) : new User();
        ButterKnife.bind(this);

        mIcon.setImageURI(Uri.parse(mUser.getIconUrl()));
        mCommonTitleText.setText(mUpdateUser ? "修改个人信息" : "个人信息");
        mNextButton.setText(mUpdateUser ? "修改" : "注册");
        initItemLayout(mTopLayout, new String[]{"性别:sex", "生日:birthday", "身高:height", "学历:education", "婚姻状况:state"});
        initItemLayout(mBottomLayout, new String[]{"月收入:earning", "昵称:nickname"});
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                // Get the result list of select image paths
                List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                // do your logic ....
                final File file = new File(path.get(0));
                byte[] bs = FileUtils.readFile(file);
                mIcon.setImageURI(Uri.parse("file://" + file.getAbsolutePath()));
                final AVFile avFile = new AVFile(file.getName(), bs);
                avFile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            final String fileUrl = avFile.getUrl();
                            mUser.setIcon(fileUrl);
                        }
                    }
                });


            }
        }
    }

    @Override
    public void onEvent(Object event) {
        super.onEvent(event);
        if (event instanceof ActionEvent) {
            final ActionEvent action = (ActionEvent) event;
            if ("account".equals(action.mAction)) {
                finish();
            }
        }
    }

    @OnClick(R.id.icon)
    public void onIconButtonClick(View v) {
        final Intent intent = IntentGenerator.showSelector(this, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @OnClick(R.id.titlebar_left_button)
    public void onLeftButtonClcik(View view) {
        onBackPressed();
    }

    @OnClick(R.id.login_button)
    public void onLoginButtonClcik(View view) {
        final String nickname = getText("nickname");
        final String birthday = getText("birthday");
        final String sex = getText("sex");
        final String earning = getText("earning");
        final String education = getText("education");
        final String height = getText("height");
        final String state = getText("state");
        final User user = mUser;

        if (Constants.DEFAULT_USER_HEADER.equals(mUser.getIcon())) {
            ToastUtils.showToast(this, "请选择您的头像");
            return;
        } else if ("未填写".equals(nickname)) {
            ToastUtils.showToast(this, "请选择您的昵称");
            return;
        } else if ("未填写".equals(birthday)) {
            ToastUtils.showToast(this, "请选择您的生日");
            return;
        } else if ("未填写".equals(height)) {
            ToastUtils.showToast(this, "请选择您的身高");
            return;
        } else if ("未填写".equals(education)) {
            ToastUtils.showToast(this, "请选择您的学历");
            return;
        } else if ("未填写".equals(nickname)) {
            ToastUtils.showToast(this, "请选择您的昵称");
            return;
        } else if ("未填写".equals(state)) {
            ToastUtils.showToast(this, "请选择您的婚姻状况");
            return;
        } else if ("未填写".equals(earning)) {
            ToastUtils.showToast(this, "请选择您的月收入");
            return;
        } else if ("未填写".equals(sex)) {
            ToastUtils.showToast(this, "请选择您的性别");
            return;
        }

        user.setNickname(nickname);
        user.setBirthday(birthday);
        user.setSex(sex);
        user.setEarning(earning);
        user.setEducation(education);
        user.setHeight(height);
        user.setState(state);

        if (mUpdateUser) {
            mUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        finish();
                    }
                }
            });
        } else {
            XQApplication.getInstance().getMessager().put("user", user);
            startActivity(IntentGenerator.genSimpleActivityIntent(this, RegisterActivity.class));
        }
    }

    private void initItemLayout(LinearLayout itemsLayout, String[] infoArray) {
        final int size = infoArray.length;
        final LayoutInflater inflater = getLayoutInflater();
        View itemLayout;
        InfoItem item;
        String[] sa;
        String text;
        for (int i = 0; i < size; i++) {
            sa = infoArray[i].split(":");
            text = mUpdateUser ? getTextByUser(sa[1]) : "未填写";
            item = new InfoItem(sa[0], text, sa[1]);
            itemLayout = inflater.inflate(R.layout.layout_user_edit_item, null);
            itemLayout.setTag(item);
            itemLayout.setOnClickListener(this);
            itemsLayout.addView(itemLayout);
            mLayouts.put(item.tag, itemLayout);
            updateUserItem(itemLayout, item);
        }

    }

    private void updateUserItem(View itemLayout, InfoItem item) {
        TextView titleText, textText;

        titleText = (TextView) itemLayout.findViewById(R.id.title_text);
        textText = (TextView) itemLayout.findViewById(R.id.text_text);

        titleText.setText(item.title);
        textText.setText(item.text);
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


    private String getText(String tag) {
        InfoItem item = (InfoItem) mLayouts.get(tag).getTag();
        return item.text;
    }

    @Override
    public void onClick(View v) {
        final InfoItem item = (InfoItem) v.getTag();
        if ("nickname".equals(item.tag)) {
            showEditDialog(getSupportFragmentManager(), item.title, item.tag);
        } else if ("birthday".equals(item.tag)) {
            showDateDialog(getSupportFragmentManager(), item.title, item.tag);
        } else if ("sex".equals(item.tag)) {
            showSelectDialog(getSupportFragmentManager(), item.title, item.tag, SEX);
        } else if ("height".equals(item.tag)) {
            showSelectDialog(getSupportFragmentManager(), item.title, item.tag, HEIGHT);
        } else if ("earning".equals(item.tag)) {
            showSelectDialog(getSupportFragmentManager(), item.title, item.tag, EARNING);
        } else if ("education".equals(item.tag)) {
            showSelectDialog(getSupportFragmentManager(), item.title, item.tag, EDUCATION);
        } else if ("state".equals(item.tag)) {
            showSelectDialog(getSupportFragmentManager(), item.title, item.tag, STATE);
        }
    }

    public void showEditDialog(FragmentManager fragmentManager, String title, String tag) {
        EditFragmentDialog dialog = EditFragmentDialog.instance(title);
        dialog.setOnEditChangedListener(this);
        dialog.show(fragmentManager, tag);
    }

    public void showDateDialog(FragmentManager fragmentManager, String title, String tag) {
        DateFragmentDialog dialog = DateFragmentDialog.instance(title);
        dialog.setOnDateChangedListener(this);
        dialog.show(fragmentManager, tag);
    }

    public void showSelectDialog(FragmentManager fragmentManager, String title, String tag, String[] sa) {
        SelectFragmentDialog dialog = SelectFragmentDialog.instance(title, sa);
        dialog.setOnSelectChangedListener(this);
        dialog.show(fragmentManager, tag);
    }

    @Override
    public void onEditChanged(DialogFragment dialog, String text) {
        final String tag = dialog.getTag();
        if (tag.equals("nickname")) {
            final View itemLayout = mLayouts.get(tag);
            final InfoItem item = (InfoItem) itemLayout.getTag();
            item.text = text;
            updateUserItem(itemLayout, item);
        }
    }

    @Override
    public void onDateChanged(DialogFragment dialog, String year_month_day) {
        final String tag = dialog.getTag();
        if (tag.equals("birthday")) {
            final View itemLayout = mLayouts.get(tag);
            final InfoItem item = (InfoItem) itemLayout.getTag();
            item.text = year_month_day;
            updateUserItem(itemLayout, item);
        }
    }

    @Override
    public void onSelectChanged(DialogFragment dialog, String text) {
        final String tag = dialog.getTag();
        if (tag.equals("sex") || tag.equals("height") || tag.equals("education") || tag.equals("state") || tag.equals("earning")) {
            final View itemLayout = mLayouts.get(tag);
            final InfoItem item = (InfoItem) itemLayout.getTag();
            item.text = text;
            updateUserItem(itemLayout, item);
        }
    }


    static class InfoItem {
        String title;
        String text;
        String tag;

        public InfoItem(String title, String text, String tag) {
            this.title = title;
            this.text = text;
            this.tag = tag;
        }
    }

}