package net.cgt.weixin.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.cgt.weixin.app.App;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * 全局工具类
 * 
 * @author lijian
 * @date 2015-01-03 12:51:27
 */
public class AppUtil {

	private static final String LOGTAG = LogUtil.makeLogTag(AppUtil.class);

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * 
	 * @param context 上下文
	 * @param dpValue 单位dip的数据
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = App.getContext().getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * 
	 * @param context 上下文
	 * @param dpValue 单位dip的数据
	 * @return
	 */
	public static float dip2px(float dpValue) {
		final float scale = App.getContext().getResources().getDisplayMetrics().density;
		return (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 * 
	 * @param context 上下文
	 * @param dpValue 单位dip的数据
	 * @return
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = App.getContext().getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 * 
	 * @param context 上下文
	 * @param dpValue 单位dip的数据
	 * @return
	 */
	public static float px2dip(float pxValue) {
		final float scale = App.getContext().getResources().getDisplayMetrics().density;
		return (pxValue / scale + 0.5f);
	}

	/**
	 * 获取屏幕的宽度
	 * 
	 * @deprecated
	 * @param context
	 * @return
	 */
	public static int getWidth(Context context) {
		DisplayMetrics dm = App.getContext().getApplicationContext().getResources().getDisplayMetrics();
		return dm.widthPixels;
	}

	/**
	 * 获取屏幕的宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getWidth() {
		DisplayMetrics dm = App.getContext().getApplicationContext().getResources().getDisplayMetrics();
		return dm.widthPixels;
	}

	/**
	 * 获取屏幕的高度
	 * 
	 * @deprecated
	 * @param context
	 * @return
	 */
	public static int getHeight(Context context) {
		DisplayMetrics dm = App.getContext().getApplicationContext().getResources().getDisplayMetrics();
		return dm.heightPixels;
	}

	/**
	 * 获取屏幕的高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getHeight() {
		DisplayMetrics dm = App.getContext().getApplicationContext().getResources().getDisplayMetrics();
		return dm.heightPixels;
	}

	/**
	 * Bitmap -> byte
	 * 
	 * @param bitmap
	 * @return
	 */
	public static byte[] bitmapToByte(Bitmap bitmap) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			bitmap.recycle();
			bitmap = null;
			byte[] array = out.toByteArray();
			return array;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Bitmap -> byte
	 * 
	 * @param bitmap
	 * @return
	 */
	public static byte[] drawableToByte(Drawable drawable) {
		Bitmap bitmap = drawableToBitmap(drawable);
		return bitmapToByte(bitmap);
	}

	/**
	 * Drawable -> bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap bm = bd.getBitmap();
		return bm;
	}

	/**
	 * byte[] -> Bitmap
	 * 
	 * @param bytes
	 * @return
	 */
	public static Bitmap byteToBitmap(byte[] bytes) {
		Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
		return bitmap;
	}

	/**
	 * bitmap -> Drawable
	 * 
	 * @param bm
	 * @return
	 */
	public static BitmapDrawable BitmapToDrawable(Bitmap bitmap) {
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		return bd;
	}

	/**
	 * byte[] -> Drawable
	 * 
	 * @param bytes
	 * @return
	 */
	public static BitmapDrawable byteToDrawable(byte[] bytes) {
		Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		bitmap.recycle();
		bitmap = null;
		return bd;
	}

	/**
	 * 获得圆角图片的方法
	 * 
	 * @param bitmap
	 * @param roundPx The x-radius of the oval used to round the corners
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		if (bitmap == null)
			return null;
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 包大小单位处理
	 * 
	 * @param length
	 * @return
	 */
	public static String formatFileSize(long length) {
		String result = null;
		int sub_string = 0;
		if (length >= 1073741824) {
			sub_string = String.valueOf((float) length / 1073741824).indexOf(".");
			result = ((float) length / 1073741824 + "000").substring(0, sub_string + 3) + "GB";
		} else if (length >= 1048576) {
			sub_string = String.valueOf((float) length / 1048576).indexOf(".");
			result = ((float) length / 1048576 + "000").substring(0, sub_string + 3) + "MB";
		} else if (length >= 1024) {
			sub_string = String.valueOf((float) length / 1024).indexOf(".");
			result = ((float) length / 1024 + "000").substring(0, sub_string + 3) + "KB";
		} else if (length < 1024)
			result = Long.toString(length) + "B";
		return result;
	}

	/**
	 * 获取百分比
	 * 
	 * @param f1
	 * @param f2
	 * @return
	 */
	public static String getPercentage(float f1, float f2) {
		if (f2 == 0) {
			return 0.0 + "%";
		} else {
			String percent = String.valueOf(f1 / f2 * 100) + "000";
			percent = percent.substring(0, percent.indexOf(".") + 3);
			return percent + "%";
		}
	}

	/**
	 * 获取下载进度
	 * 
	 * @param downloadSize
	 * @param totalSize
	 * @return
	 */
	public static int getProgress(int downloadSize, int totalSize) {
		if (totalSize == 0)
			return 0;
		else
			return downloadSize * 100 / totalSize;
	}

	/**
	 * 获取rating
	 * 
	 * @param rate
	 * @return
	 */
	public static float getRating(String rate) {
		if (rate != null) {
			if (rate.length() == 2) {
				return Float.valueOf(rate) / 10;
			} else {
				return Float.valueOf(rate);
			}
		}
		return 0;
	}

	/**
	 * 渠道号
	 * 
	 * @param context
	 * @return
	 */
	public static String getChannelNum(Context context) {
		Resources resources = context.getResources();
		try {
			Class<?> className = Class.forName((new StringBuilder(String.valueOf(context.getPackageName()))).append(".R$raw").toString());
			Field field = className.getField("parent");
			Integer result = (Integer) field.get(className);
			InputStream num = resources.openRawResource(result);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int i = 1;
			try {
				while ((i = num.read()) != -1) {
					baos.write(i);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				resources = null;
				if (num != null) {
					try {
						num.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return new String(baos.toByteArray());
		} catch (Exception e) {
		}
		return "0";
	}

	/**
	 * 格式化时间
	 * 
	 * @param time
	 * @return
	 */
	public static String formatData(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.format(new Date(time));
	}

	/**
	 * 格式化时间
	 * 
	 * @param time
	 * @return
	 */
	public static String formatData(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.format(new Date(Long.parseLong(time)));
	}

	/**
	 * 格式化时间<br>
	 * 时间格式：(2014年11月21日 中午12:04)<br>
	 * (2014年11月21日 晚上19:25)<br>
	 * (2014年12月7日 晚上22:55)<br>
	 * (昨天 早上11:09)<br>
	 * (昨天 下午17:02)<br>
	 * (昨天 晚上18:19)<br>
	 * (中午12:17)<br>
	 * 
	 * @param time
	 * @return
	 */
	public static String formatTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		return format.format(time);
	}

	/**
	 * 将金额格式为元
	 * 
	 * @param money
	 * @return
	 */
	public static BigDecimal formatToYuan(String money) throws NumberFormatException {
		if (money.equals("")) {
			return new BigDecimal(0);
		}
		long m = Long.parseLong(money);
		return BigDecimal.valueOf(m);
	}

	/**
	 * 格式化金额，保留两位小数
	 * 
	 * @param money
	 * @return
	 */
	public static String formatToYuanLXS(String money) {
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(formatToYuan(money));
	}

	/**
	 * 格式化金额，保留一位小数
	 * 
	 * @param money
	 * @return
	 */
	public static String formatToYuanYXS(String money) {
		DecimalFormat df = new DecimalFormat("#0.0");
		return df.format(formatToYuan(money));
	}

	/**
	 * 格式化金额，保留两位小数
	 * 
	 * @param money
	 * @return
	 */
	public static String formatToYuanLXS(BigDecimal money) {
		Log.e(LOGTAG, "formatToYuanLXS>>" + money);
		if (money == null) {
			return "加载中";
		}
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(money) + "元";
	}

	/**
	 * 格式化金额，保留两位小数
	 * 
	 * @param money
	 * @return
	 */
	public static String formatToYuanLXS(float money) {
		DecimalFormat df = new DecimalFormat("#0.00");
		return df.format(money);
	}

	/**
	 * 验证输入的邮箱格式是否符合
	 * 
	 * @param email
	 * @return 是否合法
	 */
	public static boolean emailFormat(String email) {
		boolean tag = true;
		final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}

	public static String getFormatCountTimeA(long time) {
		if (time <= 0) {
			return "加载中";
		}

		SimpleDateFormat format1 = new SimpleDateFormat("mm:ss");
		String last_time = "";
		int h = (int) (time / 1000 / 60 / 60);
		if (h > 0) {
			last_time = h + ":" + format1.format(time);
		} else {
			last_time = format1.format(time);
		}

		return last_time;
	}

	/**
	 * 返回时分秒格式 10时03分47秒
	 * 
	 * @param time
	 * @return
	 */
	public static String getFormatCountTime(long time) {
		if (time <= 0) {
			return "加载中";
		}

		StringBuilder sb = new StringBuilder();

		long days = time / (1000 * 60 * 60 * 24);
		long hours = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (time % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (time % (1000 * 60)) / 1000;

		if (days > 0) {
			sb.append(days);
			sb.append("天");
		}

		if (hours > 0) {
			sb.append(hours);
			sb.append("小时");
		}

		if (minutes > 0) {
			sb.append(minutes);
			sb.append("分");
		}

		if (seconds > 0) {
			sb.append(seconds);
			sb.append("秒");
		}

		return sb.toString();
	}

	/**
	 * 返回时分秒格式 10:00
	 * 
	 * @param time
	 * @return
	 */
	public static String getFormatCountTimeC(long time) {
		long hours = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		if (time <= 0) {
			return "加载中";
		}
		SimpleDateFormat format1 = new SimpleDateFormat("mm:ss");
		String last_time = "";

		if (hours > 0) {
			last_time = hours + ":" + format1.format(time);
		} else {
			last_time = format1.format(time);
		}
		return last_time;
	}

	/**
	 * 通过时间判断TodayPrize显示
	 * 
	 * @param time
	 * @return
	 * 
	 * 
	 */
	public static boolean showTodayIcon(long time) {
		if (time <= 0) {
			return true;
		}
		final long oneHour = 1000 * 60 * 60;
		if (time >= 20 * oneHour) {
			return false;
		}
		if (time >= 20 * oneHour) {
			return false;
		}
		return true;
	}

	/**
	 * 有小时返回hh小时mm分，无小时返回mm分ss秒
	 * 
	 * @return
	 */
	public static String getFormatCountTimeB(long time) {
		StringBuilder sb = new StringBuilder();

		if (time <= 0) {
			return "加载中";
		}
		long days = time / (1000 * 60 * 60 * 24);
		long hours = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (time % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (time % (1000 * 60)) / 1000;

		if (days > 0) {
			sb.append(days);
			sb.append("天");
			if (hours > 0) {
				sb.append(hours);
				sb.append("小时");
			}
			return sb.toString();
		}

		if (hours > 0) {
			sb.append(hours);
			sb.append("小时");
			if (minutes > 0) {
				sb.append(minutes);
				sb.append("分");
			}
			return sb.toString();
		}

		if (minutes > 0) {
			sb.append(minutes);
			sb.append("分");
			if (seconds > 0) {
				sb.append(seconds);
				sb.append("秒");
			}
			return sb.toString();
		}

		if (seconds > 0) {
			sb.append(seconds);
			sb.append("秒");
		}
		return sb.toString();
	}

	/**
	 * 截取scrollview的屏幕
	 * **/
	public static Bitmap getBitmapByView(FrameLayout scrollView) {
		int h = 0;
		int w = 0;
		Bitmap bitmap = null;
		// 获取listView实际高度
		for (int i = 0; i < scrollView.getChildCount(); i++) {
			h += scrollView.getChildAt(i).getHeight();
			w += scrollView.getChildAt(i).getWidth();
			// scrollView.getChildAt(i).setBackgroundResource(R.drawable.bg3);
		}
		Log.d(LOGTAG, "实际高度:" + h);
		Log.d(LOGTAG, " 高度:" + scrollView.getHeight());

		// 创建对应大小的bitmap
		bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(bitmap);
		scrollView.draw(canvas);
		// 测试输出
		// FileOutputStream out = null;
		// try {
		// out = new FileOutputStream("/sdcard/screen_test.png");
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }
		// try {
		// if (null != out) {
		// bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
		// out.flush();
		// out.close();
		// }
		// } catch (IOException e) {
		// // TODO: handle exception
		// }
		return bitmap;
	}
}