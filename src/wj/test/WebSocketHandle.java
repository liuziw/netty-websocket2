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

import com.alibaba.fastjson.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

/**
 * @FileName WebSocketHandle.java
 * @Description: 
 *
 * @Date 2018年7月23日 下午1:50:05
 * @author wangjian
 * @version 1.0
 */
public class WebSocketHandle {
    public void handleWebSocketFrame(ChannelHandlerContext ctx,
            WebSocketFrame frame) {

        if (frame instanceof TextWebSocketFrame) {

            String request = ((TextWebSocketFrame) frame).text();
            
            JSONObject jsonObject = JSONObject.parseObject(request);

            String reqType = jsonObject.getString("reqType");
            
            String clientId = "Test_" + jsonObject.getString("clientId");

            
            /**存储Socket*/
            SocketChannelMap.add(clientId, (SocketChannel) ctx.channel());
            
            switch (reqType) {
            case "A1006":       //登录验证
                JSONObject j = new JSONObject();
                j.put("clientId", clientId);
                j.put("key", "B1006");
                ctx.writeAndFlush(new TextWebSocketFrame(JSONObject.toJSONString(j.toString())));
                break;
            case "A1002":      //心跳处理
                break;
            case "A1007":         //推送消息处理
                System.out.println("推送消息应答---》 "+jsonObject.toJSONString());
                break;
            default:
                break;
            }

        } else {
            String message = "unsupported frame type: "
                    + frame.getClass().getName();
            throw new UnsupportedOperationException(message);
        }
    }
}
