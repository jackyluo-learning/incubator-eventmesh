package org.apache.eventmesh.common.protocol.amqp.common;

import java.util.Map;

public class ProtocolKey {
    public static final String ROUTING_KEY = "routingKey";
    
    public static final String EXCHANGE = "exchange";

    public static final String DEFAULT_ROUTING_KEY = "defaultRoutingKey";

    public static final String DEFAULT_EXCHANGE = "defaultExchange";

    public static final String QUEUE_NAME = "queueName";

    public static final String BASIC_PROPERTIES = "basicProperties";

    public static class ContentHeaderProperties{
        public static final String CLASS_ID = "classId";

        public static final String CLASS_NAME = "className";

        public static final String CONTENT_TYPE = "contentType";

        public static final String CONTENT_ENCODING = "contentEncoding";

        public static final String HEADERS = "headers";

        public static final String DELIVERY_MODE = "deliveryMode";

        public static final String PRIORITY = "priority";

        public static final String CORRELATION_ID = "correlationId";

        public static final String REPLY_TO = "replyTo";

        public static final String EXPIRATION = "expiration";

        public static final String MESSAGE_ID = "messageId";

        public static final String TIMESTAMP = "timestamp";

        public static final String TYPE = "type";

        public static final String USER_ID = "userId";

        public static final String APP_ID = "appId";

        public static final String CLUSTER_ID = "clusterId";

        public static final String BODY_SIZE = "bodySize";
    }
}
