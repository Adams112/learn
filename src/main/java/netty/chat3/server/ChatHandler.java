package netty.chat3.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.chat3.Message;

public class ChatHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Message message = (Message) msg;
		int type = message.getConversationType();
		if (type == ConversationType.LOGIN) { 
			if (Clients.get(message.getUserId()) != null) {
				Clients.get(message.getUserId()).close().sync();
			}
			Clients.put(message.getUserId(), ctx.channel());
			OfflineMessageQueue.sendOfflineMessage(message.getUserId());
			
			System.out.println(message.getUserId() + ": login");
			
		} else if (type == ConversationType.LOGOUT) {
			if (Clients.get(message.getUserId()) != null) {
				Clients.get(message.getUserId()).close().sync();
			}
			Clients.clients.remove(message.getUserId());
			
			System.out.println(message.getUserId() + ": logout");
		} else  if (type == ConversationType.CHAT) {
			sendMessage(message.getTargetId(), message);
		} else if (type == ConversationType.GROUP) {
			
		} else if (type == ConversationType.SYSTEM) {
			
		} else {
			throw new IllegalArgumentException("conversation type is " + type);
		}
	}
	
	private void sendMessage(String name, Message message) {
		if (Clients.get(name) == null) {
			OfflineMessageQueue.addOfflineMessage(name, message);
		} else {
			Clients.get(name).writeAndFlush(message);
		}
	}
	
}
