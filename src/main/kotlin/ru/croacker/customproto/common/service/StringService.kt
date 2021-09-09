package ru.croacker.customproto.common.service

import java.util.*
import kotlin.streams.asSequence

object StringService {

    const val LENGTH = 15L
    const val LEFT_LIMIT = 97
    const val RIGHT_LIMIT = 122

    val random = Random()

    fun randomString(): String =
        random.ints(LENGTH, LEFT_LIMIT, RIGHT_LIMIT)
            .asSequence()
            .map { it.toChar() }
            .joinToString("", "").capitalize()

}