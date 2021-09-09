package ru.croacker.customproto

import ru.croacker.customproto.slave.SlaveNode

fun main() {
    repeat(40) {
        val slaveNode = SlaveNode()
        Thread({ start() }, slaveNode.name).start()
        Thread.sleep(1000)
    }
    println("All slave's started....")
}

fun start() {
    SlaveNode().start()
}