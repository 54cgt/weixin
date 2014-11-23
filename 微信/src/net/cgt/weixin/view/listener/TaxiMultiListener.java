package net.cgt.weixin.view.listener;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

/**
 * 会议室消息监听类
 * 
 * @author lijian
 * @date 2014-11-23
 */
public class TaxiMultiListener implements PacketListener {

	@Override
	public void processPacket(Packet packet) {
		Message message = (Message) packet;
		String body = message.getBody();
	}

}
