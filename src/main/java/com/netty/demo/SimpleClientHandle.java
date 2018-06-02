package com.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * @description:
 * @author: huangqf
 * @date: 2018/6/2
 */
public class SimpleClientHandle extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            // 接收服务器传递的数据
            if (msg instanceof ByteBuf){
                System.out.println(((ByteBuf) msg).toString(Charset.defaultCharset()));
            }
            ctx.channel().close();
    }
}
