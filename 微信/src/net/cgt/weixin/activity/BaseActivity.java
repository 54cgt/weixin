package net.cgt.weixin.activity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.View.OnClickListener;

/**
 * Activity的基类
 * 
 * @author lijian
 * @data 2015-01-08 22:03:31
 */
public class BaseActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onClick(View v) {

	}

	/**
	 * overflow被展开的时候调用<br>
	 * onMenuOpened()方法用于让隐藏在overflow当中的Action按钮的图标显示出来
	 * 
	 * @param featureId
	 * @param menu
	 * @return
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		//通过返回反射的方法将MenuBuilder的setOptionalIconsVisible变量设置为true
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	/**
	 * 通过反射得到Android的有无物理Menu键<br>
	 * setOverflowShowingAlways()方法则是屏蔽掉物理Menu键，不然在有物理Menu键的手机上，overflow按钮会显示不出来
	 */
	protected void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
