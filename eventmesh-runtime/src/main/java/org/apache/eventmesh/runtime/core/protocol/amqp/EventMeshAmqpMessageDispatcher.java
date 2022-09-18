package org.apache.eventmesh.runtime.core.protocol.amqp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


public class EventMeshAmqpMessageDispatcher extends SimpleChannelInboundHandler<> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

    }
}
