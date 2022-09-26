package org.apache.eventmesh.common.protocol.amqp;

import java.util.Map;

/**
 * base class of AMQP content header
 */
public interface ContentHeader extends Cloneable {

    /**
     * obtain class id of content header and method
     * @return
     */
    int getClassId();

    /**
     * obtain class name
     * @return
     */
    String getClassName();

    /**
     * put attr into map
     * @return
     */
    Map<String, Object> toMap();
}
