package ru.croacker.customproto.common.decoder

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageDecoder
import ru.croacker.customproto.common.marshaller.Marshaller

class EventDecoder: MessageToMessageDecoder<ByteBuf>() {

    private val marshaller = Marshaller()

    override fun decode(ctx: ChannelHandlerContext, msg: ByteBuf, out: MutableList<Any>) {
        val str = msg.toString(Charsets.UTF_8)
        out.add(marshaller.unmarshal(str))
    }

}