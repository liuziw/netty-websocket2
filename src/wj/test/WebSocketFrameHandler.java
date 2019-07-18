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

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;


import com.alibaba.fastjson.JSONObject;

/**
 * @FileName WebSocketFrameHandler.java
 * @Description: 
 *
 * @Date 2018年7月23日 下午12:29:44
 * @author wangjian
 * @version 1.0
 */
public class WebSocketFrameHandler extends SimpleChannelInboundHandler<Object>{

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                
                
                SocketChannelMap.remove((SocketChannel) ctx.channel());
                ctx.close();
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                String clientId = SocketChannelMap.getClientID((SocketChannel) ctx.channel());
                JSONObject j = new JSONObject();
                j.put("key", "value");
                j.put("clientId", clientId);
                ctx.writeAndFlush(new TextWebSocketFrame(j.toString()));
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        if (msg instanceof WebSocketFrame) { // 如果是Websocket请求，则进行websocket操作
            WebSocketHandle webSocketHandle = new WebSocketHandle();
            webSocketHandle.handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }else {
            System.out.println("不知道一些啥消息信息----》 " + JSONObject.toJSONString(msg));
        }
    }

    /** channel失效，从Map中移除 */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SocketChannelMap.remove((SocketChannel) ctx.channel());
        ctx.close();
    }

    /** channel 异常处理 */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }

}
