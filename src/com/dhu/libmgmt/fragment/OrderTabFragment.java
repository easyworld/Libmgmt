package com.dhu.libmgmt.fragment;

import android.app.AlertDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.dhu.libmgmt.R;
import com.dhu.libmgmt.db.AboutDB;
import com.dhu.libmgmt.domain.User;
import com.dhu.libmgmt.listener.ReturnBookConfirmOnClickListener;

public class OrderTabFragment extends TabFragment {
	private String mMode;

	private Button addBookBtn, searchBookBtn;
	private EditText bookSearchText;
	private ListView listView;
	private TabFragment tabFragment = this;
	private int userid;

	public OrderTabFragment(String text, int userid, String mode,
			SQLiteDatabase db) {
		mMode = mode;
		super.setText(text);
		setDb(db);
		this.userid = userid;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.order_ui_content, container,
				false);
		listView = (ListView) fragView.findViewById(R.id.orderListView);
		if (User.ADMIN.equals(mMode))
			setAdapter(new SimpleAdapter(getActivity().getApplicationContext(),
					AboutDB.getAllOrderList(getDb()),
					R.layout.order_list_item_1, new String[] { "_id",
							"username", "bookname", "bookid" }, new int[] {
							R.id.list_order_id, R.id.list_order_username,
							R.id.list_order_bookname, R.id.list_order_book_id }));
		else if (User.USER.equals(mMode)) {
			setAdapter(new SimpleAdapter(getActivity().getApplicationContext(),
					AboutDB.getOrderListById(getDb(), userid),
					R.layout.order_list_item_1, new String[] { "_id",
							"username", "bookname", "bookid" }, new int[] {
							R.id.list_order_id, R.id.list_order_username,
							R.id.list_order_bookname, R.id.list_order_book_id }));
		}
		listView.setAdapter(getAdapter());
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ListView templist = (ListView) parent;
				View mView = templist.getChildAt(position);
				TextView mTextView1 = (TextView) mView
						.findViewById(R.id.list_order_id);
				TextView mTextView2 = (TextView) mView
						.findViewById(R.id.list_order_book_id);
				TextView mTextView3 = (TextView) mView
						.findViewById(R.id.list_order_bookname);
				new AlertDialog.Builder(getActivity())
						.setTitle("要还" + mTextView3.getText().toString() + "吗")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton(
								"确定",
								new ReturnBookConfirmOnClickListener(Integer
										.parseInt(mTextView1.getText()
												.toString()), Integer
										.parseInt(mTextView2.getText()
												.toString()), getDb(),
										tabFragment))
						.setNegativeButton("取消", null).show();
			}
		});
		return fragView;
	}
}
