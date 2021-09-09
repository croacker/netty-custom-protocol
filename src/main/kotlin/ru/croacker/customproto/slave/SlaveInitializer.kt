package ru.croacker.customproto.slave

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.DelimiterBasedFrameDecoder
import io.netty.handler.codec.Delimiters
import ru.croacker.customproto.common.decoder.EventDecoder
import ru.croacker.customproto.common.decoder.EventEncoder

class SlaveInitializer: ChannelInitializer<SocketChannel>() {

    override fun initChannel(ch: SocketChannel) {
        ch.pipeline()
            .addLast(DelimiterBasedFrameDecoder(128, *Delimiters.lineDelimiter()))
            .addLast(EventDecoder())
            .addLast(EventEncoder())
            .addLast(SlaveChannelHandler())
    }

}