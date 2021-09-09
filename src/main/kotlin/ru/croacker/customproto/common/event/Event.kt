package ru.croacker.customproto.common.event

interface Event {
    fun type(): Int

    fun messageId(): Long

    fun sender(): String
}