package ru.croacker.customproto.common.event

object Undefined: Event{

    override fun type(): Int = -1

    override fun messageId(): Long = -1

    override fun sender(): String = ""

}
