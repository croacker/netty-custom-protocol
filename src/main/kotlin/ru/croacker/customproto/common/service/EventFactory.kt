package ru.croacker.customproto.common.service

import ru.croacker.customproto.common.event.Acknowledgment
import ru.croacker.customproto.common.event.Event
import ru.croacker.customproto.common.event.Heartbeat
import ru.croacker.customproto.common.event.Order
import java.util.concurrent.atomic.AtomicLong

object EventFactory {

    private val messageCounter = AtomicLong()

    fun newHeartbeat(client: String): Event = Heartbeat.create(newId(), getTimestamp(), client)

    fun newAcknowledgment(sourceId: Long, client: String): Event =  Acknowledgment.create(newId(), sourceId, client)

    fun newOrder(client: String): Event = Order.create(newId(), StringService.randomString(), client)

    private fun newId(): Long = messageCounter.getAndIncrement()

    private fun getTimestamp(): Long = System.currentTimeMillis()

}