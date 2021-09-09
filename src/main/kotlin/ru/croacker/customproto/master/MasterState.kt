package ru.croacker.customproto.master

import ru.croacker.customproto.common.event.Event
import ru.croacker.customproto.common.event.Heartbeat
import ru.croacker.customproto.common.event.Order
import io.netty.channel.Channel

internal object MasterState {

    val startup = System.currentTimeMillis()

    val clients = mutableMapOf<Channel, ClientState>()

    fun channels(): Set<Channel> = clients.keys

    fun clientStates(): Collection<ClientState> = clients.values

    fun add(channel: Channel){
        clients[channel] = ClientState(clients.size.toString())
    }

    fun client(channel: Channel): ClientState? = clients[channel]

    fun update(channel: Channel, event: Event): ClientState? =
        clients[channel]?.apply {
            name = event.sender()
            lastMessageId = event.messageId()
            when (event.type()) {
                0 -> updateHeartbeatTimestamp(event as Heartbeat)
                20 -> addEvent(event as Order)
            }
        }

    fun setInactive(channel: Any): ClientState? = clients[channel]?.apply { online = false }

}