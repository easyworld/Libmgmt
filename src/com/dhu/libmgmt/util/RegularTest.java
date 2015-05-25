package com.dhu.libmgmt.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularTest {
	/**
	 * 测试输入字符串是否符合对应正则表达式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean getTest(String str, String reg) {
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(str);
		return m.find();
	}
}
