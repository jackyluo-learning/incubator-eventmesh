package org.apache.eventmesh.protocol.amqp;

import io.cloudevents.CloudEvent;
import org.apache.eventmesh.common.protocol.ProtocolTransportObject;
import org.apache.eventmesh.protocol.api.ProtocolAdaptor;
import org.apache.eventmesh.protocol.api.exception.ProtocolHandleException;

import java.util.List;

public class AmqpProtocolAdaptor implements ProtocolAdaptor<ProtocolTransportObject> {
    @Override
    public CloudEvent toCloudEvent(ProtocolTransportObject protocol) throws ProtocolHandleException {
        return null;
    }

    @Override
    public List<CloudEvent> toBatchCloudEvent(ProtocolTransportObject protocol) throws ProtocolHandleException {
        return null;
    }

    @Override
    public ProtocolTransportObject fromCloudEvent(CloudEvent cloudEvent) throws ProtocolHandleException {
        return null;
    }

    @Override
    public String getProtocolType() {
        return null;
    }
}
