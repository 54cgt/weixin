package net.cgt.weixin.view.pinyin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * 分类View
 * 
 * @author lijian-pc
 * @date 2014-11-28
 */
public class AssortView extends Button {

	/**
	 * 索引条宽度
	 **/
	private float mIndexbarWidth;
	/**
	 * 索引条外边距
	 **/
	private float mIndexbarMargin;
	/**
	 * 索引条内边距
	 **/
	private float mPreviewPadding;
	/**
	 * 密度
	 **/
	private float mDensity;
	/**
	 * 缩放密度
	 **/
	private float mScaledDensity;
	/**
	 * 透明度
	 **/
	private float mAlphaRate;
	/**
	 * 右侧索引文本色值
	 */
	private static final String COLOR_RIGHT_TEXT = "#565656";
	/**
	 * 右侧索引背景色值
	 */
	private static final String COLOR_RIGHT_BACKGROUND = "#808080";// "#bfbfbf";
	/**
	 * 中间预览文本色值
	 */
	private static final String COLOR_MIDDLE_TEXT = "#ffffff";
	/**
	 * 中间预览背景色值
	 */
	private static final String COLOR_MIDDLE_BACKGROUND = "#000000";// "#808080";

	/**
	 * 触摸右侧的分类字母响应监听
	 * 
	 * @author lijian-pc
	 */
	public interface OnTouchAssortListener {
		/**
		 * 
		 * @param s
		 */
		public void onTouchAssortListener(String s);

		public void onTouchAssortUP();
	}

	public AssortView(Context context) {
		super(context);
		init(context);
	}

	public AssortView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public AssortView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		mDensity = context.getResources().getDisplayMetrics().density;
		mScaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
		mIndexbarWidth = 25 * mDensity;
		mIndexbarMargin = 0 * mDensity;
		mPreviewPadding = 10 * mDensity;
	}

	// 分类
	// private String[] assort = { "?", "#", "A", "B", "C", "D", "E", "F", "G",
	// "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
	// "V", "W", "X", "Y", "Z" };
	/** 导航分类字符 **/
	private String[] assort = { "↑", "☆", "A", "B", "C", "D", "E", "F", "G",
			"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z", "#" };
	private Paint paint = new Paint();
	// 选择的索引
	private int selectIndex = -1;
	/**字母监听器**/ 
	private OnTouchAssortListener onTouch;

	/**
	 * 设置右侧字母触摸的监听
	 * 
	 * @param onTouch
	 *            要实现的监听接口
	 */
	public void setOnTouchAssortListener(OnTouchAssortListener onTouch) {
		this.onTouch = onTouch;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int height = getHeight();
		int width = getWidth();
		int interval = height / assort.length;

		for (int i = 0, length = assort.length; i < length; i++) {
			// 抗锯齿
			paint.setAntiAlias(true);
			// 默认粗体
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			// 颜色
			paint.setColor(Color.parseColor(COLOR_RIGHT_TEXT));
			paint.setTextSize(14 * mScaledDensity);
			if (i == selectIndex) {
				// 被选择的字母改变颜色和粗体
				paint.setColor(Color.parseColor("#3399ff"));
				paint.setFakeBoldText(true);
				paint.setTextSize(30 * mScaledDensity);
			}
			// 计算字母的X坐标
			float xPos = width / 2 - paint.measureText(assort[i]) / 2;
			// 计算字母的Y坐标
			float yPos = interval * i + interval;
			canvas.drawText(assort[i], xPos, yPos, paint);
			paint.reset();
		}
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		float y = event.getY();
		int index = (int) (y / getHeight() * assort.length);
		if (index >= 0 && index < assort.length) {

			switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE:
				// 如果滑动改变
				if (selectIndex != index) {
					selectIndex = index;
					if (onTouch != null) {
						onTouch.onTouchAssortListener(assort[selectIndex]);
					}
				}
				break;
			case MotionEvent.ACTION_DOWN:
				selectIndex = index;
				if (onTouch != null) {
					onTouch.onTouchAssortListener(assort[selectIndex]);
				}
				break;
			case MotionEvent.ACTION_UP:
				if (onTouch != null) {
					onTouch.onTouchAssortUP();
				}
				selectIndex = -1;
				break;
			}
		} else {
			selectIndex = -1;
			if (onTouch != null) {
				onTouch.onTouchAssortUP();
			}
		}
		invalidate();

		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}
}
