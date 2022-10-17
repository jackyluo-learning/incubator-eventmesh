package org.apache.eventmesh.runtime.core.protocol.amqp.downstreamstrategy;

import org.apache.eventmesh.runtime.core.protocol.amqp.processor.AmqpChannel;
import org.apache.eventmesh.runtime.core.protocol.amqp.util.AmqpGlobalMapping;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * randomly select channel
 */
public class RandomDispatchStrategy implements DownstreamDispatchStrategy {
    @Override
    public AmqpChannel select(String topic, AmqpGlobalMapping amqpGlobalMapping) {
        if (amqpGlobalMapping == null
                || MapUtils.isEmpty(amqpGlobalMapping.getConnection2ChannelMap())
                || MapUtils.isEmpty(amqpGlobalMapping.getQueue2ChannelMap())
                || StringUtils.isBlank(topic)) {
            return null;
        }

        ConcurrentHashMap<String, Set<AmqpChannel>> queue2ChannelMap = amqpGlobalMapping.getQueue2ChannelMap();
        if (!queue2ChannelMap.containsKey(topic)) {
            return null;
        }

        Set<AmqpChannel> amqpChannelSet = queue2ChannelMap.get(topic);
        // TODO: 2022/10/13 filter out invalid channel

        List<AmqpChannel> amqpChannelList = new ArrayList<>(amqpChannelSet);
        Collections.shuffle(amqpChannelList);
        return amqpChannelList.get(0);
    }
}
