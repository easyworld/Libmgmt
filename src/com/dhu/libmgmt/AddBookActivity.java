package com.dhu.libmgmt;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dhu.libmgmt.db.AboutDB;
import com.dhu.libmgmt.db.DataBaseHelper;
import com.dhu.libmgmt.domain.Book;

public class AddBookActivity extends Activity {
	private String name, author, publish;
	DataBaseHelper dbh = new DataBaseHelper(this);
	SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addbook);

		final EditText nametxt = (EditText) findViewById(R.id.addBookNameTxt);
		final EditText authortxt = (EditText) findViewById(R.id.addBookAuthorTxt);
		final EditText publishtxt = (EditText) findViewById(R.id.addBookPubTxt);
		final Button okbtn = (Button) findViewById(R.id.addBookOkBtn);
		final Button backbtn = (Button) findViewById(R.id.addBookBackBtn);
		final Bundle b = this.getIntent().getExtras();

		backbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(AddBookActivity.this,
						MainActivity.class);
				intent.putExtras(b);
				startActivity(intent);
			}
		});
		okbtn.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				name = nametxt.getText().toString();
				author = authortxt.getText().toString();
				publish = publishtxt.getText().toString();
				if (name.isEmpty() || author.isEmpty() || publish.isEmpty()) {
					Toast.makeText(AddBookActivity.this, "请输入内容",
							Toast.LENGTH_SHORT).show();
					return;
				}
				db = dbh.getWritableDatabase();
				// 判断输入书是否存在
				if (AboutDB.hasBook(db, name)) {
					Toast.makeText(AddBookActivity.this, name + "已经存在",
							Toast.LENGTH_SHORT).show();
					return;
				}
				AboutDB.insertBook(db, name, author, publish, Book.UNBORROWED);// 添加了书目
				Toast.makeText(AddBookActivity.this, "添加成功", Toast.LENGTH_SHORT)
						.show();
				Intent intent = new Intent(AddBookActivity.this,
						MainActivity.class);
				intent.putExtras(b);
				startActivity(intent);
			}
		});
	}
}
