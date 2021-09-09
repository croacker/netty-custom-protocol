package ru.croacker.customproto.slave

import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import ru.croacker.customproto.common.service.EventFactory
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

private const val HOST = "127.0.0.1"
private const val PORT = 9999

class SlaveNode {
    private val factory = EventFactory

    val name = UUID.randomUUID().toString()

    val scheduler = Executors.newScheduledThreadPool(2)

    fun start() {
        val group = NioEventLoopGroup(1)
        try {
            val channel = Bootstrap()
                .group(group)
                .channel(NioSocketChannel::class.java)
                .handler(SlaveInitializer())
                .connect(HOST, PORT).sync().channel()

            schedule(1) { sendHeartBeat(channel) }
            schedule(5) { sendOrder(channel) }

            channel.closeFuture().sync()
        } finally {
            group.shutdownGracefully()
        }
    }

    private fun schedule(period: Long, runnable: Runnable) {
        scheduler.scheduleAtFixedRate(runnable , 5, period, TimeUnit.SECONDS)
    }

    private fun sendHeartBeat(channel: Channel){
        val event = factory.newHeartbeat(name)
        channel.writeAndFlush(event)
    }

    private fun sendOrder(channel: Channel){
        val event = factory.newOrder(name)
        channel.writeAndFlush(event)
    }
}