package org.apache.eventmesh.runtime.core.protocol.amqp.Session.consumer;

import io.cloudevents.CloudEvent;

import java.util.List;

public interface Consumer {

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
