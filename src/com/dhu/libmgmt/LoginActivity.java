package com.dhu.libmgmt;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dhu.libmgmt.db.AboutDB;
import com.dhu.libmgmt.db.DataBaseHelper;
import com.dhu.libmgmt.domain.User;

public class LoginActivity extends Activity {

	DataBaseHelper dbh = new DataBaseHelper(this);
	SQLiteDatabase db;

	EditText nametext;
	EditText passtext;
	TextView register;
	Button loginbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		db = dbh.getWritableDatabase();// 创建数据库 如果数据库不存在的话就是这样的的啊
		nametext = (EditText) findViewById(R.id.loginNameTxt);
		nametext.setText("");
		passtext = (EditText) findViewById(R.id.loginPassTxt);
		passtext.setText("");
		register = (TextView) findViewById(R.id.registerBtn);
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent in = new Intent(LoginActivity.this,
						RegisterActivity.class);
				LoginActivity.this.startActivity(in);
			}
		});// 注册新 用户的代码

		loginbtn = (Button) findViewById(R.id.loginBtn);
		loginbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!AboutDB.isRightUser(db, nametext.getText().toString(),
						passtext.getText().toString())) {
					Toast.makeText(getApplication(), "哎哟，登陆失败",
							TRIM_MEMORY_BACKGROUND).show();
					return;
				} // 登录逻辑的处理
				List<User> list = AboutDB.getUserInfo(db, nametext.getText()
						.toString());
				if (list.isEmpty())
					return;
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				intent.putExtra("uid", list.get(0).getId());
				intent.putExtra("status", list.get(0).getStatus());
				LoginActivity.this.startActivity(intent);
			}
		});// 对登录按钮做事件监听处理
	}

}
