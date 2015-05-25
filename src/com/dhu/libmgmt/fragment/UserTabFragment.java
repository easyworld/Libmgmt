package com.dhu.libmgmt.fragment;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.dhu.libmgmt.R;
import com.dhu.libmgmt.db.AboutDB;
import com.dhu.libmgmt.domain.User;

public class UserTabFragment extends TabFragment {
	private String mMode;

	private Button addUserBtn, searchUserBtn;
	private EditText userSearchText;
	private ListView listView;

	public UserTabFragment(String text, String mode, SQLiteDatabase db) {
		mMode = mode;
		super.setDb(db);
		super.setText(text);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.user_ui_content, container,
				false);
		listView = (ListView) fragView.findViewById(R.id.userListView);
		listView.setAdapter(new SimpleAdapter(getActivity()
				.getApplicationContext(), AboutDB.getAllUserList(getDb()),
				R.layout.user_list_item_1,
				new String[] { "_id", "name", "status" }, new int[] {
						R.id.list_user_id, R.id.list_user_name,
						R.id.list_user_status }));
		return fragView;
	}

}
