package com.black.lei.Utils.utilsForPlatForm;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 
 * 全局功能函数类
 *
 */
public final class CommonFunction {

	public static CommonFunction daoInterface = new CommonFunction();

	/**
	 * UUID是由一个十六位的数字组成,表现出来的形式例如 ,550E8400-E29B-11D4-A716-446655440000
	 * 下面就是实现为数据库获取一个唯一的主键id的代码 获得一个UUID
	 * @return String UUID 返回一个字符串形式:550E8400E29B11D4A716446655440000
	 */
	public final String getUUID() {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
	}

	/**
	 * 获得指定数目的UUID
	 * 
	 * @param number int 需要获得的UUID数量
	 * @return String[] UUID数组
	 */
	public final String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] strUUIDArray = new String[number];
		for (int i = 0; i < number; i++) {
			strUUIDArray[i] = getUUID();
		}
		return strUUIDArray;
	}

	/**
	 * 通过毫秒转化为时间字符串
	 * @param milseconds
	 * @return
	 */
	public final String IntToDateString(int milseconds) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// CommonFunction.daoInterface.DebugPrintInfo(sdf.format(Long.parseLong("1431743236000")));
		Long mils = (long) milseconds * 1000;
		String timsString = String.valueOf(mils);
		return sdf.format(Long.parseLong(timsString));
	}

	/**
	 * 将字符串时间转换为长整型
	 * @param dateStr
	 * @return
	 */
	public long DateStrTolong(String dateStr) {
		if (!dateStr.equals("")) {
			return StrToDate(dateStr).getTime() / 1000;
		} else {
			return 0;
		}
	}

	/**
	 * 通过毫秒转化为时间
	 * @param milseconds
	 * @return
	 */
	public Date IntToDate(int milseconds) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long mils = (long) milseconds * 1000;
		String timsString = String.valueOf(mils);
		return StrToDate(sdf.format(Long.parseLong(timsString)));
	}
	
	/**
	 * 毫秒转化时间
	 * @param milseconds
	 * @return
	 */
	public Date longToDate(long milseconds) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return StrToDate(sdf.format(milseconds));
	}

	/**
	 * 日期转换成字符串
	 * @param date
	 * @return str
	 */
	public String DateToStr(Date date) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		return str;
	}

	/**
	 * 字符串转换成日期
	 * @param str
	 * @return date
	 */
	public Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public Date StrtoDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public Date StrToDateV2(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 转换字符串为日期
	 * @param strDate
	 * @param strFormat
	 * @return
	 */
	public Date StrToDateFormat(String strDate, String strFormat) {

		SimpleDateFormat format = new SimpleDateFormat(strFormat);
		Date date = null;
		try {
			date = format.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	// 获取当前sql时间
	public String GetCurSqlDateString() {
		return DateToStr(new Date());
	}

	// 获取当前UTC时间
	public int getCurrentUtcTime() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	public boolean isValidDateTime(String str) {
		boolean convertSuccess = true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			// e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}

	public boolean isValidDate(String str) {
		boolean convertSuccess = true;
		// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// 设置lenient为false.
			// 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			// e.printStackTrace();
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}

	/**
	 * 转换RecordSet为List<Record>
	 * @param rs
	 * @return
	 * @throws SQLException
	 */

	/** */
	/**
	 * 文件重命名
	 * 
	 * @param path 文件目录
	 * @param oldname 原来的文件名
	 * @param newname 新文件名
	 * @return
	 */
	public void renameFile(String path, String oldname, String newname) {
		if (!oldname.equals(newname)) {// 新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile = new File(path + "/" + oldname);
			File newfile = new File(path + "/" + newname);
			if (!oldfile.exists()) {
				return;// 重命名文件不存在
			}
			if (newfile.exists())// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				CommonFunction.daoInterface.DebugPrintInfo(newname + "已经存在！");
			else {
				oldfile.renameTo(newfile);
			}
		} else {
			CommonFunction.daoInterface.DebugPrintInfo("新文件名和旧文件名相同...");
		}
	}

	/**
	 * 转移文件目录不等同于复制文件，复制文件是复制后两个目录都存在该文件，而转移文件目录则是转移后，只有新目录中存在该文件。
	 * @param spath
	 * @param dpath
	 * @param oldname
	 * @param newname
	 */
	public void renameCopyFile(String spath, String dpath, String oldname, String newname) {
		if (!oldname.equals(newname)) {// 新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile = new File(spath + "/" + oldname);
			File newfile = new File(dpath + "/" + newname);
			if (!oldfile.exists()) {
				return;// 重命名文件不存在
			}
			if (newfile.exists())// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				CommonFunction.daoInterface.DebugPrintInfo(newname + "已经存在！");
			else {
				oldfile.renameTo(newfile);
			}
		} else {
			CommonFunction.daoInterface.DebugPrintInfo("新文件名和旧文件名相同...");
		}
	}

	/** */
	/**
	 * 网页请求中文乱码解码函数
	 * 
	 * @param requestStr
	 * 网页请求的得到字符串数据
	 * @return 正确编码的中文
	 */
	public String decodeRequestChineseString(String requestStr) {
		try {
			requestStr = new String(requestStr.getBytes("iso-8859-1"), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return requestStr;
		}
		try {
			return java.net.URLDecoder.decode(requestStr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return requestStr;
		}
	}

	// 转换为%E4%BD%A0形式
	public String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = String.valueOf(c).getBytes("utf-8");
				} catch (Exception ex) {
					DebugPrintInfo(ex.toString());
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 将%E4%BD%A0转换为汉字
	 * @param s
	 * @return
	 */
	public String unescape(String s) {
		StringBuffer sbuf = new StringBuffer();
		int l = s.length();
		int ch = -1;
		int b, sumb = 0;
		for (int i = 0, more = -1; i < l; i++) {
			/* Get next byte b from URL segment s */
			switch (ch = s.charAt(i)) {
			case '%':
				ch = s.charAt(++i);
				int hb = (Character.isDigit((char) ch) ? ch - '0' : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
				ch = s.charAt(++i);
				int lb = (Character.isDigit((char) ch) ? ch - '0' : 10 + Character.toLowerCase((char) ch) - 'a') & 0xF;
				b = (hb << 4) | lb;
				break;
			case '+':
				b = ' ';
				break;
			default:
				b = ch;
			}
			/* Decode byte b as UTF-8, sumb collects incomplete chars */
			if ((b & 0xc0) == 0x80) { // 10xxxxxx (continuation byte)
				sumb = (sumb << 6) | (b & 0x3f); // Add 6 bits to sumb
				if (--more == 0)
					sbuf.append((char) sumb); // Add char to sbuf
			} else if ((b & 0x80) == 0x00) { // 0xxxxxxx (yields 7 bits)
				sbuf.append((char) b); // Store in sbuf
			} else if ((b & 0xe0) == 0xc0) { // 110xxxxx (yields 5 bits)
				sumb = b & 0x1f;
				more = 1; // Expect 1 more byte
			} else if ((b & 0xf0) == 0xe0) { // 1110xxxx (yields 4 bits)
				sumb = b & 0x0f;
				more = 2; // Expect 2 more bytes
			} else if ((b & 0xf8) == 0xf0) { // 11110xxx (yields 3 bits)
				sumb = b & 0x07;
				more = 3; // Expect 3 more bytes
			} else if ((b & 0xfc) == 0xf8) { // 111110xx (yields 2 bits)
				sumb = b & 0x03;
				more = 4; // Expect 4 more bytes
			} else /* if ((b & 0xfe) == 0xfc) */ { // 1111110x (yields 1 bit)
				sumb = b & 0x01;
				more = 5; // Expect 5 more bytes
			}
			/* We don't test if the UTF-8 encoding is well-formed */
		}
		return sbuf.toString();
	}

	// 调试信息输出
	public void DebugPrintInfo(String info) {
		// System.out.println(info);
	}
	
	
	/**
	 * 提取每个汉字的首字母
	 * 
	 * @param str
	 * @return String
	 */
	@SuppressWarnings("unused")
//	public  String getFirstUpperCase(String str) {
//		String convert = "";
//		for (int j = 0; j < str.length(); j ++) {
//			char word = str.charAt(j);
//			// 提取汉字的首字母
//			String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
//			if (pinyinArray != null) {
//				convert += pinyinArray[0].charAt(0);
//			} else {
//				convert += word;
//			}
//			return convert.toUpperCase();
//		}
//		return convert.toUpperCase();
//	}
//
	/**
	 * 将千分位数字转为不含千分位
	 */
	public String numberFormat(String str){
		int b = 0;
		try {
			b = new DecimalFormat().parse(str).intValue();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return String.valueOf(b);
	}

}
