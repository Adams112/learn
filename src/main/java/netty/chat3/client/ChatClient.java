package netty.chat3.client;

import java.util.Scanner;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.chat3.Message;
import netty.chat3.MessageDecoder;
import netty.chat3.MessageEncoder;
import netty.chat3.server.ConversationType;

public class ChatClient implements Runnable {
	private ChannelFuture future;
	
	public ChannelFuture getFuture() {
		return future;
	}

	public void setFuture(ChannelFuture future) {
		this.future = future;
	}

	@Override
	public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.AUTO_READ, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new MessageDecoder());
					ch.pipeline().addLast(new MessageEncoder());
					ch.pipeline().addLast(new ChatHanlder());
                }
            });
            ChannelFuture f = b.connect("127.0.0.1", 8080).sync();
            this.future = f;
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
	}
	
	public static void main(String[] args) {
        ChatClient client = new ChatClient();
        new Thread(client).start();
        ChannelFuture future = null;
        Scanner in = new Scanner(System.in);
        while (true) {
            try {
                //获取future，线程有等待处理时间
                if (null == future) {
                    future = client.getFuture();
                    Thread.sleep(500);
                    continue;
                }
                
                sendMessage(future.channel(), in.nextLine());
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
	
	// login|userId
	// logout|userId
	// chat|userId|targetId|content
	public static void sendMessage(Channel ch, String line) {
		String[] split = line.split("\\|");
		
		String type = split[0], userId = split[1];
		Message message = new Message();
		if (type.equalsIgnoreCase("login")) {
			message.setConversationType(ConversationType.LOGIN);
			message.setUserId(userId);
		} else if (type.equalsIgnoreCase("logout")) {
			message.setConversationType(ConversationType.LOGOUT);
			message.setUserId(userId);
		} else if (type.equalsIgnoreCase("chat")) {
			message.setConversationType(ConversationType.CHAT);
			message.setUserId(userId);
			message.setTargetId(split[2]);
			message.setContent(split[3].getBytes());
		} else if (type.equalsIgnoreCase("group")) {
			
		} else if (type.equalsIgnoreCase("system")) {
			
		} else {
			throw new IllegalArgumentException("wrong conversation type: " + type);
		}
		
		ch.writeAndFlush(message);
	}
	
}
