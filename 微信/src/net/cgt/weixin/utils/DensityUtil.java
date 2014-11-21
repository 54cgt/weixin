package net.cgt.weixin.utils;

import android.content.Context;

//dip���dp ��Ļ���� �� ��Ļ��ߵ�һ������ֵ
public class DensityUtil {
	/** 
	 * ����ֻ�ķֱ��ʴ� dip �ĵ�λ ת��Ϊ px(����) 
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/** 
	 * ����ֻ�ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp 
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
