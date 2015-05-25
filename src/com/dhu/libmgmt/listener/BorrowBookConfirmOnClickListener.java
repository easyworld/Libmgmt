package com.dhu.libmgmt.listener;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.dhu.libmgmt.db.AboutDB;
import com.dhu.libmgmt.domain.Book;
import com.dhu.libmgmt.fragment.TabFragment;

public class BorrowBookConfirmOnClickListener implements OnClickListener {

	private int uid;
	private int bookid;
	private SQLiteDatabase db;
	private TabFragment fragment;

	public BorrowBookConfirmOnClickListener(int uid, int bookid,
			SQLiteDatabase db, TabFragment fragment) {
		this.uid = uid;
		this.bookid = bookid;
		this.db = db;
		this.fragment = fragment;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// 检查书有没有被借走
		if (AboutDB.isBorrowed(db, bookid)) {
			Toast.makeText(fragment.getActivity(), "已经借走了", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		// 插入订单
		AboutDB.insertOrder(db, uid, bookid);
		// 修改图书状态
		AboutDB.updateBook(db, bookid, Book.BORROWED);
		fragment.refresh();
		return;

	}
}
