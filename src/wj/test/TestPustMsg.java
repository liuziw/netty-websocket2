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

import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Set;

import com.alibaba.fastjson.JSONObject;

/**
 * @FileName TestPustMsg.java
 * @Description: 
 *
 * @Date 2018年7月23日 下午2:16:42
 * @author wangjian
 * @version 1.0
 */
public class TestPustMsg {
    public static void main(String[] args) {
        // 推送消息给socket客户端
        Set<String> keysSet = SocketChannelMap.getKeys();
        for (String keys : keysSet) {
            if (keys.contains("Test_")) {
                SocketChannel channel = (SocketChannel) SocketChannelMap.get(keys);
                if (channel != null) {
                    JSONObject j = new JSONObject();
                    j.put("msg", "你好");
                    channel.writeAndFlush(new TextWebSocketFrame(j.toString()));
                }
            }
        }
    }
}
