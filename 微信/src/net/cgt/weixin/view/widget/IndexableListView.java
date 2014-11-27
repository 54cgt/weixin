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

package net.cgt.weixin.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 自定义索引列表
 * 
 * @author lijian-pc
 * @date 2014-11-26
 */
public class IndexableListView extends ListView {

	private boolean mIsFastScrollEnabled = false;
	private IndexScroller mScroller = null;
	private GestureDetector mGestureDetector = null;

	public IndexableListView(Context context) {
		super(context);
		init(context);
	}

	public IndexableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public IndexableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		if (mScroller == null)
			mScroller = new IndexScroller(context, this);
	}

	@Override
	public boolean isFastScrollEnabled() {
		return mIsFastScrollEnabled;
	}

	@Override
	public void setFastScrollEnabled(boolean enabled) {
		mIsFastScrollEnabled = enabled;
		if (mIsFastScrollEnabled) {
			if (mScroller != null) {
				mScroller.show();// 右侧索引条显示
			}
		} else {
			// 如果不是快速滚动
			if (mScroller != null) {
				mScroller.hide();// 右侧索引条隐藏
				mScroller = null;
			}
		}
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);

		// Overlay index bar
		if (mScroller != null)
			mScroller.draw(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// Intercept ListView's touch event
		// 如果索引条上有触摸事件,则拦截ListView's的触摸事件
		if (mScroller != null && mScroller.onTouchEvent(ev))
			return true;

		if (mGestureDetector == null) {
			mGestureDetector = new GestureDetector(getContext(),
					new GestureDetector.SimpleOnGestureListener() {

						@Override
						public boolean onFling(MotionEvent e1, MotionEvent e2,
								float velocityX, float velocityY) {
							// If fling happens, index bar shows
							// 如果fling事件发生,则索引条显示
							if (mScroller != null)
								mScroller.show();
							return super.onFling(e1, e2, velocityX, velocityY);
						}

					});
		}
		mGestureDetector.onTouchEvent(ev);

		return super.onTouchEvent(ev);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
		if (mScroller != null)
			mScroller.setAdapter(adapter);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (mScroller != null)
			mScroller.onSizeChanged(w, h, oldw, oldh);
	}

}
