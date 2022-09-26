package org.apache.eventmesh.runtime.core.protocol.amqp;

import org.apache.eventmesh.runtime.core.protocol.amqp.processor.AmqpConnection;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class ConnectionContainer {
    private Map<VirtualHost, Set<AmqpConnection>> connectionMap = new ConcurrentHashMap<>();

    public ConnectionContainer() {

    }

    public void addConnection(VirtualHost virtualHost, AmqpConnection amqpConnection) {
        this.connectionMap.compute(virtualHost, (virtualHost1, amqpConnections) -> {
            if (amqpConnections == null) {
                amqpConnections = new HashSet<>();
            }
            amqpConnections.add(amqpConnection);
            return amqpConnections;
        });
    }

    public void removeConnection(VirtualHost virtualHost, AmqpConnection amqpConnection) {
        if (virtualHost == null) {
            return;
        }
        connectionMap.getOrDefault(virtualHost, Collections.emptySet()).remove(amqpConnection);
    }
}
