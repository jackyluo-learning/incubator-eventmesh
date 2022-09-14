package org.apache.eventmesh.common.protocol.amqp;

import java.util.Map;

/**
 * AMQP报文内容头基类
 */
public interface ContentHeader extends Cloneable{

    /**
     * 获取内容头的类id与方法的类id一致
     * @return
     */
    int getClassId();

    /**
     * 获取内容头的类名
     * @return
     */
    String getClassName();

    /**
     * 将属性放进map中
     * @return
     */
    Map<String, Object> toMap();
}
