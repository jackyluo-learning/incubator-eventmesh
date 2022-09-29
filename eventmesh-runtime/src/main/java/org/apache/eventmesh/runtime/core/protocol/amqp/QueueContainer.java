package org.apache.eventmesh.runtime.core.protocol.amqp;

import org.apache.eventmesh.runtime.core.protocol.amqp.remoting.MetaModels.AmqpQueue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * manage all queues used in the server
 */
public class QueueContainer {
    private ExchangeContainer exchangeContainer;

    private Map<VirtualHost, Map<String, AmqpQueue>> queueMap;

    public QueueContainer(ExchangeContainer exchangeContainer) {
        this.exchangeContainer = exchangeContainer;
        this.queueMap = new ConcurrentHashMap<>();
    }

    // TODO: 2022/9/29
    public AmqpQueue getOrCreateQueue(VirtualHost virtualHost, String queueName) {
        return null;
    }
}
