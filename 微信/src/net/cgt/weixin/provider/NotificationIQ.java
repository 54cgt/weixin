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

/** 
 * This class represents a notifcatin IQ packet.<br>
 * 通知实体
 *
 * @author lijian (lijian_17@163.com)
 * @date 2014-11-22
 */
public class NotificationIQ extends IQ {

    private String id;

    private String apiKey;

    private String title;

    private String message;

    private String uri;

    /**
     * 通知实体
     */
    public NotificationIQ() {
    }

    /**
	   <iq id="857-94" to="8e302d850bed414e98dd866fc2bac421@127.0.0.1/AndroidpnClient" type="set">
			<notification xmlns="androidpn:iq:notification">
				<id>13866063</id>
			</notification> 
		</iq>
	 * <pre>
	 * &lt;iq id="857-94" to="8e302d850bed414e98dd866fc2bac421@127.0.0.1/AndroidpnClient" type="set"&gt;
	 * 	&lt;notification xmlns="androidpn:iq:notification"&gt;
	 * 		&lt;id&gt;13866063&lt;/id&gt;
	 * 	&lt;/notification&gt; 
	 * &lt;/iq&gt;
	 * </pre>
	 */
    @Override
    public String getChildElementXML() {
        StringBuilder buf = new StringBuilder();
        buf.append("<").append("notification").append(" xmlns=\"").append(
                "androidpn:iq:notification").append("\">");
        if (id != null) {
            buf.append("<id>").append(id).append("</id>");
        }
        buf.append("</").append("notification").append("> ");
        return buf.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String url) {
        this.uri = url;
    }

}
