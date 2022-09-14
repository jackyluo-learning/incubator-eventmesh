package org.apache.eventmesh.protocol.amqp.resolver;

import com.alibaba.fastjson.JSON;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.v1.CloudEventBuilder;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.commons.collections4.MapUtils;
import org.apache.eventmesh.common.protocol.amqp.AmqpMessage;
import org.apache.eventmesh.common.protocol.amqp.common.ProtocolKey;
import org.apache.eventmesh.common.protocol.amqp.impl.AMQBasicProperties;
import org.apache.eventmesh.protocol.amqp.AMQPProtocolConstant;
import org.apache.eventmesh.protocol.api.exception.ProtocolHandleException;

import java.net.URI;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class AmqpProtocolResolver {
    public static CloudEvent buildEvent(AmqpMessage amqpMessage) throws ProtocolHandleException {
        CloudEventBuilder cloudEventBuilder;
        cloudEventBuilder = new CloudEventBuilder();
        String id = UUID.randomUUID().toString();
        URI source = URI.create("/");
        String routingKey = ProtocolKey.DEFAULT_ROUTING_KEY;
        String exchange = ProtocolKey.DEFAULT_EXCHANGE;
        Map<String, Object> extendInfo = amqpMessage.getExtendInfo();
        if (MapUtils.isNotEmpty(extendInfo)) {
            routingKey = extendInfo.get(ProtocolKey.ROUTING_KEY).toString();
            exchange = extendInfo.get(ProtocolKey.EXCHANGE).toString();
        }
        AMQBasicProperties amqBasicProperties = amqpMessage.getContentHeader();
        byte[] contentBody = amqpMessage.getContentBody();
        cloudEventBuilder
                .withId(id)
                .withSource(source)
                .withType(AMQPProtocolConstant.PROTOCOL_NAME)
                .withSubject(routingKey)
                .withExtension(ProtocolKey.BASIC_PROPERTIES, JSON.toJSONString(amqBasicProperties))
                .withExtension(ProtocolKey.EXCHANGE, exchange);
        cloudEventBuilder.withData(contentBody);
        return cloudEventBuilder.build();
    }

    public static AmqpMessage buildAmqpMessage (CloudEvent cloudEvent) {
        Map<String, Object> extendInfo = new HashMap<>();
        String routingKey = cloudEvent.getSubject();
        String exchange = Objects.requireNonNull(cloudEvent.getExtension(ProtocolKey.EXCHANGE)).toString();
        extendInfo.put(ProtocolKey.ROUTING_KEY, routingKey);
        extendInfo.put(ProtocolKey.EXCHANGE, exchange);
        byte[] contentBody = Objects.requireNonNull(cloudEvent.getData()).toBytes();
        AMQBasicProperties amqBasicProperties = JSON.parseObject(Objects.requireNonNull(cloudEvent.getExtension(ProtocolKey.BASIC_PROPERTIES)).toString(), AMQBasicProperties.class);
        return new AmqpMessage(amqBasicProperties, contentBody, extendInfo);
    }

}
