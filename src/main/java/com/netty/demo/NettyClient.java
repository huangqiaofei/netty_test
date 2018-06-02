package com.netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @description:
 * @author: huangqf
 * @date: 2018/6/2
 */
public class NettyClient {

    public static void main(String args[]) throws InterruptedException {

        Bootstrap client = new Bootstrap();

        // 绑定线程组 处理读写和连接事件
        EventLoopGroup group = new NioEventLoopGroup();
        client.group(group);
        // 绑定客服端通道
        client.channel(NioSocketChannel.class);
        client.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                nioSocketChannel.pipeline().addLast(new SimpleClientHandle());
                nioSocketChannel.pipeline().addLast(new StringEncoder());
            }
        });

        ChannelFuture future = client.connect("localhost", 8080).sync();

        String msg = "hello netty";
        future.channel().writeAndFlush(msg);
        future.channel().closeFuture().sync();
        System.out.println("客服端运行完毕！");

    }
}
