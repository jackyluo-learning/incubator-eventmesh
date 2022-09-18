package org.apache.eventmesh.protocol.amqp;

import io.cloudevents.CloudEvent;
import org.apache.eventmesh.common.protocol.ProtocolTransportObject;
import org.apache.eventmesh.common.protocol.amqp.AmqpMessage;
import org.apache.eventmesh.common.protocol.amqp.common.ProtocolKey;
import org.apache.eventmesh.common.protocol.grpc.protos.SimpleMessage;
import org.apache.eventmesh.common.protocol.http.HttpEventWrapper;
import org.apache.eventmesh.protocol.amqp.resolver.AmqpProtocolResolver;
import org.apache.eventmesh.protocol.api.ProtocolAdaptor;
import org.apache.eventmesh.protocol.api.exception.ProtocolHandleException;

import java.util.List;

/**
 * Amqp协议适配器，amqp与cloudEvent之间的转换
 */
public class AmqpProtocolAdaptor implements ProtocolAdaptor<ProtocolTransportObject> {
    @Override
    public CloudEvent toCloudEvent(ProtocolTransportObject protocol) throws ProtocolHandleException {
        if (protocol instanceof AmqpMessage) {
            AmqpMessage amqpMessage = (AmqpMessage) protocol;
            return deserializeAmqpProtocol(amqpMessage);

        } else {
            throw new ProtocolHandleException(String.format("protocol class: %s", protocol.getClass()));
        }
    }

    private CloudEvent deserializeAmqpProtocol(AmqpMessage amqpMessage)
            throws ProtocolHandleException {
        return AmqpProtocolResolver.buildEvent(amqpMessage);
    }

    @Override
    public List<CloudEvent> toBatchCloudEvent(ProtocolTransportObject protocol) throws ProtocolHandleException {
        return null;
    }

    @Override
    public ProtocolTransportObject fromCloudEvent(CloudEvent cloudEvent) throws ProtocolHandleException {
        String type = cloudEvent.getType();
        if (AMQPProtocolConstant.PROTOCOL_NAME.equalsIgnoreCase(type)){
            return AmqpProtocolResolver.buildAmqpMessage(cloudEvent);
        }
        else {
            throw new ProtocolHandleException(String.format("Unsupported protocolDesc: %s", type));
        }
    }

    @Override
    public String getProtocolType() {
        return null;
    }
}
