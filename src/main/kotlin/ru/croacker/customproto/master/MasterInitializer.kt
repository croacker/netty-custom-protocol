package ru.croacker.customproto.master

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.DelimiterBasedFrameDecoder
import io.netty.handler.codec.Delimiters
import ru.croacker.customproto.common.decoder.EventDecoder
import ru.croacker.customproto.common.decoder.EventEncoder

class MasterInitializer: ChannelInitializer<SocketChannel>() {

    @Throws(Exception::class)
    override fun initChannel(ch: SocketChannel) {
        ch.pipeline()
            .addLast(EventDecoder())
            .addLast(EventEncoder())
            .addLast(MasterChannelHandler())
    }

}