package netty.print;

import java.nio.charset.Charset;
import java.util.Scanner;

import com.alibaba.fastjson.JSON;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import netty.chat2.ClientHandler;

public class Client implements Runnable {
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
					ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
					ch.pipeline().addLast(new StringDecoder(Charset.forName("UTF8")));
					ch.pipeline().addLast(new StringEncoder(Charset.forName("UTF8")));
					ch.pipeline().addLast(new ClientHandler());
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
        Client client = new Client();
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
                
                String line = in.nextLine();
                future.channel().writeAndFlush(line + "\n");;
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
	
}
