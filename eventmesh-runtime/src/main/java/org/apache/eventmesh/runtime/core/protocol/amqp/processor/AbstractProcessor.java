package org.apache.eventmesh.runtime.core.protocol.amqp.processor;

import io.netty.channel.ChannelHandlerContext;
import org.apache.eventmesh.common.protocol.amqp.AMQCommand;
import org.apache.eventmesh.runtime.core.protocol.amqp.Connection;

public abstract class AbstractProcessor implements Runnable{
    protected AMQCommand amqCommand;
    protected ChannelHandlerContext ctx;
    protected Connection connection;

    public AbstractProcessor(AMQCommand amqCommand, ChannelHandlerContext ctx, Connection connection) {
        this.amqCommand = amqCommand;
        this.ctx = ctx;
        this.connection = connection;
    }
}
