package org.apache.eventmesh.runtime.core.protocol.amqp.Session.consumer;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import lombok.Data;
import org.apache.eventmesh.api.EventListener;
import org.apache.eventmesh.runtime.boot.EventMeshAmqpServer;
import org.apache.eventmesh.runtime.constants.EventMeshConstants;
import org.apache.eventmesh.runtime.core.plugin.MQConsumerWrapper;
import org.apache.eventmesh.runtime.core.protocol.amqp.processor.AmqpChannel;
import org.apache.eventmesh.runtime.core.protocol.amqp.processor.AmqpConnection;
import org.apache.eventmesh.runtime.util.EventMeshUtil;

import java.util.List;
import java.util.Properties;

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
