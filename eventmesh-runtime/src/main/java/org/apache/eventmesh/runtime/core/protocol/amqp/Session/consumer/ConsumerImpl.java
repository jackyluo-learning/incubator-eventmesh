package org.apache.eventmesh.runtime.core.protocol.amqp.Session.consumer;

import io.cloudevents.CloudEvent;
import lombok.Data;

import java.util.List;

@Data
public class ConsumerImpl implements Consumer {

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
