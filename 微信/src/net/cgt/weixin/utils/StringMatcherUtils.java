/*
 * Copyright 2014 cgt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.cgt.weixin.utils;

public class StringMatcherUtils {

	/**
	 * 韩文字符编码开始？
	 */
	private final static char KOREAN_UNICODE_START = '가';
	/**
	 * 韩文字符编码结束？
	 */
	private final static char KOREAN_UNICODE_END = '힣';
	/**
	 * 韩文字符的单元
	 */
	private final static char KOREAN_UNIT = '까' - '가';
	/**
	 * 韩文的一些字符初始化
	 */
	private final static char[] KOREAN_INITIAL = { 'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ',
			'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ',
			'ㅎ' };

	/**
	 * 字符匹配
	 * 
	 * @param value
	 *            需要keyword匹配的字符串
	 * @param keyword
	 *            #ABCDEFGHIJKLMNOPQRSTUVWXYZ中的一个
	 * @return
	 */
	public static boolean match(String value, String keyword) {
		if (value == null || keyword == null)
			return false;
		if (keyword.length() > value.length())
			return false;

		int i = 0, j = 0;
		do {
			// 如果是韩文字符并且在韩文初始数组里面
			if (isKorean(value.charAt(i)) && isInitialSound(keyword.charAt(j))) {
				if (keyword.charAt(j) == getInitialSound(value.charAt(i))) {
					i++;
					j++;
				} else if (j > 0)
					break;
				else
					i++;
			} else {
				// 逐个字符匹配
				if (keyword.charAt(j) == value.charAt(i)) {
					i++;
					j++;
				} else if (j > 0)
					break;
				else
					i++;
			}
		} while (i < value.length() && j < keyword.length());
		// 如果最后j等于keyword的长度说明匹配成功
		return (j == keyword.length()) ? true : false;
	}

	/**
	 * 判断字符是否在韩文字符编码范围内
	 * 
	 * @param c
	 * @return
	 */
	private static boolean isKorean(char c) {
		if (c >= KOREAN_UNICODE_START && c <= KOREAN_UNICODE_END)
			return true;
		return false;
	}

	/**
	 * 判断是否在韩文字符里面
	 * 
	 * @param c
	 * @return
	 */
	private static boolean isInitialSound(char c) {
		for (char i : KOREAN_INITIAL) {
			if (c == i)
				return true;
		}
		return false;
	}

	/**
	 * 获得韩文初始化字符数组里面的一个字符
	 * 
	 * @param c
	 * @return
	 */
	private static char getInitialSound(char c) {

		if (!isKorean(c)) {
			return c;
		}

		return KOREAN_INITIAL[(c - KOREAN_UNICODE_START) / KOREAN_UNIT];
	}
}
