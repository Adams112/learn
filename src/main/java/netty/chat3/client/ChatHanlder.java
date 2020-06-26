package netty.chat3.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.chat3.Message;
import netty.chat3.server.ConversationType;

public class ChatHanlder extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		Message message = (Message) msg;
		
		System.out.println(message);
	}

}
