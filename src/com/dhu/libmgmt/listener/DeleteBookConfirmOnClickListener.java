package com.dhu.libmgmt.listener;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.sqlite.SQLiteDatabase;

import com.dhu.libmgmt.db.AboutDB;
import com.dhu.libmgmt.fragment.TabFragment;

public class DeleteBookConfirmOnClickListener implements OnClickListener {

	private int bookid;
	private SQLiteDatabase db;
	private TabFragment fragment;

	public DeleteBookConfirmOnClickListener(int bookid, SQLiteDatabase db,
			TabFragment fragment) {
		this.bookid = bookid;
		this.db = db;
		this.fragment = fragment;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// 删除图书
		AboutDB.deleteBook(db, bookid);
		// 删除图书对应的订单
		AboutDB.deleteOrderByBookId(db, bookid);
		fragment.refresh();
		return;

	}
}
