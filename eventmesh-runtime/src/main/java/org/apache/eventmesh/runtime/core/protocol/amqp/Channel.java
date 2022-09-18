package org.apache.eventmesh.runtime.core.protocol.amqp;

import lombok.Data;
import org.apache.eventmesh.common.protocol.amqp.AMQCommand;
import org.apache.eventmesh.runtime.core.plugin.MQConsumerWrapper;
import org.apache.eventmesh.runtime.core.plugin.MQProducerWrapper;
import org.apache.eventmesh.runtime.core.protocol.amqp.send.ChannelSender;
import org.apache.eventmesh.runtime.core.protocol.amqp.send.EventMeshAmqpSendResult;


@Data
public class Channel {
    private int channelId;

    private Connection connection;

    private MQProducerWrapper mqProducerWrapper;

    private MQConsumerWrapper mqConsumerWrapper;

    private ChannelSender channelSender;

    public Channel(int channelId, Connection connection, MQProducerWrapper mqProducerWrapper, MQConsumerWrapper mqConsumerWrapper, ChannelSender channelSender) {
        this.channelId = channelId;
        this.connection = connection;
        this.mqProducerWrapper = new MQProducerWrapper(this.connection.getEventMeshAmqpServer().getEventMeshAmqpConfiguration().eventMeshConnectorPluginType);
        this.mqConsumerWrapper = new MQConsumerWrapper(this.connection.getEventMeshAmqpServer().getEventMeshAmqpConfiguration().eventMeshConnectorPluginType);
    }

    public ChannelSender getSender() {
        ChannelSender channelSender = this.channelSender;
        if (channelSender == null) {
            channelSender = new ChannelSender(this);
        }
        return channelSender;
    }

    public EventMeshAmqpSendResult receiveBasicPublish(AMQCommand amqCommand){
        // TODO: 2022/9/16 解析amqCommand获取路由信息等，调用协议转换，将cloudEvent发送到mq
        channelSender.send();
    }

}
