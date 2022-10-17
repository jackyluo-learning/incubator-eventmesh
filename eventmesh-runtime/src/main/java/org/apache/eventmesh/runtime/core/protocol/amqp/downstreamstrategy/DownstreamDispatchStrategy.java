package org.apache.eventmesh.runtime.core.protocol.amqp.downstreamstrategy;

import org.apache.eventmesh.runtime.core.protocol.amqp.AmqpGlobalMapping;
import org.apache.eventmesh.runtime.core.protocol.amqp.processor.AmqpChannel;

import java.util.Set;

/**
 * used to choose a channel that can consume message
 */
public interface DownstreamDispatchStrategy {
    AmqpChannel select(String topic, AmqpGlobalMapping amqpGlobalMapping);
}
