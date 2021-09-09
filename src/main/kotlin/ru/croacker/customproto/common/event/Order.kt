package ru.croacker.customproto.common.event

data class Order(val id: Long, val description: String, var client: String = ""): Event{

    companion object Factory {
        fun create(id: Long, description: String, client: String = ""): Order = Order(id, description, client)
    }

    override fun type(): Int = 20

    override fun messageId(): Long = id

    override fun sender(): String = client

}
