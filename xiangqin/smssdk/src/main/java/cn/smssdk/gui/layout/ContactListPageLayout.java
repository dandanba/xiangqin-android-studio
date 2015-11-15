/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2014年 mob.com. All rights reserved.
 */
package cn.smssdk.gui.layout;

import android.content.Context;
import android.widget.LinearLayout;

import cn.smssdk.gui.ContactsListView;

/**联系人列表页面布局*/
public class ContactListPageLayout extends BasePageLayout {

	public ContactListPageLayout(Context c) {
		super(c, true);
	}

	protected void onCreateContent(LinearLayout parent) {
		ContactsListView contactsList = new ContactsListView(context);
		contactsList.setId(Res.id.clContact);
		LinearLayout.LayoutParams listParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
		contactsList.setLayoutParams(listParams);

		parent.addView(contactsList);
	}

}
