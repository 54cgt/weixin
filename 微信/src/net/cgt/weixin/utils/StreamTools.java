package net.cgt.weixin.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamTools {
	
	/**
	 * 工具方法  把流里面的内容转换成字符串
	 * @param is
	 * @return
	 * @throws IOException 
	 */
	public static String readStream(InputStream is) throws IOException{
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = 0;
		while(( len = is.read(buffer))!=-1){
			baos.write(buffer, 0, len);
		}
		is.close();
		String result = baos.toString();
		baos.close();
		return result;
	}
}
