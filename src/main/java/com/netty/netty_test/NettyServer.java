package com.netty.netty_test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * @description:
 * @author: huangqf
 * @date: 2018/6/2
 */
public class NettyServer {

    public static void main(String args[]) throws InterruptedException {

        ServerBootstrap server = new ServerBootstrap();
        // 1.绑定两个线程组分别处理客服端通道的accept的读写时间
        // 2.绑定服务端通道NioServerSocketChannel
        // 3.给读写事件的线程通道绑定handle去真正处理读写
        // 4.监听端口

        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        server.group(parentGroup,childGroup);

        server.channel(NioServerSocketChannel.class);
        server.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new SimpleServerHandle());
            }
        });

        ChannelFuture future = server.bind(8080).sync();
        future.channel().closeFuture().sync();
    }
}
