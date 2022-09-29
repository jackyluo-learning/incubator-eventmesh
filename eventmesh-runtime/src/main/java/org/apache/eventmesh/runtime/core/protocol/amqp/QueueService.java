package org.apache.eventmesh.runtime.core.protocol.amqp;

import org.apache.eventmesh.runtime.core.protocol.amqp.remoting.MetaModels.AmqpQueue;

/**
 * provide services to use ExchangeContainer
 */
public class QueueService {
    private QueueContainer queueContainer;

    public AmqpQueue getQueue(VirtualHost virtualHost, String queueName) {
        return queueContainer.getOrCreateQueue(virtualHost, queueName);
    }
}
