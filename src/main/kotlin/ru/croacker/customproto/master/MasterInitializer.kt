package ru.croacker.customproto.master

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.DelimiterBasedFrameDecoder
import io.netty.handler.codec.Delimiters
import io.netty.handler.codec.string.StringDecoder
import io.netty.handler.codec.string.StringEncoder

class MasterInitializer: ChannelInitializer<SocketChannel>() {

    @Throws(Exception::class)
    override fun initChannel(ch: SocketChannel) {
        ch.pipeline()
            .addLast(DelimiterBasedFrameDecoder(128, *Delimiters.lineDelimiter()))
            .addLast(StringDecoder())
            .addLast(StringEncoder())
            .addLast(StringAdapterHandler())
    }

}