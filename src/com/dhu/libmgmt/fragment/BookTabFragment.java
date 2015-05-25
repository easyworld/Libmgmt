package com.dhu.libmgmt.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.dhu.libmgmt.AddBookActivity;
import com.dhu.libmgmt.R;
import com.dhu.libmgmt.db.AboutDB;
import com.dhu.libmgmt.domain.User;
import com.dhu.libmgmt.listener.BorrowBookConfirmOnClickListener;
import com.dhu.libmgmt.listener.DeleteBookConfirmOnClickListener;

public class BookTabFragment extends TabFragment {
	private String userStatus;
	private int uid;
	private Button addBookBtn;
	private ListView bookListView;
	private TabFragment tabFragment = this;

	public BookTabFragment(String text, int uid, String userStatus,
			SQLiteDatabase db) {
		this.uid = uid;
		this.userStatus = userStatus;
		super.setText(text);
		super.setDb(db);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onContextItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View fragView = inflater.inflate(R.layout.book_ui_content, container,
				false);

		addBookBtn = (Button) fragView.findViewById(R.id.addBookBtn);
		if (User.USER.equals(userStatus)) {
			addBookBtn.setVisibility(android.view.View.GONE);
		}
		addBookBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), AddBookActivity.class);
				intent.putExtra("uid", uid);
				intent.putExtra("status", userStatus);
				startActivity(intent);
			}
		});
		bookListView = (ListView) fragView.findViewById(R.id.bookListView);
		this.setAdapter(new SimpleAdapter(
				getActivity().getApplicationContext(), AboutDB
						.getAllBookList(getDb()), R.layout.book_list_item_1,
				new String[] { "_id", "name", "author", "publish", "status" },
				new int[] { R.id.list_book_id, R.id.list_book_name,
						R.id.list_book_author, R.id.list_book_publish,
						R.id.list_book_status }));
		bookListView.setAdapter(getAdapter());
		bookListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						ListView templist = (ListView) parent;
						View mView = templist.getChildAt(position);
						TextView mTextView1 = (TextView) mView
								.findViewById(R.id.list_book_id);
						TextView mTextView2 = (TextView) mView
								.findViewById(R.id.list_book_name);
						if (User.USER.equals(userStatus)) {
							new AlertDialog.Builder(getActivity())
									.setTitle(
											"要借"
													+ mTextView2.getText()
															.toString() + "吗")
									.setIcon(android.R.drawable.ic_dialog_info)
									.setPositiveButton(
											"确定",
											new BorrowBookConfirmOnClickListener(
													uid,
													Integer.parseInt(mTextView1
															.getText()
															.toString()),
													getDb(), tabFragment))
									.setNegativeButton("取消", null).show();
						} else if (User.ADMIN.equals(userStatus)) {
							new AlertDialog.Builder(getActivity())
									.setTitle(
											"要删除"
													+ mTextView2.getText()
															.toString()
													+ "吗?该图书对应的借阅记录也会删除")
									.setIcon(android.R.drawable.ic_dialog_info)
									.setPositiveButton(
											"确定",
											new DeleteBookConfirmOnClickListener(
													Integer.parseInt(mTextView1
															.getText()
															.toString()),
													getDb(), tabFragment))
									.setNegativeButton("取消", null).show();
						}

					}
				});
		return fragView;
	}
}
