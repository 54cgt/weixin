/*
 * Copyright 2011 woozzu
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
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SectionIndexer;

/**
 * 右侧的索引条
 * 
 * @author lijian-pc
 * @date 2014-11-26
 */
public class IndexScroller {

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
	 * 状态
	 **/
	private int mState = STATE_HIDDEN;
	/**
	 * ListView宽度
	 **/
	private int mListViewWidth;
	/**
	 * ListView高度
	 **/
	private int mListViewHeight;
	/**
	 * 当前部分
	 */
	private int mCurrentSection = -1;
	/**
	 * 是否正在索引
	 */
	private boolean mIsIndexing = false;

	private ListView mListView = null;
	private SectionIndexer mIndexer = null;
	private String[] mSections = null;
	private RectF mIndexbarRect;

	/** 4种状态（已隐藏） **/
	private static final int STATE_HIDDEN = 0;
	/** 4种状态（正在显示） **/
	private static final int STATE_SHOWING = 1;
	/** 4种状态（已显示） **/
	private static final int STATE_SHOWN = 2;
	/** 4种状态（正在隐藏） **/
	private static final int STATE_HIDING = 3;

	public IndexScroller(Context context, ListView lv) {
		mDensity = context.getResources().getDisplayMetrics().density;
		mScaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
		mListView = lv;
		setAdapter(mListView.getAdapter());

		mIndexbarWidth = 20 * mDensity;
		mIndexbarMargin = 10 * mDensity;
		mPreviewPadding = 5 * mDensity;
	}

	public void draw(Canvas canvas) {
		if (mState == STATE_HIDDEN)
			return;

		// mAlphaRate determines the rate of opacity
		Paint indexbarPaint = new Paint();
		indexbarPaint.setColor(Color.BLACK);
		indexbarPaint.setAlpha((int) (64 * mAlphaRate));
		indexbarPaint.setAntiAlias(true);
		// 画右侧字母索引的圆矩形
		canvas.drawRoundRect(mIndexbarRect, 5 * mDensity, 5 * mDensity,
				indexbarPaint);

		if (mSections != null && mSections.length > 0) {
			// Preview is shown when mCurrentSection is set
			// 中间预览文本
			if (mCurrentSection >= 0) {
				Paint previewPaint = new Paint(); // 用来绘画索引条背景的画笔
				previewPaint.setColor(Color.BLACK);// 设置画笔颜色为黑色
				previewPaint.setAlpha(96); // 设置透明度
				previewPaint.setAntiAlias(true);// 设置抗锯齿
				previewPaint.setShadowLayer(3, 0, 0, Color.argb(64, 0, 0, 0)); // 设置阴影层

				Paint previewTextPaint = new Paint(); // 用来绘画索引字母的画笔
				previewTextPaint.setColor(Color.WHITE); // 设置画笔为白色
				previewTextPaint.setAntiAlias(true); // 设置抗锯齿
				previewTextPaint.setTextSize(50 * mScaledDensity); // 设置字体大小

				// 单个文本的宽度
				float previewTextWidth = previewTextPaint
						.measureText(mSections[mCurrentSection]);

				float previewSize = 2 * mPreviewPadding
						+ previewTextPaint.descent()
						- previewTextPaint.ascent();

				RectF previewRect = new RectF(
						(mListViewWidth - previewSize) / 2,
						(mListViewHeight - previewSize) / 2,
						(mListViewWidth - previewSize) / 2 + previewSize,
						(mListViewHeight - previewSize) / 2 + previewSize);

				// 中间索引的那个框
				canvas.drawRoundRect(previewRect, 5 * mDensity, 5 * mDensity,
						previewPaint);

				// 绘画索引字母
				canvas.drawText(
						mSections[mCurrentSection],
						previewRect.left + (previewSize - previewTextWidth) / 2
								- 1,
						previewRect.top + mPreviewPadding
								- previewTextPaint.ascent() + 1,
						previewTextPaint);
			}

			// 绘画右侧索引条的字母
			Paint indexPaint = new Paint();
			indexPaint.setColor(Color.WHITE);
			indexPaint.setAlpha((int) (255 * mAlphaRate));
			indexPaint.setAntiAlias(true);
			indexPaint.setTextSize(12 * mScaledDensity);

			float sectionHeight = (mIndexbarRect.height() - 2 * mIndexbarMargin)
					/ mSections.length;
			float paddingTop = (sectionHeight - (indexPaint.descent() - indexPaint
					.ascent())) / 2;
			for (int i = 0; i < mSections.length; i++) {
				float paddingLeft = (mIndexbarWidth - indexPaint
						.measureText(mSections[i])) / 2;
				canvas.drawText(mSections[i], mIndexbarRect.left + paddingLeft,
						mIndexbarRect.top + mIndexbarMargin + sectionHeight * i
								+ paddingTop - indexPaint.ascent(), indexPaint);
			}
		}
	}

	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// If down event occurs inside index bar region, start indexing
			// 如果按下的事件发生在索引条区域内,开始索引
			if (mState != STATE_HIDDEN && contains(ev.getX(), ev.getY())) {
				setState(STATE_SHOWN);

				// It demonstrates that the motion event started from index bar
				mIsIndexing = true;
				// Determine which section the point is in, and move the list to
				// that section
				mCurrentSection = getSectionByPoint(ev.getY());

				// ListView.setSelection(int position）
				// ListView.setSelectionFromTop(int position, int y);
				// 其中
				// position指的是指定的item的在ListView中的索引，注意如果有Header存在的情况下，索引是从Header就开始算的。
				// y指的是到ListView可见范围内最上边边缘的距离。
				mListView.setSelection(mIndexer
						.getPositionForSection(mCurrentSection));
				return true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (mIsIndexing) {
				// If this event moves inside index bar
				if (contains(ev.getX(), ev.getY())) {
					// Determine which section the point is in, and move the
					// list to that section
					mCurrentSection = getSectionByPoint(ev.getY());
					mListView.setSelection(mIndexer
							.getPositionForSection(mCurrentSection));
				}
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			if (mIsIndexing) {
				mIsIndexing = false;
				mCurrentSection = -1;
			}
			if (mState == STATE_SHOWN)
				setState(STATE_HIDING);
			break;
		}
		return false;
	}

	/**
	 * 创建索引条大小
	 * 
	 * @param w
	 * @param h
	 * @param oldw
	 * @param oldh
	 */
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		mListViewWidth = w;
		mListViewHeight = h;
		mIndexbarRect = new RectF(w - mIndexbarMargin - mIndexbarWidth,
				mIndexbarMargin, w - mIndexbarMargin, h - mIndexbarMargin);
	}

	public void show() {
		if (mState == STATE_HIDDEN)
			setState(STATE_SHOWING);
		else if (mState == STATE_HIDING)
			setState(STATE_HIDING);
	}

	public void hide() {
		if (mState == STATE_SHOWN)
			setState(STATE_HIDING);
	}

	/**
	 * 设置右侧索引条数据
	 * 
	 * @param adapter
	 */
	public void setAdapter(Adapter adapter) {
		if (adapter instanceof SectionIndexer) {
			mIndexer = (SectionIndexer) adapter;
			mSections = (String[]) mIndexer.getSections();
		}
	}

	/**
	 * 设置显隐状态
	 * 
	 * @param state
	 */
	private void setState(int state) {
		if (state < STATE_HIDDEN || state > STATE_HIDING)
			return;

		mState = state;
		switch (mState) {
		case STATE_HIDDEN:
			// Cancel any fade effect
			mHandler.removeMessages(0);
			break;
		case STATE_SHOWING:
			// Start to fade in
			mAlphaRate = 0;
			fade(0);
			break;
		case STATE_SHOWN:
			// Cancel any fade effect
			mHandler.removeMessages(0);
			break;
		case STATE_HIDING:
			// Start to fade out after three seconds
			mAlphaRate = 1;
			fade(3000);
			break;
		}
	}

	/**
	 * 检测触摸点是否在索引条区域内
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean contains(float x, float y) {
		// Determine if the point is in index bar region, which includes the
		// right margin of the bar
		return (x >= mIndexbarRect.left && y >= mIndexbarRect.top && y <= mIndexbarRect.top
				+ mIndexbarRect.height());
	}

	/**
	 * 根据触摸点的y坐标获得区域
	 * 
	 * @param y
	 * @return
	 */
	private int getSectionByPoint(float y) {
		// 如果索引字母不存在,返回0
		if (mSections == null || mSections.length == 0)
			return 0;
		// 如果在最顶部的索引字母之外,返回0
		if (y < mIndexbarRect.top + mIndexbarMargin)
			return 0;
		// 如果在最底部的索引字母之外,返回最后一个索引字母角标
		if (y >= mIndexbarRect.top + mIndexbarRect.height() - mIndexbarMargin)
			return mSections.length - 1;
		return (int) ((y - mIndexbarRect.top - mIndexbarMargin) / ((mIndexbarRect
				.height() - 2 * mIndexbarMargin) / mSections.length));
	}

	/**
	 * 设置多久后隐藏
	 * 
	 * @param delay
	 */
	private void fade(long delay) {
		mHandler.removeMessages(0);
		mHandler.sendEmptyMessageAtTime(0, SystemClock.uptimeMillis() + delay);
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (mState) {
			case STATE_SHOWING:
				// Fade in effect
				mAlphaRate += (1 - mAlphaRate) * 0.2;
				if (mAlphaRate > 0.9) {
					mAlphaRate = 1;
					setState(STATE_SHOWN);
				}

				mListView.invalidate();
				fade(10);
				break;
			case STATE_SHOWN:
				// If no action, hide automatically
				setState(STATE_HIDING);
				break;
			case STATE_HIDING:
				// Fade out effect
				mAlphaRate -= mAlphaRate * 0.2;
				if (mAlphaRate < 0.1) {
					mAlphaRate = 0;
					setState(STATE_HIDDEN);
				}

				mListView.invalidate();
				fade(10);
				break;
			}
		}

	};
}
