package ru.croacker.customproto.slave

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import ru.croacker.customproto.common.event.Event

class SlaveChannelHandler: SimpleChannelInboundHandler<Event>() {

    override fun channelRead0(ctx: ChannelHandlerContext, event: Event) {
        println(event)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }

}