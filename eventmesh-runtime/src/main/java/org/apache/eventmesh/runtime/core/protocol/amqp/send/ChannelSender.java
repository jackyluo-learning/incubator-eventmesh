package org.apache.eventmesh.runtime.core.protocol.amqp.send;

import io.cloudevents.CloudEvent;
import org.apache.eventmesh.api.SendCallback;
import org.apache.eventmesh.runtime.core.protocol.amqp.processor.AmqpChannel;

public class ChannelSender {
    private AmqpChannel channel;

    public ChannelSender (AmqpChannel channel) {
        this.channel = channel;
    }

    public EventMeshAmqpSendResult send (CloudEvent cloudEvent, SendCallback sendCallback) {
        // TODO: 2022/9/16 实现流控，调用底层通用eventmesh mq接口，或其他实现
        return null;
    }
}
