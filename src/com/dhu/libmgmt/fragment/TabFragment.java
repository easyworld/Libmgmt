package com.dhu.libmgmt.fragment;

import android.app.Fragment;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class TabFragment extends Fragment {
	private String mText;
	private SQLiteDatabase db;
	private SimpleAdapter adapter;

	public SimpleAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(SimpleAdapter adapter) {
		this.adapter = adapter;
	}

	public SQLiteDatabase getDb() {
		return db;
	}

	public void setDb(SQLiteDatabase db) {
		this.db = db;
	}

	public void setText(String text) {
		mText = text;
	}

	public String getText() {
		return mText;
	}

	public void refresh() {
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}
}
