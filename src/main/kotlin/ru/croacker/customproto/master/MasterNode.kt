package ru.croacker.customproto.master

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel

private const val PORT = 9999

class MasterNode {

    fun start() {
        val group = NioEventLoopGroup(1)
        try {
            ServerBootstrap()
                .group(group)
                .channel(NioServerSocketChannel::class.java)
                .childHandler(MasterInitializer())
                .bind(PORT).sync()
                .channel().closeFuture().sync()
        } finally {
            group.shutdownGracefully()
        }
    }

}