package net.cgt.weixin.domain.pinyin;

import java.util.Comparator;

import net.cgt.weixin.domain.User;
import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * 汉字排序
 * 
 * @author lijian
 * @date 2014-11-29
 * @param <T>
 */
public class LanguageComparator_CN<T> implements Comparator<T> {

	@Override
	public int compare(T lhs, T rhs) {

		if (lhs instanceof User && rhs instanceof User) {
			return compareUser(lhs, rhs);
		} else if (lhs instanceof String && rhs instanceof String) {
			return compareStr(lhs, rhs);
		} else {
			return 0;
		}

	}

	/**
	 * 字符串排序
	 * 
	 * @param lhs
	 * @param rhs
	 * @return
	 */
	private int compareStr(T lhs, T rhs) {
		String ostr1 = (String) lhs;
		String ostr2 = (String) rhs;

		for (int i = 0; i < ostr1.length() && i < ostr2.length(); i++) {

			int codePoint1 = ostr1.charAt(i);
			int codePoint2 = ostr2.charAt(i);

			if (codePoint1 == 35) {//处理将#放置到最后
				codePoint1 = codePoint1 + 100;
			}

			if (codePoint2 == 35) {//处理将#放置到最后
				codePoint2 = codePoint2 + 100;
			}

			//确定指定的字符（Unicode代码点）是否是在增补字符范围内
			if (Character.isSupplementaryCodePoint(codePoint1) || Character.isSupplementaryCodePoint(codePoint2)) {
				i++;
			}
			if (codePoint1 != codePoint2) {
				if (Character.isSupplementaryCodePoint(codePoint1) || Character.isSupplementaryCodePoint(codePoint2)) {
					return codePoint1 - codePoint2;
				}
				String pinyin1 = pinyin((char) codePoint1);
				String pinyin2 = pinyin((char) codePoint2);

				if (pinyin1 != null && pinyin2 != null) { // 两个字符都是汉字
					if (!pinyin1.equals(pinyin2)) {
						return pinyin1.compareTo(pinyin2);
					}
				} else {
					return codePoint1 - codePoint2;
				}
			}
		}
		return ostr1.length() - ostr2.length();
	}

	/**
	 * 用户domain排序
	 * 
	 * @param lhs
	 * @param rhs
	 * @return
	 */
	private int compareUser(T lhs, T rhs) {
		User user1 = (User) lhs;
		User user2 = (User) rhs;

		for (int i = 0; i < user1.getUserAccount().length() && i < user2.getUserAccount().length(); i++) {

			int codePoint1 = user1.getUserAccount().charAt(i);
			int codePoint2 = user2.getUserAccount().charAt(i);

			if (codePoint1 == 35) {//处理将#放置到最后
				codePoint1 = codePoint1 + 100;
			}

			if (codePoint2 == 35) {//处理将#放置到最后
				codePoint2 = codePoint2 + 100;
			}

			if (Character.isSupplementaryCodePoint(codePoint1) || Character.isSupplementaryCodePoint(codePoint2)) {
				i++;
			}
			if (codePoint1 != codePoint2) {
				if (Character.isSupplementaryCodePoint(codePoint1) || Character.isSupplementaryCodePoint(codePoint2)) {
					return codePoint1 - codePoint2;
				}
				String pinyin1 = pinyin((char) codePoint1);
				String pinyin2 = pinyin((char) codePoint2);

				if (pinyin1 != null && pinyin2 != null) { // 两个字符都是汉字
					if (!pinyin1.equals(pinyin2)) {
						return pinyin1.compareTo(pinyin2);
					}
				} else {
					return codePoint1 - codePoint2;
				}
			}
		}
		return user1.getUserAccount().length() - user2.getUserAccount().length();
	}

	// 获得汉字拼音的首字符
	private String pinyin(char c) {
		String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c);
		if (pinyins == null) {
			return null;
		}
		return pinyins[0];
	}
}
