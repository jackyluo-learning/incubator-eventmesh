package org.apache.eventmesh.runtime.core.protocol.amqp;

import lombok.Data;
import org.apache.eventmesh.runtime.boot.EventMeshAmqpServer;
import org.apache.eventmesh.runtime.core.plugin.MQConsumerWrapper;
import org.apache.eventmesh.runtime.core.plugin.MQProducerWrapper;

import java.util.Map;

@Data
public class Connection {
    private Map<String, Channel> channelMap;

    private EventMeshAmqpServer eventMeshAmqpServer;

    public Connection(EventMeshAmqpServer eventMeshAmqpServer, Map<String, Channel> channelMap) {
        this.eventMeshAmqpServer = eventMeshAmqpServer;
        this.channelMap = channelMap;
    }

    public Channel getChannel(String channelNum) {
        return channelMap.get(channelNum);
    }
}
