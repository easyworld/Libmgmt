package com.dhu.libmgmt;

import android.app.ActionBar;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.dhu.libmgmt.db.DataBaseHelper;
import com.dhu.libmgmt.domain.User;
import com.dhu.libmgmt.fragment.BookTabFragment;
import com.dhu.libmgmt.fragment.OrderTabFragment;
import com.dhu.libmgmt.fragment.UserTabFragment;
import com.dhu.libmgmt.listener.TabListener;

public class MainActivity extends Activity {
	DataBaseHelper dbh = new DataBaseHelper(this);
	SQLiteDatabase db;
	ActionBar bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		db = dbh.getWritableDatabase();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Bundle b = this.getIntent().getExtras();
		String userStatus = String.valueOf(b.get("status"));
		int uid = b.getInt("uid");
		bar = getActionBar();
		if (bar.getNavigationMode() != ActionBar.NAVIGATION_MODE_TABS)
			bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setDisplayShowTitleEnabled(false);
		bar.setDisplayShowHomeEnabled(false);
		bar.addTab(bar
				.newTab()
				.setText("图书")
				.setTabListener(
						new TabListener(new BookTabFragment("图书", uid,
								userStatus, db))));
		if (User.ADMIN.equals(userStatus))
			bar.addTab(bar
					.newTab()
					.setText("用户")
					.setTabListener(
							new TabListener(new UserTabFragment("用户",
									userStatus, db))));
		bar.addTab(bar
				.newTab()
				.setText("订单")
				.setTabListener(
						new TabListener(new OrderTabFragment("订单", uid,
								userStatus, db))));
	}

}
