package org.apache.eventmesh.common.protocol.amqp;

import com.rabbitmq.client.AMQP.BasicProperties;
import lombok.Data;
import org.apache.eventmesh.common.protocol.ProtocolTransportObject;

import java.util.Map;

/**
 * amqp消息体，包含content header和content body
 */
@Data
public class AmqpMessage implements ProtocolTransportObject {
    private BasicProperties contentHeader;

    private byte[] contentBody;

    private Map<String, Object> extendInfo;

    public AmqpMessage() {
        this(null, null, null);
    }

    public AmqpMessage(BasicProperties contentHeader, byte[] contentBody, Map<String, Object> extendInfo) {
        this.contentHeader = contentHeader;
        this.contentBody = contentBody;
        this.extendInfo = extendInfo;
    }

    public long getSize() {
        return contentBody == null ? 0 : contentHeader == null ? contentBody.length : contentBody.length + contentHeader.getBodySize();
    }
}
