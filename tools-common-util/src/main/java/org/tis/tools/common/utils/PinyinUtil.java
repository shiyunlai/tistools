package org.tis.tools.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {

	/**
	 * 将字符串input中的汉子转为对应的汉语拼音输出（只转换其中汉子内容，且所有字母被转为小写）
	 * 
	 * @param input
	 * @return
	 */
	public static String convert(String input) {
		StringBuilder pinyin = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
			defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
			char c = input.charAt(i);
			String[] pinyinArray = null;
			try {
				pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);
			} catch (BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
			if (pinyinArray != null) {
				pinyin.append(pinyinArray[0]);
			} else if (c != ' ') {
				pinyin.append(input.charAt(i));
			}
		}

		String a = pinyin.toString().trim().toLowerCase();
		String b = "";
		for (int i = 0; i < a.length(); i++) {
			if (Character.isLetter(a.charAt(i)) || Character.isDigit(a.charAt(i))) {
				b = b + a.charAt(i);
			}
		}
		return b;
	}

}
