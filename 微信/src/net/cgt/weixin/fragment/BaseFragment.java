package net.cgt.weixin.fragment;

import net.cgt.weixin.R;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Fragment基类
 * 
 * @author lijian-pc
 * 
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class BaseFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.cgt_fragment_base, null);
		TextView textView = (TextView) view.findViewById(R.id.cgt_tv_content);
		textView.setText(initContent());
		return view;
	}

	public abstract CharSequence initContent();

}
