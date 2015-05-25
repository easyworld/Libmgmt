package com.dhu.libmgmt.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dhu.libmgmt.domain.Book;
import com.dhu.libmgmt.domain.User;

public class AboutDB {
	/**
	 * _id<br>
	 * name<br>
	 * author<br>
	 * publish<br>
	 * status<br>
	 */
	private final static String CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ " book(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ " name VARCHAR,author VARCHAR,publish VARCHAR,status VARCHAR)";
	/**
	 * _id<br>
	 * name<br>
	 * pass<br>
	 * status<br>
	 */
	private final static String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ " user(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ " name VARCHAR,pass VARCHAR,status INTEGER)";
	/**
	 * _id<br>
	 * bookid<br>
	 * userid<br>
	 */
	private final static String CREATE_ORDER_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ " bookOrder(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ " bookid INTEGER,userid INTEGER)";
	private final static String INSERT_DEFAULT_BOOKS[] = {
			"insert into book(name,author,publish,status) values('C++编程基础','谭浩强','人民邮电出版社','未借出');",
			"insert into book(name,author,publish,status) values('JAVA编程思想','唐纳德','人民邮电出版社','未借出');" };

	private static final String SELECT_USER_BY_USERNAME = "select _id,name,pass,status FROM user WHERE name = ?";
	private static final String VALIDATE_PASSWORD_SQL = "select _id,name,pass,status from user where name=? and pass=? ";
	private static final String SELECT_ALL_BOOK = "select _id,name,author,publish,status from book";
	private static final String SELECT_ALL_USER = "select _id,name,pass,status from user";
	private static final String SELECT_ALL_ORDER = "select c._id as _id, a.name as username, b._id as bookid,b.name as bookname "
			+ " from user a,book b,bookOrder c where a._id = c.userid and b._id = c.bookid;";
	private static final String SELECT_ORDER_BY_ID = "select c._id as _id, a.name as username, b._id as bookid,b.name as bookname "
			+ " from user a,book b,bookOrder c where a._id = c.userid and b._id = c.bookid and a._id = ?";
	private static final String SELECT_BORROWED_BOOK_BY_ID = "select * from book where status=? AND _id = ?";
	private static final String SELECT_BOOK_BY_BOOKNAME = "select * from book where name = ?";
	static Cursor cu;

	public static void init(SQLiteDatabase db) {
		Log.v("database", "Creating table");
		db.execSQL(CREATE_BOOK_TABLE);
		db.execSQL(CREATE_USER_TABLE);
		db.execSQL(CREATE_ORDER_TABLE);
		ContentValues cv = new ContentValues();
		cv.put("name", "admin");
		cv.put("pass", "admin");
		cv.put("status", User.ADMIN);
		db.insert("user", null, cv);
		cv.clear();
		for (String str : INSERT_DEFAULT_BOOKS)
			db.execSQL(str);
	}

	public static boolean isRightUser(SQLiteDatabase db, String name,
			String pass) {
		cu = db.rawQuery(VALIDATE_PASSWORD_SQL, new String[] { name, pass });
		List<User> list = new ArrayList<User>();
		int idindex = 0, nameindex = 0, passindex = 0, statusindex = 0;
		if (cu.getCount() > 0) {
			idindex = cu.getColumnIndex("_id");
			nameindex = cu.getColumnIndex("name");
			passindex = cu.getColumnIndex("pass");
			statusindex = cu.getColumnIndex("status");

		} else
			return false;
		while (cu.moveToNext()) {
			list.add(new User(cu.getInt(idindex), cu.getString(nameindex), cu
					.getString(passindex), cu.getString(statusindex)));

		}
		return list.size() > 0 ? true : false;
	}

	public static List<User> getUserInfo(SQLiteDatabase db, String name) {
		cu = db.rawQuery(SELECT_USER_BY_USERNAME, new String[] { name });
		List<User> list = new ArrayList<User>();
		int idindex = 0, nameindex = 0, passindex = 0, statusindex = 0;
		if (cu.getCount() > 0) {
			idindex = cu.getColumnIndex("_id");
			nameindex = cu.getColumnIndex("name");
			passindex = cu.getColumnIndex("pass");
			statusindex = cu.getColumnIndex("status");

		} else
			return list;
		while (cu.moveToNext()) {
			list.add(new User(cu.getInt(idindex), cu.getString(nameindex), cu
					.getString(passindex), cu.getString(statusindex)));
		}
		return list;
	}

	public static List<User> getAllUser(SQLiteDatabase db) {
		List<User> listItem = new ArrayList<User>();
		cu = db.rawQuery(SELECT_ALL_USER, null);
		int idindex = 0, nameindex = 0, passindex = 0, statusindex = 0;
		if (cu.getCount() > 0) {
			idindex = cu.getColumnIndex("_id");
			nameindex = cu.getColumnIndex("name");
			passindex = cu.getColumnIndex("pass");
			statusindex = cu.getColumnIndex("status");
		} else
			return listItem;
		while (cu.moveToNext()) {
			listItem.add(new User(Integer.parseInt(cu.getString(idindex)), cu
					.getString(nameindex), cu.getString(passindex), cu
					.getString(statusindex)));
		}
		return listItem;
	}

	public static List<Map<String, Object>> getAllUserList(SQLiteDatabase db) {
		List<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		cu = db.rawQuery(SELECT_ALL_USER, null);
		int idindex = 0, nameindex = 0, passindex = 0, statusindex = 0;
		if (cu.getCount() > 0) {
			idindex = cu.getColumnIndex("_id");
			nameindex = cu.getColumnIndex("name");
			passindex = cu.getColumnIndex("pass");
			statusindex = cu.getColumnIndex("status");
		} else
			return listItem;
		while (cu.moveToNext()) {
			map = new TreeMap<String, Object>();
			map.put("_id", Integer.parseInt(cu.getString(idindex)));
			map.put("name", cu.getString(nameindex));
			map.put("pass", cu.getString(passindex));
			map.put("status", cu.getString(statusindex));
			listItem.add(map);
		}
		return listItem;
	}

	public static List<Book> getAllBook(SQLiteDatabase db) {
		List<Book> listItem = new ArrayList<Book>();
		cu = db.rawQuery(SELECT_ALL_BOOK, null);
		int idindex = 0, nameindex = 0, authorindex = 0, publishindex = 0, statusindex = 0;
		if (cu.getCount() > 0) {
			idindex = cu.getColumnIndex("_id");
			nameindex = cu.getColumnIndex("name");
			authorindex = cu.getColumnIndex("author");
			publishindex = cu.getColumnIndex("publish");
			statusindex = cu.getColumnIndex("status");
		} else
			return listItem;
		while (cu.moveToNext()) {
			listItem.add(new Book(Integer.parseInt(cu.getString(idindex)), cu
					.getString(nameindex), cu.getString(authorindex), cu
					.getString(publishindex), cu.getString(statusindex)));
		}
		return listItem;

	}

	public static List<Map<String, Object>> getAllBookList(SQLiteDatabase db) {
		List<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		cu = db.rawQuery(SELECT_ALL_BOOK, null);
		int idindex = 0, nameindex = 0, authorindex = 0, publishindex = 0, statusindex = 0;
		if (cu.getCount() > 0) {
			idindex = cu.getColumnIndex("_id");
			nameindex = cu.getColumnIndex("name");
			authorindex = cu.getColumnIndex("author");
			publishindex = cu.getColumnIndex("publish");
			statusindex = cu.getColumnIndex("status");
		} else
			return listItem;
		while (cu.moveToNext()) {
			map = new TreeMap<String, Object>();
			map.put("_id", Integer.parseInt(cu.getString(idindex)));
			map.put("name", cu.getString(nameindex));
			map.put("author", cu.getString(authorindex));
			map.put("publish", cu.getString(publishindex));
			map.put("status", cu.getString(statusindex));
			listItem.add(map);
		}
		return listItem;

	}

	public static boolean hasUser(SQLiteDatabase db, String name) {
		cu = db.rawQuery(SELECT_USER_BY_USERNAME, new String[] { name });
		return cu.getCount() > 0;
	}

	public static void insertUser(SQLiteDatabase db, String name, String pass,
			String status) {
		String sql = "insert  into user(name,pass,status)values(?,?,?)";
		db.execSQL(sql, new String[] { name, pass, User.USER });
	}

	public static boolean isBorrowed(SQLiteDatabase db, int bookid) {
		cu = db.rawQuery(SELECT_BORROWED_BOOK_BY_ID, new String[] {
				Book.BORROWED, String.valueOf(bookid) });
		return cu.getCount() > 0;
	}

	public static void insertOrder(SQLiteDatabase db, int uid, int bookid) {
		ContentValues cv = new ContentValues();
		cv.put("bookid", bookid);
		cv.put("userid", uid);
		db.insert("bookOrder", null, cv);

	}

	public static List<? extends Map<String, ?>> getAllOrderList(
			SQLiteDatabase db) {
		List<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		cu = db.rawQuery(SELECT_ALL_ORDER, null);
		int idindex = 0, usernameindex = 0, bookidindex = 0, booknameindex = 0;
		if (cu.getCount() > 0) {
			idindex = cu.getColumnIndex("_id");
			usernameindex = cu.getColumnIndex("username");
			bookidindex = cu.getColumnIndex("bookid");
			booknameindex = cu.getColumnIndex("bookname");
		} else
			return listItem;
		while (cu.moveToNext()) {
			map = new TreeMap<String, Object>();
			map.put("_id", cu.getInt(idindex));
			map.put("username", cu.getString(usernameindex));
			map.put("bookid", cu.getString(bookidindex));
			map.put("bookname", cu.getString(booknameindex));

			listItem.add(map);
		}
		return listItem;
	}

	public static void updateBook(SQLiteDatabase db, int bookid, String bORROWED) {
		ContentValues cv = new ContentValues();
		cv.put("status", bORROWED);
		db.update("book", cv, "_id = ?",
				new String[] { String.valueOf(bookid) });

	}

	public static void deleteOrder(SQLiteDatabase db, int _id) {
		db.delete("bookOrder", "_id = ?", new String[] { String.valueOf(_id) });
	}

	public static void deleteOrderByBookId(SQLiteDatabase db, int bookid) {
		db.delete("bookOrder", "bookid = ?",
				new String[] { String.valueOf(bookid) });
	}

	public static void deleteBook(SQLiteDatabase db, int _id) {
		db.delete("book", "_id = ?", new String[] { String.valueOf(_id) });
	}

	public static List<? extends Map<String, ?>> getOrderListById(
			SQLiteDatabase db, int userid) {
		List<Map<String, Object>> listItem = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		cu = db.rawQuery(SELECT_ORDER_BY_ID,
				new String[] { String.valueOf(userid) });
		int idindex = 0, usernameindex = 0, bookidindex = 0, booknameindex = 0;
		if (cu.getCount() > 0) {
			idindex = cu.getColumnIndex("_id");
			usernameindex = cu.getColumnIndex("username");
			bookidindex = cu.getColumnIndex("bookid");
			booknameindex = cu.getColumnIndex("bookname");
		} else
			return listItem;
		while (cu.moveToNext()) {
			map = new TreeMap<String, Object>();
			map.put("_id", cu.getInt(idindex));
			map.put("username", cu.getString(usernameindex));
			map.put("bookid", cu.getString(bookidindex));
			map.put("bookname", cu.getString(booknameindex));

			listItem.add(map);
		}
		return listItem;
	}

	public static boolean hasBook(SQLiteDatabase db, String name) {
		cu = db.rawQuery(SELECT_BOOK_BY_BOOKNAME, new String[] { name });
		return cu.getCount() > 0;
	}

	public static void insertBook(SQLiteDatabase db, String name,
			String author, String publish, String status) {
		ContentValues cv = new ContentValues();
		cv.put("name", name);
		cv.put("author", author);
		cv.put("publish", publish);
		cv.put("status", status);
		db.insert("book", null, cv);

	}
}