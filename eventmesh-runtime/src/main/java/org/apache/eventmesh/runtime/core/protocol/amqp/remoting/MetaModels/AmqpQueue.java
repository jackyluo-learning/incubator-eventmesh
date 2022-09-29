package org.apache.eventmesh.runtime.core.protocol.amqp.remoting.MetaModels;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Define Queue in amqp
 */
public class AmqpQueue {
    @Getter
    private final String queueName;

    @Getter
    private final boolean durable;
    private boolean passive;

    @Getter
    private boolean exclusive;

    @Getter
    private boolean autoDelete;

    // which connection create the queue.
    @Getter
    private final long connectionId;

    /**
     * ExchangeName and AmqpBinding Mapping
     */
    private final Map<String, AmqpBinding> bindings;

    public AmqpQueue(String queueName,
                                boolean durable, long connectionId,
                                boolean exclusive, boolean autoDelete) {
        this.queueName = queueName;
        this.durable = durable;
        this.connectionId = connectionId;
        this.exclusive = exclusive;
        this.autoDelete = autoDelete;
        this.bindings = new ConcurrentHashMap<>();
    }

    public void bindingExchange(AmqpExchange amqpExchange, AmqpBinding amqpBinding, String bindingKey, Map<String, Object> arguments) {
        if (isBindingExisted(amqpExchange)) {
            this.bindings.get(amqpExchange.getExchangeName()).addBindingKey(bindingKey);
        } else {
            amqpBinding.setAmqpExchange(amqpExchange);
            amqpBinding.setAmqpQueue(this);
            amqpBinding.addBindingKey(bindingKey);
            amqpBinding.setArguments(arguments);
            this.bindings.put(amqpExchange.getExchangeName(), amqpBinding);
        }
        amqpExchange.addQueue(this);
    }

    public void unbindExchange(AmqpExchange exchange) {
        exchange.removeQueue(this);
        this.bindings.remove(exchange.getExchangeName());
    }

    private boolean isBindingExisted(AmqpExchange exchange) {
        return null != this.bindings.get(exchange.getExchangeName());
    }
}
