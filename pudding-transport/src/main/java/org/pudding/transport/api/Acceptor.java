package org.pudding.transport.api;

import java.net.SocketAddress;

/**
 * 接受端.
 *
 * @author Yohann.
 */
public interface Acceptor {

    /**
     * 返回绑定地址.
     *
     * @return
     */
    SocketAddress localAddress();

    /**
     * 绑定本地，启动监听.
     *
     * @param port
     */
    Future bind(int port);

    /**
     * 绑定本地，启动监听.
     *
     * @param host
     * @param port
     */
    Future bind(String host, int port);

    /**
     * 绑定本地，启动监听.
     *
     * @param localAddress
     */
    Future bind(SocketAddress localAddress);

    /**
     * 返回配置对象Config.
     *
     * @return
     */
    Config config();

    /**
     * 关闭服务资源.
     *
     * @return
     */
    void shutdownGracefully();
}
