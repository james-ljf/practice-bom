package com.practice.bom.server;

import com.practice.bom.config.NettyConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author ljf
 * @description 服务端
 * @date 2023/2/13 10:29 AM
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PracticeServer {

    private final NettyConfig nettyConfig;

    private final PracticeServerHandler practiceServerHandler;

    public void start() {
        // 定义两个线程组，一个处理客户端accept事件接入连接，一个处理通道的读写事件
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                // 连接队列大小
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(NettyConfig.DATA_PACK_SEPARATOR.getBytes())))
                                .addLast(practiceServerHandler)
                                // 解码器
                                .addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()[0]));
                    }
                });
        try {
            ChannelFuture future = bootstrap.bind(nettyConfig.getPort()).sync();
            if (future.isSuccess()) {
                log.info("PracticeServer 启动成功，端口号{}", nettyConfig.getPort());
//                future.channel().closeFuture().sync();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
//            workerGroup.shutdownGracefully();
//            bossGroup.shutdownGracefully();
        }

    }

}
