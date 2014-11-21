package net.cgt.weixin.utils;

import android.content.Context;

//dip简称dp 屏幕像素 和 屏幕宽高的一个比例值
public class DensityUtil {
  /** 
   * 根据手机的分辨率从 dip 的单位 转成为 px(像素) 
   */  
  public static int dip2px(Context context, float dpValue) {  
      final float scale = context.getResources().getDisplayMetrics().density;  
      return (int) (dpValue * scale + 0.5f);  
  }  

  /** 
   * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
   */  
  public static int px2dip(Context context, float pxValue) {  
      final float scale = context.getResources().getDisplayMetrics().density;  
      return (int) (pxValue / scale + 0.5f);  
  }  
}
