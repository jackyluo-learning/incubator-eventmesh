package org.apache.eventmesh.runtime.core.protocol.amqp.consumer;

import java.util.List;

import io.cloudevents.CloudEvent;
import org.apache.eventmesh.runtime.core.protocol.tcp.client.session.push.DownStreamMsgContext;

/**
 * AMQP Consumer, consume message from MQ and push to client
 */
public interface AmqpConsumer {

    void pushMessage(PushMessageContext pushMessageContext);

    void messageRedeliver(Object messageId);

    void messageRedeliver(List<Object> messageIds);

    /**
     * messageId Not defined yet
     * @param messageId
     * @throws Exception
     */
    void messageAck(String messageId) throws Exception;

    void close();

    String getConsumerId();

    String getConsumerTag();

    String getQueue();

    void notifyConsumer();
}
