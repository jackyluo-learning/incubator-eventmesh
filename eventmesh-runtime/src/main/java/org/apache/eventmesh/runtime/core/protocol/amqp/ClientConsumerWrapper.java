package org.apache.eventmesh.runtime.core.protocol.amqp;

import io.cloudevents.core.builder.CloudEventBuilder;
import org.apache.eventmesh.api.EventListener;
import org.apache.eventmesh.runtime.boot.EventMeshAmqpServer;
import org.apache.eventmesh.runtime.constants.EventMeshConstants;
import org.apache.eventmesh.runtime.core.plugin.MQConsumerWrapper;
import org.apache.eventmesh.runtime.core.protocol.amqp.downstreamstrategy.DownstreamDispatchStrategy;
import org.apache.eventmesh.runtime.core.protocol.amqp.processor.AmqpChannel;

import java.util.Properties;

/**
 * define MQ consumer
 * define EventLister registered in MQ consumer
 */
public class ClientConsumerWrapper {

    /**
     * global server
     */
    private EventMeshAmqpServer eventMeshAmqpServer;

    /**
     * global mapping
     */
    private AmqpGlobalMapping amqpGlobalMapping;

    /**
     * a MQConsumer that used to subscribe and consume message from mesh
     */
    private MQConsumerWrapper mqConsumerWrapper;

    /**
     * strategy that used to select client to downstream message
     */
    private DownstreamDispatchStrategy downstreamDispatchStrategy;

    public ClientConsumerWrapper(EventMeshAmqpServer eventMeshAmqpServer,
                                 AmqpGlobalMapping amqpGlobalMapping,
                                 MQConsumerWrapper mqConsumerWrapper,
                                 DownstreamDispatchStrategy downstreamDispatchStrategy) {
        this.eventMeshAmqpServer = eventMeshAmqpServer;
        this.amqpGlobalMapping = amqpGlobalMapping;
        this.mqConsumerWrapper = mqConsumerWrapper;
        this.downstreamDispatchStrategy = downstreamDispatchStrategy;
    }

    /**
     * initialize amqpConsumer
     * 1. initialize EventMesh mq consumer
     * 2. create EventListener and register into EventMesh mq consumer
     */
    public void init() throws Exception {
        Properties keyValue = new Properties();
        keyValue.put(EventMeshConstants.IS_BROADCAST, "false");
        keyValue.put(EventMeshConstants.EVENT_MESH_IDC, this.eventMeshAmqpServer.getEventMeshAmqpConfiguration().eventMeshIDC);
        mqConsumerWrapper.init(keyValue);

        EventListener eventListener = (cloudEvent, context) -> {
            cloudEvent = CloudEventBuilder.from(cloudEvent).withExtension(EventMeshConstants.REQ_MQ2EVENTMESH_TIMESTAMP,
                            String.valueOf(System.currentTimeMillis()))
                    .withExtension(EventMeshConstants.REQ_RECEIVE_EVENTMESH_IP,
                            this.eventMeshAmqpServer.getEventMeshAmqpConfiguration().eventMeshServerIp).build();
            String topic = cloudEvent.getSubject();
            AmqpChannel selectedAmqpChannel = downstreamDispatchStrategy.select(topic, amqpGlobalMapping);

        };
    }
}
