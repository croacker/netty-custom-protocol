package ru.croacker.customproto.common.event

data class Heartbeat(val id: Long, val timestamp: Long, var client: String = ""): Event {

    companion object Factory {
        fun create(id: Long, timestamp: Long, client: String = ""): Heartbeat = Heartbeat(id, timestamp, client)
    }

    override fun type(): Int = 0

    override fun messageId(): Long = id

    override fun sender(): String = client

}
