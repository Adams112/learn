package netty.chat3.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
import netty.chat3.Message;

public class OfflineMessageQueue {
	public static ConcurrentHashMap<String, List<Message>> offlineMessages = new ConcurrentHashMap<String, List<Message>>();
	
	public static void addOfflineMessage(String name, Message message) {
		offlineMessages.putIfAbsent(name, new ArrayList<Message>());
		List<Message> list = offlineMessages.get(name);
		synchronized (list) {
			list.add(message);
		}
		
		sendOfflineMessage(name);
	}
	
	public static void sendOfflineMessage(String name) {
		Channel ch = Clients.get(name);
		if (ch != null) {
			List<Message> messages = offlineMessages.get(name);
			offlineMessages.remove(name);
			if (messages != null) {
				for (Message message : messages) {
					ch.writeAndFlush(message);
				}
			}
		}
	}
}
