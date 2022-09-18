package org.apache.eventmesh.runtime.core.protocol.amqp.send;

import io.cloudevents.CloudEvent;
import org.apache.eventmesh.api.SendCallback;
import org.apache.eventmesh.runtime.core.plugin.MQConsumerWrapper;
import org.apache.eventmesh.runtime.core.plugin.MQProducerWrapper;
import org.apache.eventmesh.runtime.core.protocol.amqp.Channel;
import org.apache.eventmesh.runtime.core.protocol.amqp.send.EventMeshAmqpSendResult;

public class ChannelSender {
    private Channel channel;

    public ChannelSender (Channel channel) {
        this.channel = channel;
    }

    public EventMeshAmqpSendResult send (CloudEvent cloudEvent, SendCallback sendCallback) {
        // TODO: 2022/9/16 实现流控，调用底层通用eventmesh mq接口，或其他实现
        try {
            this.channel.getMqProducerWrapper().send(cloudEvent, sendCallback);
        } catch (Exception e) {
            e.printStackTrace();
            return new EventMeshAmqpSendResult(cloudEvent.getId(), EventMeshAmqpSendStatus.OTHER_EXCEPTION, e.getCause().toString());
        }
        return new EventMeshAmqpSendResult(cloudEvent.getId(), EventMeshAmqpSendStatus.SUCCESS, EventMeshAmqpSendStatus.SUCCESS.name());
    }
}
