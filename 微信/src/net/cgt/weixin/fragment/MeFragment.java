package net.cgt.weixin.fragment;

import net.cgt.weixin.R;
import net.cgt.weixin.utils.AppToast;
import net.cgt.weixin.utils.L;
import net.cgt.weixin.utils.LogUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 我页面
 * 
 * @author lijian
 * @date 2014-11-26
 */
public class MeFragment extends BaseFragment implements OnClickListener {

	private static final String LOGTAG = LogUtil.makeLogTag(MeFragment.class);
	private ImageView mIv_userPic;
	private TextView mTv_userName;
	private TextView mTv_weixinNum;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.cgt_fragment_me, null);
		init(v);
		return v;
	}

	private void init(View v) {
		initView(v);
		initData();
	}

	private void initView(View v) {
		LinearLayout mLl_userInfo = (LinearLayout) v.findViewById(R.id.cgt_ll_me_userInfo);
		mIv_userPic = (ImageView) v.findViewById(R.id.cgt_iv_me_userPic);
		mTv_userName = (TextView) v.findViewById(R.id.cgt_tv_me_userName);
		mTv_weixinNum = (TextView) v.findViewById(R.id.cgt_tv_me_weixinNum);
		ImageButton mIb_qrCOde = (ImageButton) v.findViewById(R.id.cgt_ib_me_qrCode);

		LinearLayout mLl_photo = (LinearLayout) v.findViewById(R.id.cgt_ll_me_photo);
		LinearLayout mLl_collect = (LinearLayout) v.findViewById(R.id.cgt_ll_me_collect);
		LinearLayout mLl_wallet = (LinearLayout) v.findViewById(R.id.cgt_ll_me_wallet);
		LinearLayout mLl_set = (LinearLayout) v.findViewById(R.id.cgt_ll_me_set);

		mLl_userInfo.setOnClickListener(this);
		mIb_qrCOde.setOnClickListener(this);
		mLl_photo.setOnClickListener(this);
		mLl_collect.setOnClickListener(this);
		mLl_wallet.setOnClickListener(this);
		mLl_set.setOnClickListener(this);
	}

	private void initData() {
		mIv_userPic.setImageResource(R.drawable.user_picture);
		mTv_userName.setText("深情小建");
		mTv_weixinNum.setText("lijian374452668");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cgt_ll_me_userInfo:
			AppToast.getToast().show("用户信息");
			L.i(LOGTAG, "用户信息");
			break;
		case R.id.cgt_ib_me_qrCode:
			AppToast.getToast().show("二维码");
			L.i(LOGTAG, "二维码");
			break;
		case R.id.cgt_ll_me_photo:
			AppToast.getToast().show(R.string.text_me_photo);
			L.i(LOGTAG, "相册");
			break;
		case R.id.cgt_ll_me_collect:
			AppToast.getToast().show(R.string.text_me_collect);
			L.i(LOGTAG, "收藏");
			break;
		case R.id.cgt_ll_me_wallet:
			AppToast.getToast().show(R.string.text_me_wallet);
			L.i(LOGTAG, "钱包");
			break;
		case R.id.cgt_ll_me_set:
			AppToast.getToast().show(R.string.text_me_set);
			L.i(LOGTAG, "设置");
			break;

		default:
			break;
		}
	}
}
