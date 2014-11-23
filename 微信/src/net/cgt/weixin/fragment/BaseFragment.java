package net.cgt.weixin.fragment;

import net.cgt.weixin.R;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment基类
 * 
 * @author lijian
 * 
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class BaseFragment extends Fragment {

	private int layoutResID;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = null;
		if (layoutResID == 0) {
			view = inflater.inflate(R.layout.cgt_fragment_base, null);
			return view;
		} else {
			view = inflater.inflate(layoutResID, null);
		}
		return view;
	}

	public void setContentView(int layoutResID) {
		this.layoutResID = layoutResID;
	}

}
