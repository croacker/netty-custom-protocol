package ru.croacker.customproto.master

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel

private const val PORT = 9999

class MasterNode {

    fun start() {
        val parent = NioEventLoopGroup(1)
        val child = NioEventLoopGroup(1)
        try {
            ServerBootstrap()
                .group(parent, child)
                .channel(NioServerSocketChannel::class.java)
                .childHandler(MasterInitializer())
                .bind(PORT).sync()
                .channel().closeFuture().sync()
        } finally {
            parent.shutdownGracefully()
            child.shutdownGracefully()
        }
    }

}