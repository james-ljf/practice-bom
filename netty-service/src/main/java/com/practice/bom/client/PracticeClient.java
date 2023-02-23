package com.practice.bom.client;

import com.practice.bom.config.NettyConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ljf
 * @description 客户端
 * @date 2023/2/13 2:12 PM
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PracticeClient {

    private final NettyConfig nettyConfig;

    private final PracticeClientHandler practiceClientHandler;

    public void connect() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup).channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline()
                                    .addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(NettyConfig.DATA_PACK_SEPARATOR.getBytes())))
                                    .addLast(practiceClientHandler);
                        }
                    });
            ChannelFuture future = b.connect(nettyConfig.getHost(), nettyConfig.getPort()).sync();
            if (future.isSuccess()) {
                log.info("PracticeClient 连接服务端成功");
            }
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }


}
