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

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @FileName SocketChannelMap.java
 * @Description: 
 *
 * @Date 2018年7月23日 下午12:31:27
 * @author wangjian
 * @version 1.0
 */
public class SocketChannelMap {
    private static Map<String, SocketChannel> channelMap = new ConcurrentHashMap<String, SocketChannel>();

    public static void add(String clientId, SocketChannel socketChannel) {
        channelMap.put(clientId, socketChannel);
    }

    public static Channel get(String clientId) {
        return channelMap.get(clientId);
    }

    @SuppressWarnings("rawtypes")
    public static void remove(SocketChannel socketChannel) {
        for (Map.Entry entry : channelMap.entrySet()) {
            if (entry.getValue() == socketChannel) {
                channelMap.remove(entry.getKey());
            }
        }
    }
    public static int getSize() {
        return channelMap.size();
    }

    public static Set<String> getKeys() {
        return channelMap.keySet();
    }
    
    
    @SuppressWarnings("rawtypes")
    public static String getClientID(SocketChannel socketChannel){
        for (Map.Entry entry : channelMap.entrySet()) {
            if (entry.getValue() == socketChannel) {
                return (String) entry.getKey();
            }
        }
        return null;
    }
}
