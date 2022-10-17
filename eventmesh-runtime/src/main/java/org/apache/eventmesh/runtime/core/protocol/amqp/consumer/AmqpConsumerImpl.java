package org.apache.eventmesh.runtime.core.protocol.amqp.consumer;

import java.util.List;

import io.cloudevents.CloudEvent;

import lombok.Data;

@Data
public class AmqpConsumerImpl implements AmqpConsumer {

    /**
     * each consumer should be a tag that used to identify itself only within a channel.
     * each consumerTag should be unique
     */
    private String consumerTag;

    private boolean autoAck;

    /**
     * a queue which current consumer wants to consume from
     */
    private String queueName;

    @Override
    public void pushMessage(CloudEvent cloudEvent) {
        // TODO: 2022/10/12 push to client
    }

    @Override
    public void messageRedeliver(Object messageId) {

    }

    @Override
    public void messageRedeliver(List<Object> messageIds) {

    }

    @Override
    public void messageAck(Object messageId) throws Exception {

    }

    @Override
    public void close() {

    }

    @Override
    public String getConsumerTag() {
        return null;
    }

    @Override
    public String getQueue() {
        return null;
    }

    @Override
    public void notifyConsumer() {

    }
}
