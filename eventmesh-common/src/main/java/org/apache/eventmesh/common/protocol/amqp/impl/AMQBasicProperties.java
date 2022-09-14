package org.apache.eventmesh.common.protocol.amqp.impl;

import org.apache.eventmesh.common.protocol.amqp.ContentHeader;
import org.apache.eventmesh.common.protocol.amqp.common.ProtocolKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * AMQP的内容头，一般正常的内容头类id固定为60，类名固定为basic
 * 包含若干个属性
 */
public class AMQBasicProperties implements ContentHeader {

    private int classId;

    private String className;
    private String contentType;
    private String contentEncoding;
    private Map<String, Object> headers;
    private Integer deliveryMode;
    private Integer priority;
    private String correlationId;
    private String replyTo;
    private String expiration;
    private String messageId;
    private Date timestamp;
    private String type;
    private String userId;
    private String appId;
    private String clusterId;

    private long bodySize;

    public AMQBasicProperties(){

    }

    public AMQBasicProperties(String contentType, String contentEncoding, Map<String, Object> headers, Integer deliveryMode, Integer priority, String correlationId, String replyTo, String expiration, String messageId, Date timestamp, String type, String userId, String appId, String clusterId, long bodySize) {
        this.classId = 60;
        this.className = "basic";
        this.contentType = contentType;
        this.contentEncoding = contentEncoding;
        this.headers = headers;
        this.deliveryMode = deliveryMode;
        this.priority = priority;
        this.correlationId = correlationId;
        this.replyTo = replyTo;
        this.expiration = expiration;
        this.messageId = messageId;
        this.timestamp = timestamp;
        this.type = type;
        this.userId = userId;
        this.appId = appId;
        this.clusterId = clusterId;
        this.bodySize = bodySize;
    }
    /**
     * 正常content header的类id为60
     * @return
     */
    @Override
    public int getClassId() {
        return this.classId;
    }

    /**
     * 正常content header的类名为basic
     * @return
     */
    @Override
    public String getClassName() {
        return this.className;
    }

    public String getContentType() {
        return contentType;
    }

    public long getBodySize() {
        return bodySize;
    }

    public void setBodySize(long bodySize) {
        this.bodySize = bodySize;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public Integer getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(Integer deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(ProtocolKey.ContentHeaderProperties.CLASS_ID, classId);
        map.put(ProtocolKey.ContentHeaderProperties.CLASS_NAME, className);
        map.put(ProtocolKey.ContentHeaderProperties.CONTENT_TYPE, contentType);
        map.put(ProtocolKey.ContentHeaderProperties.CONTENT_ENCODING, contentEncoding);
        map.put(ProtocolKey.ContentHeaderProperties.HEADERS, headers);
        map.put(ProtocolKey.ContentHeaderProperties.DELIVERY_MODE, deliveryMode);
        map.put(ProtocolKey.ContentHeaderProperties.PRIORITY, priority);
        map.put(ProtocolKey.ContentHeaderProperties.CORRELATION_ID, correlationId);
        map.put(ProtocolKey.ContentHeaderProperties.REPLY_TO, replyTo);
        map.put(ProtocolKey.ContentHeaderProperties.EXPIRATION, expiration);
        map.put(ProtocolKey.ContentHeaderProperties.MESSAGE_ID, messageId);
        map.put(ProtocolKey.ContentHeaderProperties.TIMESTAMP, timestamp);
        map.put(ProtocolKey.ContentHeaderProperties.TYPE, type);
        map.put(ProtocolKey.ContentHeaderProperties.USER_ID, userId);
        map.put(ProtocolKey.ContentHeaderProperties.APP_ID, appId);
        map.put(ProtocolKey.ContentHeaderProperties.CLUSTER_ID, clusterId);
        map.put(ProtocolKey.ContentHeaderProperties.BODY_SIZE, bodySize);
        return map;
    }
}
