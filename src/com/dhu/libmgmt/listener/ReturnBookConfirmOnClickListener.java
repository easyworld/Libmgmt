package com.dhu.libmgmt.listener;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;

import com.dhu.libmgmt.db.AboutDB;
import com.dhu.libmgmt.domain.Book;
import com.dhu.libmgmt.fragment.TabFragment;

public class ReturnBookConfirmOnClickListener implements OnClickListener {

	private int _id;
	private int bookid;
	private SQLiteDatabase db;
	private TabFragment tabFragment;

	public ReturnBookConfirmOnClickListener(int _id, int bookid,
			SQLiteDatabase db, TabFragment tabFragment) {
		this._id = _id;
		this.bookid = bookid;
		this.db = db;
		this.tabFragment = tabFragment;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

		// 插入订单
		AboutDB.deleteOrder(db, _id);
		// 修改图书状态
		AboutDB.updateBook(db, bookid, Book.UNBORROWED);
		tabFragment.refresh();
		return;

	}
}
