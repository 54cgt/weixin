package net.cgt.weixin.view.pinyin;

import net.cgt.weixin.domain.User;
import net.cgt.weixin.utils.map.sort.HashList;
import net.cgt.weixin.utils.map.sort.KeySortImpl;
import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * 分类拼音集合
 * 
 * @author lijian
 * @date 2014-11-29
 */
public class AssortPinyinList {

	private HashList<String, User> hashList = new HashList<String, User>(new KeySortImpl<String, User>() {

		@Override
		public String getKey(User user) {
			return getFirstChar(user);
		}

	});

	/**
	 * 获得字符串的首字母 首字符 转汉语拼音
	 * 
	 * @param value
	 * @return
	 */
	public String getFirstChar(User user) {
		// 首字符
		char firstChar = user.getUserAccount().charAt(0);
		// 首字母分类
		String first = null;
		// 是否是非汉字
		// 将首字符转化为汉语拼音字符串数组
		String[] print = PinyinHelper.toHanyuPinyinStringArray(firstChar);

		if (print == null) {
			// 将小写字母改成大写
			if ((firstChar >= 97 && firstChar <= 122)) {//小写字母
				firstChar -= 32;
			}
			if (firstChar >= 65 && firstChar <= 90) {//大写字母
				first = String.valueOf(firstChar);
			} else if (firstChar == 32) {
				first = "↑";
			} else {
				// 认为首字符为数字或者特殊字符
				first = "#";
			}
		} else {
			// 如果是中文 分类大写字母
			first = String.valueOf((char) (print[0].charAt(0) - 32));
		}
		if (first == null) {
			first = "?";
		}
		return first;
	}

	public HashList<String, User> getHashList() {
		return hashList;
	}

}
