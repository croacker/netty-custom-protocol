package ru.croacker.customproto.common.decoder

import io.netty.buffer.ByteBufUtil
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageEncoder
import ru.croacker.customproto.common.event.Event
import ru.croacker.customproto.common.marshaller.Marshaller
import java.nio.CharBuffer

class EventEncoder: MessageToMessageEncoder<Event>() {

    private val marshaller = Marshaller()

    override fun encode(ctx: ChannelHandlerContext, event: Event, out: MutableList<Any>) {
        val msg = marshaller.marshal(event)
        out.add(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(msg), Charsets.UTF_8))
    }

}