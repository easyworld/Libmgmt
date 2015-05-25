package com.dhu.libmgmt.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	public final static String dbname = "bookandyes";

	public DataBaseHelper(Context context) {
		super(context, dbname, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		AboutDB.init(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
