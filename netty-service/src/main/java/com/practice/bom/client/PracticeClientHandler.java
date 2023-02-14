package com.practice.bom.client;

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
 * @description 客户端管道业务处理
 * @date 2023/2/13 2:13 PM
 */
@Slf4j
@Component
public class PracticeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("[PracticeClientHandler.channelActive]");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] buffer = new byte[buf.readableBytes()];
        buf.readBytes(buffer);
        String message = new String(buffer, StandardCharsets.UTF_8);
        log.info("[PracticeClientHandler.channelRead] : 接收到服务端消息 = {}", message);
        ByteBuf byteBuf = Unpooled.copiedBuffer("服务端，已收到你的消息" + NettyConfig.DATA_PACK_SEPARATOR, StandardCharsets.UTF_8);
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("[PracticeClientHandler.channelInactive]");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.info("[PracticeClientHandler.exceptionCaught]");
        cause.printStackTrace();
        ctx.close();
    }

}
