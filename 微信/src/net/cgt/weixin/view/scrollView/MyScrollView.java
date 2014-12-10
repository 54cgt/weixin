package net.cgt.weixin.view.scrollView;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class MyScrollView extends ViewGroup {
	private Context ctx;

	public MyScrollView(Context context) {
		super(context);
		this.ctx = context;
		init();
	}

	private void init() {
		scroller = new Scroller(ctx);
		gestureDetector = new GestureDetector(ctx, new OnGestureListener() {

			@Override
			public boolean onSingleTapUp(MotionEvent e) {//有一个手指抬起的时候
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {//当发生按下事件的时候
				// TODO Auto-generated method stub

			}

			/**
			 * 正常滑动屏幕时的回调方法
			 */
			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
				scrollBy((int) distanceX, 0);
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {//长按事件的时候
				// TODO Auto-generated method stub

			}

			/**
			 * 发生快速滑动时的回调方法
			 */
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

				isFling = true;//处理和onTouchEvent事件里面的抬起事件的冲突问题

				if (velocityX > 0 && curId > 0) {//快速向右滑
					moveToDest(curId - 1);
				} else if (velocityX < 0 && curId < getChildCount()) {//快速向左滑
					moveToDest(curId + 1);
				} else {//其他乱滑动，统一默认处理
					moveToDest();
				}

				return false;
			}

			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
		});

	}

	/**
	 * 对子view进行测量大小 view显示出来的几个步骤： 1、构造方法 2、测量大小 3、指定位置 4、绘制出来
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			view.measure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	/**
	 * 对子view进行排列布局
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			view.layout(i * getWidth(), 0, (i + 1) * getWidth(), getHeight());
		}
	}

	private GestureDetector gestureDetector;

	private Scroller scroller;

	/**
	 * 当前屏幕显示的子view的下标
	 */
	private int curId;

	/**
	 * 是否发生快速滑动
	 */
	private boolean isFling = false;

	private float lastX;
	private float lastY;

	/**
	 * 中断Touch事件
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		//这个是为了解决一个突然跳动的bug，因为在中断事件的过程中,子View获得了down事件这个时候突然中断 但是父view却没有down事件导致初始坐标为0，出现弹跳bug
		gestureDetector.onTouchEvent(ev);

		boolean result = false;

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = ev.getX();//记录按下的坐标值
			lastY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			int distanceX = (int) Math.abs(ev.getX() - lastX);//记录移动的偏移量
			int distanceY = (int) Math.abs(ev.getY() - lastY);

			//如果左右的偏移量 大于 上下的偏移量 并且    偏移量大于10(用来表明不是手指抖动)个像素  父View就执行中断操作
			if (distanceX > distanceY && distanceX > 10) {
				result = true;
			} else {
				result = false;
			}

			break;
		case MotionEvent.ACTION_UP:
			break;
		}

		return result;
	}

	/**
	 * 添加触摸事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		gestureDetector.onTouchEvent(event);

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN://按下
			break;
		case MotionEvent.ACTION_MOVE://移动
			break;
		case MotionEvent.ACTION_UP://抬起

			if (!isFling) {
				moveToDest();
			}
			isFling = false;
			break;
		}

		return true;
	}

	/**
	 * 将viewGroup的内容移动到适当的位置上
	 */
	private void moveToDest() {
		int destId = (getScrollX() + getWidth() / 2) / getWidth();
		moveToDest(destId);
	}

	/**
	 * 将指定下标的子view移动到屏幕上来
	 * 
	 * @param destId 子view下标
	 */
	public void moveToDest(int destId) {

		if (destId > getChildCount() - 1) {//处理下标超过边界的问题
			destId = getChildCount() - 1;
		}

		curId = destId;

		if (changedListener != null) {
			changedListener.changedTo(curId);
		}

		int distance = destId * getWidth() - getScrollX();
		//scroller.startScroll(getScrollX(), 0, distance, 0);//使用默认完成时间250ms
		/**
		 * 处理下速度问题(让播放时间与距离成正比)
		 */
		scroller.startScroll(getScrollX(), 0, distance, 0, Math.abs(distance));

		invalidate();//刷新视图     会执行  computeScroll() 这个方法 
	}

	@Override
	public void computeScroll() {//当父类要去更新子类的ScrollX,ScrollY的时候调用该方法
		if (scroller.computeScrollOffset()) {//是否还有偏移量，判断动画结束了没
			scrollTo(scroller.getCurrX(), 0);
			invalidate();
		}
	}

	/**
	 * 对外暴露一个接口
	 * 
	 * @author lijian
	 * 
	 */
	public interface IPageChangedListener {
		public void changedTo(int pageId);
	}

	private IPageChangedListener changedListener;

	public IPageChangedListener getChangedListener() {
		return changedListener;
	}

	public void setChangedListener(IPageChangedListener changedListener) {
		this.changedListener = changedListener;
	}

	/**
	 * 图片自动轮播的下标传递
	 * 
	 * @return
	 */
	public int getCurId() {
		return curId;
	}
}
