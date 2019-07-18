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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @FileName WebsocketTest.java
 * @Description: 
 *
 * @Date 2018年7月23日 上午11:41:40
 * @author wangjian
 * @version 1.0
 */
public class WebsocketTest {
    public static void main(String[] args) {
        
        int port = 8888;
        WebSocketServer webSocketServer = new WebSocketServer(port);
        
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        threadPool.execute(webSocketServer);
    }
}
