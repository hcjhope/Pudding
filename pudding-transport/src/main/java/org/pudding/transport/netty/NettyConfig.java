package org.pudding.transport.netty;

import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import org.pudding.transport.abstraction.Config;
import org.pudding.transport.options.Option;

import java.util.HashMap;
import java.util.Map;

/**
 * Netty配置.
 *
 * @author Yohann.
 */
public class NettyConfig implements INettyConfig {

    private Map<Option<?>, Object> options;  // save parent options, singleton
    private Map<Option<?>, Object> childOptions;  // save child options, singleton

    // EventLoopGroup default value:
    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    private Class<? extends Channel> channelClass;

    private ChannelInitializer initializer;

    public NettyConfig(Class<? extends Channel> channelClass, ChannelInitializer initializer) {
        this(null, null, channelClass, initializer);
    }

    public NettyConfig(EventLoopGroup bossGroup, EventLoopGroup workerGroup,
                       Class<? extends Channel> channelClass, ChannelInitializer initializer) {
        checkGroup(bossGroup, workerGroup);
        validate(channelClass, initializer);
        this.channelClass = channelClass;
        this.initializer = initializer;
        options = options();
        childOptions = childOptions();
    }

    private void checkGroup(EventLoopGroup bossGroup, EventLoopGroup workerGroup) {
        // Set the default values if the null
        if (bossGroup != null) {
            this.bossGroup = bossGroup;
        }
        if (workerGroup != null) {
            this.workerGroup = workerGroup;
        }
    }

    @Override
    public <T> Config option(Option<T> option, T value) {
        validate(option, value);
        options.put(option, value);
        return this;
    }

    @Override
    public <T> Config childOption(Option<T> option, T value) {
        validate(option, value);
        childOptions.put(option, value);
        return this;
    }

    @Override
    public INettyConfig bossGroup(EventLoopGroup bossGroup) {
        this.bossGroup = bossGroup;
        return this;
    }

    @Override
    public EventLoopGroup bossGroup() {
        return bossGroup;
    }

    @Override
    public INettyConfig workerGroup(EventLoopGroup workerGroup) {
        this.workerGroup = workerGroup;
        return this;
    }

    @Override
    public EventLoopGroup workerGroup() {
        return workerGroup;
    }

    @Override
    public INettyConfig channel(Class channelClass) {
        this.channelClass = channelClass;
        return this;
    }

    @Override
    public Class channel() {
        return channelClass;
    }

    @Override
    public INettyConfig childHandler(ChannelInitializer initializer) {
        this.initializer = initializer;
        return this;
    }

    @Override
    public ChannelInitializer childHandler() {
        return initializer;
    }

    @Override
    public void addHandlers(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
    }

    @Override
    public Map<Option<?>, Object> options() {
        if (options == null) {
            synchronized (this) {
                if (options == null) {
                    options = new HashMap<>();
                }
            }
        }
        return options;
    }

    @Override
    public Map<Option<?>, Object> childOptions() {
        if (childOptions == null) {
            synchronized (this) {
                if (childOptions == null) {
                    childOptions = new HashMap<>();
                }
            }
        }
        return childOptions;
    }

    private <T> void validate(Option<T> option, T value) {
        if (option == null) {
            throw new NullPointerException("option == null");
        }
        if (value == null) {
            throw new NullPointerException("value == null");
        }
    }

    private void validate(Class<? extends Channel> channelClass, ChannelInitializer initializer) {
        if (channelClass == null) {
            throw new NullPointerException("channelClass == null");
        }
        if (initializer == null) {
            throw new NullPointerException("initializer == null");
        }
    }
}