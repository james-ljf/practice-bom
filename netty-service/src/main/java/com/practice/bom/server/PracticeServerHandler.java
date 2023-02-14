package com.practice.bom.server;

import com.practice.bom.config.NettyConfig;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author ljf
 * @description 服务端管道业务处理
 * @date 2023/2/13 11:55 AM
 */
@Slf4j
@Component
public class PracticeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("[PracticeServerHandler.channelActive]");
        ByteBuf byteBuf = Unpooled.copiedBuffer("客户端，你好啊！" + NettyConfig.DATA_PACK_SEPARATOR, StandardCharsets.UTF_8);
        ctx.writeAndFlush(byteBuf);
        // writeAndFlush是异步的，future此时可能还没开始发生io操作，如需只发送而不接收任何请求可以增加监听器等操作完成后进行关闭管道
//        future.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] buffer = new byte[buf.readableBytes()];
        buf.readBytes(buffer);
        String message = new String(buffer, StandardCharsets.UTF_8);
        log.info("[PracticeServerHandler.channelRead] : 接收到客户端消息 = {}", message);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("[PracticeServerHandler.channelInactive]");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("[PracticeServerHandler.exceptionCaught]");
        cause.printStackTrace();
        ctx.close();
    }
}
