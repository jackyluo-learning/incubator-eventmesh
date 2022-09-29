package org.apache.eventmesh.runtime.core.protocol.amqp.Session;

import lombok.Data;
import org.apache.eventmesh.runtime.core.plugin.MQConsumerWrapper;
import org.apache.eventmesh.runtime.core.protocol.amqp.Session.consumer.Consumer;
import org.apache.eventmesh.runtime.core.protocol.amqp.Session.consumer.ConsumerImpl;
import org.apache.eventmesh.runtime.core.protocol.amqp.processor.AmqpChannel;

/**
 * represent a session between server and client
 * In amqp, a session is related to a channel, because client interact with server base on different channel
 */
@Data
public class Session {
    /**
     * a MQConsumer that used to subscribe and consume message from mesh
     */
    private MQConsumerWrapper mqConsumerWrapper;

    /**
     * a consumer that used to push message to client
     * and keep track of status of message
     */
    private Consumer consumer;

    /**
     * the channel that related to current session
     */
    private AmqpChannel amqpChannel;

    public Session(AmqpChannel amqpChannel) {
        this.amqpChannel = amqpChannel;
    }

    public void setConsumer(Consumer consumer) {
        this.mqConsumerWrapper = new MQConsumerWrapper(
                this.amqpChannel.getConnection().getAmqpServer().getEventMeshAmqpConfiguration().eventMeshConnectorPluginType);
        this.consumer = consumer;
    }
}
