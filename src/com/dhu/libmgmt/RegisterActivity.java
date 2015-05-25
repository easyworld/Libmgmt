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
import com.dhu.libmgmt.domain.User;

public class RegisterActivity extends Activity {
	private String name, pass, confirm;
	DataBaseHelper dbh = new DataBaseHelper(this);
	SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		final EditText nametxt = (EditText) findViewById(R.id.regnametxt);
		final EditText passtxt = (EditText) findViewById(R.id.regpasstxt);
		final EditText confirmtxt = (EditText) findViewById(R.id.regpasscon);
		final Button btn = (Button) findViewById(R.id.regbtn);
		final Button btn1 = (Button) findViewById(R.id.backbtn);
		btn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RegisterActivity.this,
						LoginActivity.class);
				startActivity(intent);
			}
		});
		btn.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				name = nametxt.getText().toString();
				pass = passtxt.getText().toString();
				confirm = confirmtxt.getText().toString();
				if (name.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
					Toast.makeText(RegisterActivity.this, "请输入内容",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (!pass.equals(confirm)) {
					Toast.makeText(RegisterActivity.this, "密码不一致",
							Toast.LENGTH_SHORT).show();
					return;
				}
				db = dbh.getWritableDatabase();
				// 判断输入用户是否存在
				if (AboutDB.hasUser(db, name)) {
					Toast.makeText(RegisterActivity.this, "用户" + name + "已经存在",
							Toast.LENGTH_SHORT).show();
					return;
				}
				AboutDB.insertUser(db, name, pass, User.USER);// 添加了新用户
				Toast.makeText(RegisterActivity.this, "注册成功",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(RegisterActivity.this,
						LoginActivity.class);
				startActivity(intent);
			}
		});
	}
}
