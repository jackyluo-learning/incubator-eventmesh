package org.apache.eventmesh.runtime.core.protocol.amqp;

import lombok.Getter;

/**
 * a service that manage meta message, including the exchange, queue and vhost related to a single connection
 */
public class MetaMessageService {
    @Getter
    private ExchangeContainer exchangeContainer;
    @Getter
    private QueueContainer queueContainer;
    @Getter
    private ExchangeService exchangeService;
    @Getter
    private QueueService queueService;

}
