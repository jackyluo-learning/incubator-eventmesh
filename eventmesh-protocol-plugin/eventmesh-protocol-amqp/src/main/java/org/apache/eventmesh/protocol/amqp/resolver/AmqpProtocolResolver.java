package org.apache.eventmesh.protocol.amqp.resolver;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.AMQP.BasicProperties;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.v1.CloudEventBuilder;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.commons.collections4.MapUtils;
import org.apache.eventmesh.common.protocol.amqp.AmqpMessage;
import org.apache.eventmesh.common.protocol.amqp.common.ProtocolKey;
import org.apache.eventmesh.protocol.amqp.AMQPProtocolConstant;
import org.apache.eventmesh.protocol.api.exception.ProtocolHandleException;

import java.net.URI;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * 解析AmqpMessage或CloudEvent
 */
public class AmqpProtocolResolver {
    public static CloudEvent buildEvent(AmqpMessage amqpMessage) throws ProtocolHandleException {
        CloudEventBuilder cloudEventBuilder;
        cloudEventBuilder = new CloudEventBuilder();
        String id = UUID.randomUUID().toString();
        URI source = URI.create("/");
//        String routingKey = ProtocolKey.DEFAULT_ROUTING_KEY;
//        String exchange = ProtocolKey.DEFAULT_EXCHANGE;
        String queueName = "";
        Map<String, Object> extendInfo = amqpMessage.getExtendInfo();
        if (MapUtils.isNotEmpty(extendInfo)) {
            // routingKey, exchange去掉，路由信息在这之前完成
//            routingKey = extendInfo.get(ProtocolKey.ROUTING_KEY).toString();
//            exchange = extendInfo.get(ProtocolKey.EXCHANGE).toString();
            queueName = extendInfo.get(ProtocolKey.QUEUE_NAME).toString();
        }
        BasicProperties amqBasicProperties = amqpMessage.getContentHeader();
        byte[] contentBody = amqpMessage.getContentBody();
        cloudEventBuilder
                .withId(id)
                .withSource(source)
                .withType(AMQPProtocolConstant.PROTOCOL_NAME)
                .withSubject(queueName)
                .withExtension(ProtocolKey.BASIC_PROPERTIES, JSON.toJSONString(amqBasicProperties))
                .withData(contentBody);
        return cloudEventBuilder.build();
    }

    public static AmqpMessage buildAmqpMessage (CloudEvent cloudEvent) {
        Map<String, Object> extendInfo = new HashMap<>();
        String routingKey = cloudEvent.getSubject();
        String exchange = Objects.requireNonNull(cloudEvent.getExtension(ProtocolKey.EXCHANGE)).toString();
        extendInfo.put(ProtocolKey.ROUTING_KEY, routingKey);
        extendInfo.put(ProtocolKey.EXCHANGE, exchange);
        byte[] contentBody = Objects.requireNonNull(cloudEvent.getData()).toBytes();
        BasicProperties amqBasicProperties = JSON.parseObject(Objects.requireNonNull(cloudEvent.getExtension(ProtocolKey.BASIC_PROPERTIES)).toString(), BasicProperties.class);
        return new AmqpMessage(amqBasicProperties, contentBody, extendInfo);
    }

}
