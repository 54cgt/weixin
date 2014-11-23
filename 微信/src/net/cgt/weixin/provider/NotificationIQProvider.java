/*
 * Copyright (C) 2010 Moduad Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.cgt.weixin.provider;

import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.provider.IQProvider;
import org.xmlpull.v1.XmlPullParser;

/**
 * This class parses incoming IQ packets to NotificationIQ objects.<br>
 * 一个xml的处理类，负责把一个xml结构的消息内容转换成一个NotificationIQ通知实体对象
 * 
 * @author lijian (lijian_17@163.com)
 * @date 2014-11-22
 */
public class NotificationIQProvider implements IQProvider {

	public NotificationIQProvider() {
	}

	@Override
	public IQ parseIQ(XmlPullParser parser) throws Exception {
		//通过XmlPullParser解析xml,并将其转化为一个NotificationIQ
		//创建一个通知实体
		NotificationIQ notification = new NotificationIQ();
		for (boolean done = false; !done;) {
			int eventType = parser.next();
			if (eventType == XmlPullParser.START_TAG) {
				if ("id".equals(parser.getName())) {
					notification.setId(parser.nextText());
				}
				if ("apiKey".equals(parser.getName())) {
					notification.setApiKey(parser.nextText());
				}
				if ("title".equals(parser.getName())) {
					notification.setTitle(parser.nextText());
				}
				if ("message".equals(parser.getName())) {
					notification.setMessage(parser.nextText());
				}
				if ("uri".equals(parser.getName())) {
					notification.setUri(parser.nextText());
				}
			} else if (eventType == XmlPullParser.END_TAG && "notification".equals(parser.getName())) {
				done = true;
			}
		}

		return notification;
	}

}
