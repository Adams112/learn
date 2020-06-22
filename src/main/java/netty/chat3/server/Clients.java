package netty.chat3.server;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;

public class Clients {
	public static ConcurrentHashMap<String, Channel> clients = new ConcurrentHashMap<String, Channel>();
	
	private static HashMap<String, Integer> times = new HashMap<String, Integer>();
	
	public static void start() {
		// 开启心跳线程
	}
	
	public static void put(String name, Channel channel) {
		if (clients.contains(name)) {
			Channel ch = clients.get(name);
			try {
				ch.close().sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		clients.put(name, channel);
	}
	
	public static Channel get(String name) {
		return clients.get(name);
	}
}
