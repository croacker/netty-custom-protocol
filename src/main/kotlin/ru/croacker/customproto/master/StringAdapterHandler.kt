package ru.croacker.customproto.master

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.group.ChannelGroup
import io.netty.channel.group.DefaultChannelGroup
import io.netty.util.concurrent.GlobalEventExecutor

val channels: ChannelGroup = DefaultChannelGroup(GlobalEventExecutor.INSTANCE)

class StringAdapterHandler: SimpleChannelInboundHandler<String>() {

    override fun channelActive(ctx: ChannelHandlerContext) {
        super.channelActive(ctx)
        channels.add(ctx.channel())
    }

    override fun channelRead0(ctx: ChannelHandlerContext, msg: String) {
        for (c in channels) {
            if (c !== ctx.channel()) {
                c.writeAndFlush("[${ctx.channel().remoteAddress()}] $msg\r\n")
            } else {
                c.writeAndFlush("[you] $msg\r\n")
            }
        }
    }

}