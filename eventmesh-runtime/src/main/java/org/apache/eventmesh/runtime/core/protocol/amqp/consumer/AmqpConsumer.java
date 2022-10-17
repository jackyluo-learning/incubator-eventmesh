package org.apache.eventmesh.runtime.core.protocol.amqp.consumer;

import java.util.List;

import io.cloudevents.CloudEvent;

/**
 * AMQP Consumer, consume message from MQ and push to client
 */
public interface AmqpConsumer {

    void pushMessage(CloudEvent cloudEvent);

    void messageRedeliver(Object messageId);

    void messageRedeliver(List<Object> messageIds);

    /**
     * messageId Not defined yet
     * @param messageId
     * @throws Exception
     */
    void messageAck(Object messageId) throws Exception;

    void close();

    String getConsumerTag();

    String getQueue();

    void notifyConsumer();
}
