package net.cgt.weixin.factory;

import net.cgt.weixin.fragment.AddressBookFragment;
import net.cgt.weixin.fragment.FindFragment;
import net.cgt.weixin.fragment.MeFragment;
import net.cgt.weixin.fragment.WeixinFragment;
import android.app.Fragment;

/**
 * Fragment工厂
 * 
 * @author lijian-pc
 * 
 */
public class FragmentFactory {
	public static Fragment getInstanceByIndex(int index) {
		Fragment fragment = null;
		switch (index) {
		case 1:// 微信
			fragment = new WeixinFragment();
			break;
		case 2:// 通讯录
			fragment = new AddressBookFragment();
			break;
		case 3:// 发现
			fragment = new FindFragment();
			break;
		case 4:// 我
			fragment = new MeFragment();
			break;
		}
		return fragment;
	}
}
