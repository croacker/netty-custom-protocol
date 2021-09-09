package ru.croacker.customproto.master

import ru.croacker.customproto.common.event.Event
import ru.croacker.customproto.common.event.Heartbeat
import ru.croacker.customproto.common.event.Order

data class ClientState(val id: String) {

    var online = true

    var name = "".padEnd(36, ' ')

    var heartbeat = System.currentTimeMillis()

    var ordersCount = 0L

    var lastMessageId = 0L

    val events = mutableListOf<Event>()

    fun incOrdersCount(){
        ++ordersCount
    }

    fun updateHeartbeatTimestamp(event: Heartbeat) {
        heartbeat = event.timestamp
    }

    fun addEvent(order: Order) {
        incOrdersCount()
        events.add(order)
    }

}