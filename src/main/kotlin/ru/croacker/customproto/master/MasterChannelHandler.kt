package ru.croacker.customproto.master

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import ru.croacker.customproto.common.event.Event
import ru.croacker.customproto.common.service.EventFactory

class MasterChannelHandler : SimpleChannelInboundHandler<Event>() {

    private val state = MasterState

    private val factory = EventFactory

    override fun channelActive(ctx: ChannelHandlerContext) {
        println("New connection [${ctx.channel().remoteAddress()}]")
        super.channelActive(ctx)
        state.add(ctx.channel())
    }

    override fun channelRead0(ctx: ChannelHandlerContext, event: Event) {
        println(event)
        state.channels().forEach {
            if (ctx.channel() == it) {
                state.update(it, event)?.name
                it.writeAndFlush(factory.newAcknowledgment(event.messageId(), "MASTER"))
            } else {
                if (event.type() == 20) {
                    it.writeAndFlush(event)
                }
            }
        }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        val remoteAddress = ctx.channel().remoteAddress()
        val name = state.client(ctx.channel())?.name
        println("Disconnected [${name}:${remoteAddress}]")
        state.setInactive(ctx.channel())
        ctx.close()
    }


}