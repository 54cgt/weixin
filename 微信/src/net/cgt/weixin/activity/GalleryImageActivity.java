package net.cgt.weixin.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import net.cgt.weixin.R;
import net.cgt.weixin.domain.User;
import ru.truba.touchgallery.GalleryWidget.BasePagerAdapter.OnItemChangeListener;
import ru.truba.touchgallery.GalleryWidget.FilePagerAdapter;
import ru.truba.touchgallery.GalleryWidget.GalleryViewPager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * 图片查看器
 * 
 * @author lijian
 * @date 2014-11-30
 */
public class GalleryImageActivity extends Activity {

	private GalleryViewPager mViewPager;
	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cgt_activity_galleryimage);
		Intent intent = getIntent();
		user = intent.getParcelableExtra("user");
		init();
	}

	private void init() {
		initView();
		initData();
	}

	private void initView() {
		// TODO Auto-generated method stub

	}

	private void initData() {
//		String[] urls = null;
		List<String> items = new ArrayList<String>();
//		try {
//			urls = getAssets().list("");
//
//			for (String filename : urls) {
//				if (filename.matches(".+\\.jpg")) {
//					String path = getFilesDir() + "/" + filename;
//					copy(getAssets().open(filename), new File(path));
//					items.add(path);
//				}
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		items.add(user.getUserPhote());

		FilePagerAdapter pagerAdapter = new FilePagerAdapter(this, items);
		pagerAdapter.setOnItemChangeListener(new OnItemChangeListener() {
			@Override
			public void onItemChange(int currentPosition) {
				Toast.makeText(GalleryImageActivity.this, "Current item is " + currentPosition, Toast.LENGTH_SHORT).show();
			}
		});

		mViewPager = (GalleryViewPager) findViewById(R.id.viewer);
		mViewPager.setOffscreenPageLimit(3);
		mViewPager.setAdapter(pagerAdapter);
	}

	public void copy(InputStream in, File dst) throws IOException {

		OutputStream out = new FileOutputStream(dst);

		// Transfer bytes from in to out
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}
}
