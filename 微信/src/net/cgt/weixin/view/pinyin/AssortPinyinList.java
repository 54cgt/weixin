package net.cgt.weixin.view.pinyin;

import net.cgt.weixin.utils.map.sort.HashList;
import net.cgt.weixin.utils.map.sort.KeySort;
import net.sourceforge.pinyin4j.PinyinHelper;

public class AssortPinyinList {

	private HashList<String, String> hashList = new HashList<String, String>(new KeySort<String, String>() {
		public String getKey(String value) {
			return getFirstChar(value);
		}
	});

	//获得字符串的首字母 首字符 转汉语拼音
	public String getFirstChar(String value) {
		// 首字符
		char firstChar = value.charAt(0);
		// 首字母分类
		String first = null;
		// 是否是非汉字
		String[] print = PinyinHelper.toHanyuPinyinStringArray(firstChar);

		if (print == null) {

			// 将小写字母改成大写
			if ((firstChar >= 97 && firstChar <= 122)) {
				firstChar -= 32;
			}
			if (firstChar >= 65 && firstChar <= 90) {
				first = String.valueOf((char) firstChar);
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

	public HashList<String, String> getHashList() {
		return hashList;
	}

}
