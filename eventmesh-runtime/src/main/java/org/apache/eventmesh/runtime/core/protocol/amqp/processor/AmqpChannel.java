package org.apache.eventmesh.runtime.core.protocol.amqp.processor;

import com.rabbitmq.client.impl.AMQCommand;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.eventmesh.runtime.core.protocol.amqp.ExchangeContainer;
import org.apache.eventmesh.runtime.core.protocol.amqp.ExchangeService;
import org.apache.eventmesh.runtime.core.protocol.amqp.QueueContainer;
import org.apache.eventmesh.runtime.core.protocol.amqp.QueueService;
import org.apache.eventmesh.runtime.core.protocol.amqp.Session.Session;
import org.apache.eventmesh.runtime.core.protocol.amqp.Session.consumer.AmqpConsumer;
import org.apache.eventmesh.runtime.core.protocol.amqp.Session.consumer.AmqpConsumerImpl;
import org.apache.eventmesh.runtime.core.protocol.amqp.remoting.MetaModels.AmqpQueue;
import org.apache.eventmesh.runtime.core.protocol.amqp.remoting.constants.ErrorCodes;
import org.apache.eventmesh.runtime.core.protocol.amqp.remoting.frame.AMQPFrame;
import org.apache.eventmesh.runtime.util.AmqpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * client can build multiple channels, each represent a session
 */
public class AmqpChannel implements ChannelMethodProcessor {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private  int channelId;

    @Getter
    private  AmqpConnection connection;
    private  long amqpMaxMessageSize;
    /**
     * Maps from consumer tag to consumers instance.
     */
    private final Map<String, AmqpConsumer> tag2ConsumersMap = new ConcurrentHashMap<>();

    /**
     * The current message - which may be partial in the sense that not all frames have been received yet - which has
     * been received by this channel. As the frames are received the message gets updated and once all frames have been
     * received the message can then be routed.
     */
    private AMQCommand currentMessage;

    /**
     * This tag is unique per subscription to a queue. The server returns this in response to a basic.consume request.
     */
    private AtomicInteger consumerTag = new AtomicInteger(0);

    /**
     * The delivery tag is unique per channel. This is pre-incremented before putting into the deliver frame so that
     * value of this represents the <b>last</b> tag sent out.
     */
    private AtomicLong deliveryTag = new AtomicLong(0);

    private ExchangeService exchangeService;
    private QueueService queueService;
    private ExchangeContainer exchangeContainer;
    private QueueContainer queueContainer;

    /**
     * store context of a AmqpChannel
     */
    private Session session;

    public AmqpChannel(int channelId, AmqpConnection amqpConnection) {
        this.channelId = channelId;
        this.connection = amqpConnection;
        this.exchangeService = this.connection.getMetaMessageService().getExchangeService();
        this.queueService = this.connection.getMetaMessageService().getQueueService();
        this.exchangeContainer = this.connection.getMetaMessageService().getExchangeContainer();
        this.queueContainer = this.connection.getMetaMessageService().getQueueContainer();
        this.session = new Session(this);
    }

    @Override
    public void receiveChannelFlow(boolean active) {

    }

    @Override
    public void receiveChannelFlowOk(boolean active) {

    }

    @Override
    public void receiveChannelClose(int replyCode, String replyText, int classId, int methodId) {

    }

    @Override
    public void receiveChannelCloseOk() {

    }

    @Override
    public void receiveMessageContent(AMQPFrame data) {
        if (log.isDebugEnabled()) {
            try {
                log.debug("receive messageContent[data:{}]", new String(data.getData(), "UTF8"));
            } catch (UnsupportedEncodingException e) {
                log.error("Failed to encode data:{}", e);
            }
        }

        if (hasCurrentMessage()) {
            // TODO: 2022/9/20 send message to mq
//            try {
//                if (currentMessage.handleFrame(data)) {
//                    processSendMessage();
//                }
//            } catch (IOException e) {
//                log.error("receiveMessageContent exception {}", e);
//                closeChannel(ErrorCodes.COMMAND_INVALID,
//                        "Attempt to send a content  not valid");
//            } catch (Exception e) {
//                log.error("receiveMessageContent exception {}", e);
//                closeChannel(ErrorCodes.SYNTAX_ERROR,
//                        "system error");
//            }
        } else {
            closeChannel(ErrorCodes.COMMAND_INVALID,
                    "Attempt to send a content without first sending a publish frame");
        }
    }

    @Override
    public void receiveMessageHeader(AMQPFrame frame) {
        if (log.isDebugEnabled()) {
            log.debug("recv[{}] message header[{}]", channelId, frame);
        }

        if (hasCurrentMessage()) {
            // TODO: 2022/9/20 send message to mq
//            try {
//                if (currentMessage.handleFrame(frame)) {
//                    processSendMessage();
//                }
//            } catch (IOException e) {
//                log.error("receiveMessageHeader exce {}", e.getMessage());
//                closeChannel(ErrorCodes.COMMAND_INVALID,
//                        "Attempt to send a content  not valid");
//            } catch (Exception e) {
//                log.error("receiveMessageHeader exce {}", e.getMessage());
//                closeChannel(ErrorCodes.SYNTAX_ERROR,
//                        "system error");
//            }

            long bodySize = currentMessage.getContentHeader().getBodySize();
            if (bodySize > amqpMaxMessageSize) {
                log.error("RECV[{}] too large message bodySize {}", channelId, bodySize);
                closeChannel(ErrorCodes.MESSAGE_TOO_LARGE,
                        "Message size of " + bodySize + " greater than allowed maximum of " + amqpMaxMessageSize);
            }

        } else {
            closeChannel(ErrorCodes.COMMAND_INVALID,
                    "Attempt to send a content without first sending a publish frame");
        }
    }

    /**
     * send message
     */
    private void processSendMessage(){
        // TODO: 2022/9/20 convert to cloudEvent and send to mq



    }




    @Override
    public boolean ignoreAllButCloseOk() {
        return false;
    }

    @Override
    public void receiveBasicNack(long deliveryTag, boolean multiple, boolean requeue) {

    }

    @Override
    public void receiveBasicAck(long deliveryTag, boolean multiple) {

    }

    @Override
    public void receiveAccessRequest(String realm, boolean exclusive, boolean passive, boolean active, boolean write, boolean read) {

    }

    @Override
    public void receiveExchangeDeclare(String exchange, String type, boolean passive, boolean durable, boolean autoDelete, boolean internal, boolean nowait, Map<String, Object> arguments) {

    }

    @Override
    public void receiveExchangeDelete(String exchange, boolean ifUnused, boolean nowait) {

    }

    @Override
    public void receiveExchangeBound(String exchange, String routingKey, String queue) {

    }

    @Override
    public void receiveQueueDeclare(String queue, boolean passive, boolean durable, boolean exclusive, boolean autoDelete, boolean nowait, Map<String, Object> arguments) {

    }

    @Override
    public void receiveQueueBind(String queue, String exchange, String bindingKey, boolean nowait, Map<String, Object> arguments) {

    }

    @Override
    public void receiveQueuePurge(String queue, boolean nowait) {

    }

    @Override
    public void receiveQueueDelete(String queue, boolean ifUnused, boolean ifEmpty, boolean nowait) {

    }

    @Override
    public void receiveQueueUnbind(String queue, String exchange, String bindingKey, Map<String, Object> arguments) {

    }

    @Override
    public void receiveBasicRecover(boolean requeue, boolean sync) {

    }

    @Override
    public void receiveBasicQos(long prefetchSize, int prefetchCount, boolean global) {

    }

    @Override
    public void receiveBasicConsume(String queue, String consumerTag, boolean noLocal, boolean noAck, boolean exclusive, boolean nowait, Map<String, Object> arguments) {
        log.info("RECV BasicConsume[queue:{} consumerTag:{} noLocal:{} noAck:{} exclusive:{} nowait:{}"
                + " arguments:{}]",  queue, consumerTag, noLocal, noAck, exclusive, nowait, arguments);

        // TODO check queue exist
        AmqpQueue amqpQueue = queueService.getQueue(this.connection.getVhost(), queue);
        if (amqpQueue == null) {
            AmqpUtils.sendConnectionClose(ErrorCodes.NOT_FOUND, "No such queue: " + queue, this.channelId);
        } else {
            try {
                subscribe(consumerTag, amqpQueue, nowait);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void subscribe(String consumerTag, AmqpQueue amqpQueue, boolean nowait) throws Exception {
        final String customConsumerTag;
        if (StringUtils.isBlank(consumerTag)) {
            customConsumerTag = "consumerTag_" + getNextConsumerTag();
        } else {
            customConsumerTag = consumerTag;
        }

        AmqpConsumer c = new AmqpConsumerImpl();
        tag2ConsumersMap.putIfAbsent(customConsumerTag, c);
        this.session.setAmqpConsumer(c);
        List<String> topicList = queueName2Topic(amqpQueue);
        for (String topic : topicList) {
            this.session.getMqConsumerWrapper().subscribe(topic);
        }
        if (!nowait) {
            //connection.writeMethod(connection.getCommandFactory().createBasicConsumeOkBody(consumer.getConsumerTag()), channelId);
        }
    }

    private List<String> queueName2Topic (AmqpQueue amqpQueue) {
        List<String> topicList = new ArrayList<>();
        // TODO: 2022/9/29 transform queue to topic
        return topicList;
    }

    @Override
    public void receiveBasicCancel(String consumerTag, boolean noWait) {

    }

    @Override
    public void receiveBasicPublish(String exchange, String routingKey, boolean mandatory, boolean immediate) {

    }

    @Override
    public void receiveBasicGet(String queue, boolean noAck) {

    }

    @Override
    public void receiveBasicReject(long deliveryTag, boolean requeue) {

    }

    @Override
    public void receiveTxSelect() {

    }

    @Override
    public void receiveTxCommit() {

    }

    @Override
    public void receiveTxRollback() {

    }

    @Override
    public void receiveConfirmSelect(boolean nowait) {

    }

    public void closeChannel(int cause, final String message) {
        // TODO
    }


    private boolean hasCurrentMessage() {
        return currentMessage != null;
    }

    public long getNextDeliveryTag() {
        return deliveryTag.incrementAndGet();
    }

    private int getNextConsumerTag() {
        return consumerTag.incrementAndGet();
    }
}
