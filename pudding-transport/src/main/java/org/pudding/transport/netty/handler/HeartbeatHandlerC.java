package org.pudding.transport.netty.handler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.log4j.Logger;
import org.pudding.common.protocol.MessageHolder;
import org.pudding.common.utils.MessageHolderFactory;

/**
 * Connector心跳检测Handler.
 * <p>
 *
 * @author Yohann.
 */
public class HeartbeatHandlerC extends ChannelInboundHandlerAdapter {
    private static final Logger logger = Logger.getLogger(HeartbeatHandlerC.class);

    @Override
    public void userEventTriggered(final ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            // 向接收端发送心跳包
            heartbeat(ctx).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        heartbeat(ctx);
                    }
                }
            });
        }
    }

    private ChannelFuture heartbeat(ChannelHandlerContext ctx) {
        MessageHolder holder = MessageHolderFactory.newHeartbeatHolder();
        return ctx.writeAndFlush(holder);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}