package ru.croacker.customproto.common.event

data class Acknowledgment(val id: Long, val sourceId: Long, var client: String = ""): Event{

    companion object Factory {
        fun create(id: Long, sourceId: Long, client: String = ""): Acknowledgment = Acknowledgment(id, sourceId, client)
    }

    override fun type(): Int = 1

    override fun messageId(): Long = id

    override fun sender(): String = client

}
