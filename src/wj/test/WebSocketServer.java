/*
 * COPYRIGHT. ShenZhen JiMi Technology Co., Ltd. 2018.
 * ALL RIGHTS RESERVED.
 *
 * No part of this publication may be reproduced, stored in a retrieval system, or transmitted,
 * on any form or by any means, electronic, mechanical, photocopying, recording, 
 * or otherwise, without the prior written permission of ShenZhen JiMi Network Technology Co., Ltd.
 *
 * Amendment History:
 * 
 * Date                   By              Description
 * -------------------    -----------     -------------------------------------------
 * 2018年7月23日    wangjian         Create the class
 * http://www.jimilab.com/
*/

package wj.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @FileName WebSocketServer.java
 * @Description: 
 *
 * @Date 2018年7月23日 上午11:42:54
 * @author wangjian
 * @version 1.0
 */
public class WebSocketServer implements Runnable  {
    private  int PORT = 0;
    private static final String WEBSOCKET_PATH = "/websocket";
    
    public WebSocketServer(int port) {
        super();
        this.PORT = port;
    }

    @Override
    public void run() {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG, 128);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel)
                        throws Exception {
                    ChannelPipeline p = socketChannel.pipeline();
                    p.addLast(new HttpServerCodec());
                    p.addLast(new ChunkedWriteHandler());
                    p.addLast(new HttpObjectAggregator(65536));
                    p.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true));
                    p.addLast(new IdleStateHandler(20, 5, 5,TimeUnit.SECONDS));
                    p.addLast(new WebSocketFrameHandler());
                }
            });
            ChannelFuture f = bootstrap.bind(PORT).sync();
            if (f.isSuccess()) {
                System.out.println("websocket 服务器启动成功");
            } else {
                System.out.println("启动失败");
            }
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            throw new RuntimeException("关闭websocket服务端失败");
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}
