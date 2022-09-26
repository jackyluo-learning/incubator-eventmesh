package org.apache.eventmesh.runtime.core.protocol.amqp.remoting;

import org.apache.eventmesh.runtime.core.protocol.amqp.remoting.protocol.ProtocolVersion;

import java.util.Map;

/**
 * create response method body
 */
public class MethodRegistry {
    private ProtocolVersion protocolVersion;

    public MethodRegistry(ProtocolVersion pv) {
        this.protocolVersion = pv;
    }
}
